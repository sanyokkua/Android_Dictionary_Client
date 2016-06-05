package ua.kostenko.mydictionary.ui.fragments.parser.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.dataaccess.FileUtils;
import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.core.local.parsing.implementation.TxtParser;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;

public class ParseTask extends AsyncTask<String, Void, List<ParserUnit>> {
    private final OnFinish onFinish;
    @Inject FileUtils fileUtils;
    private MaterialDialog progressDialog;

    public ParseTask(@NonNull final Context context, @NonNull final OnFinish onFinish) {
        App.getAppComponent().inject(this);
        checkNotNull(context);
        checkNotNull(onFinish);
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
        final Parser<ParserUnit> parser = new TxtParser();
        final List<ParserUnit> parse = parser.parse(fileUtils.readFile(params[0]));
        Collections.sort(parse, new Comparator<ParserUnit>() {
            @Override
            public int compare(ParserUnit first, ParserUnit second) {
                int result = 0;
                if (first.getCounter() != second.getCounter()) {
                    result = first.getCounter() > second.getCounter() ? -1 : 1;
                }
                return result;
            }
        });
        return parse;
    }

    @Override
    protected void onPostExecute(List<ParserUnit> parserUnits) {
        super.onPostExecute(parserUnits);
        progressDialog.hide();
        progressDialog = null;
        onFinish.onFinish(parserUnits);
    }

    public interface OnFinish {
        void onFinish(List<ParserUnit> unitsList);
    }
}
