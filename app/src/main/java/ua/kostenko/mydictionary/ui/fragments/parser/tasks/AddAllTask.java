package ua.kostenko.mydictionary.ui.fragments.parser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import javax.inject.Inject;

import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.OnResultCallback;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;
import static ua.kostenko.mydictionary.core.commonutils.Utils.isNull;

public class AddAllTask extends AsyncTask<List<ParserUnit>, Void, Boolean> {
    private static final String TAG = AddAllTask.class.getSimpleName();
    private MaterialDialog progressDialog;
    @Inject Context context;
    @Inject UnitDao unitDao;
    @Inject TranslateService<Unit> translateService;

    public AddAllTask(@NonNull final Context context) {
        checkNotNull(context);
        App.getAppComponent().inject(this);
        progressDialog = new MaterialDialog.Builder(context)
                .title(R.string.parser_dialog_add_all_title)
                .content(R.string.parser_dialog_add_all_content)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(List<ParserUnit>... params) {
        for (ParserUnit current : params[0]) {
            Log.d(TAG, "UnitDao: " + unitDao);
            Unit unit = unitDao.findBySource(current.getSource());
            Log.d(TAG, "Unit: " + unit);
            if (isNull(unit)){
                translateService.translate(Languages.ENGLISH, Languages.RUSSIAN, current.getSource(), new OnResultCallback<Unit>() {
                    @Override
                    public void onResult(Unit result) {
                        unitDao.saveUnit(result);
                    }
                });
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
        progressDialog = null;
    }
}
