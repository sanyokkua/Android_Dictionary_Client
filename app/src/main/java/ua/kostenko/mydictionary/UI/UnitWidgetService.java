package ua.kostenko.mydictionary.UI;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Александр on 02.12.2015.
 */
public class UnitWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new UnitWidgetFactory(getApplicationContext(), intent);
    }
}
