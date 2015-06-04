package ua.nure.mydictionary.UI.SecondaryClasses;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import ua.nure.mydictionary.R;

public class ToolbarCreator {
    public static Toolbar getToolbar(Activity appActivity) {
        Toolbar sToolbar = (Toolbar) appActivity.findViewById(R.id.toolbar);
        if (sToolbar == null)
            throw new NullPointerException("Toolbar is null");
        return sToolbar;
    }
}
