package ua.kostenko.mydictionary.UI;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.CommonClasses.DataAccess.WordDataAccess;
import ua.kostenko.mydictionary.UI.CommonClasses.Word;

/**
 * Created by Александр on 02.12.2015.
 */
public class UnitWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Word> list;
    Context context;
    int widgetId;

    UnitWidgetFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        list = new ArrayList<>();
        WordDataAccess wordDataAccess = new WordDataAccess(context);
        list.addAll(wordDataAccess.getSavedData());
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.dictionary_word);
        remoteViews.setTextViewText(R.id.dictionary_word_text_view, list.get(i).getWord());
        remoteViews.setTextViewText(R.id.dictionary_translation_text_view, list.get(i).getTranslation());
        remoteViews.setTextViewText(R.id.dictionary_count_text_view, String.valueOf(list.get(i).getCount()));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}