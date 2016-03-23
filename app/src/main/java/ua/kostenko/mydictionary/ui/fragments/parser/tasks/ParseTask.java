package ua.kostenko.mydictionary.ui.fragments.parser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Preconditions;

import java.util.List;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.dataaccess.FileUtils;
import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.local.parsing.implementation.TxtParser;

public class ParseTask extends AsyncTask<String, Void, List<ParserUnit>> {
    private final Context context;
    private final MaterialDialog progressDialog;
    private final OnFinish onFinish;

    public ParseTask(@NonNull final Context context, @NonNull final OnFinish onFinish) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(onFinish);
        this.context = context;
        this.onFinish = onFinish;
        progressDialog = new MaterialDialog.Builder(context)
                .title(R.string.parser_progress_dialog)
                .content(R.string.parser_progress_dialog_content)
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
    protected List<ParserUnit> doInBackground(String... params) {
        final FileUtils fileUtils = new FileUtils(context);
        final Parser<ParserUnit> parser = new TxtParser();
        try {
            Thread.sleep(1000 * 5);//TODO: only for testing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parser.parse(fileUtils.readFile(params[0]));
    }

    @Override
    protected void onPostExecute(List<ParserUnit> parserUnits) {
        super.onPostExecute(parserUnits);
        progressDialog.hide();
        onFinish.onFinish(parserUnits);
    }

    public interface OnFinish {
        void onFinish(List<ParserUnit> unitsList);
    }
}
