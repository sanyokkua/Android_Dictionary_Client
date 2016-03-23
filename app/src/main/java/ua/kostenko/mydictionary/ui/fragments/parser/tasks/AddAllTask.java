package ua.kostenko.mydictionary.ui.fragments.parser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Preconditions;

import java.util.List;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

public class AddAllTask extends AsyncTask<List<ParserUnit>, Void, Boolean> {
    private final Context context;
    private final MaterialDialog progressDialog;
    private UnitDao unitDao; //TODO: inject dao
    private TranslateService translateService;

    public AddAllTask(@NonNull final Context context) {
        Preconditions.checkNotNull(context);
        this.context = context;
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
        //TODO: testing
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*for (ParserUnit current : params[0]) {
            Unit unit = unitDao.findBySource(current.getSource());
            if (unit != null) {
                update(current, unit);
            } else {
                create(current);
            }
        }*/
        return true;
    }


    private void update(@NonNull final ParserUnit current, Unit unit) {
        long counter = unit.getCounter() + current.getCounter();
        unit.setCounter(counter);
        unitDao.saveUnit(unit);
    }

    private void create(@NonNull final ParserUnit current) {
        String translation = translateService.translate(Languages.ENGLISH, Languages.RUSSIAN, current.getSource());
        Unit newUnit = new Unit(current.getSource(), translation, current.getCounter());
        unitDao.saveUnit(newUnit);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
    }
}
