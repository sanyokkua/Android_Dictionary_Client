package ua.kostenko.mydictionary.ui.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import ua.kostenko.mydictionary.R;

public class SnackBarHelper {

    public static Snackbar make(View view, String text, int length) {
        Snackbar snackbar = Snackbar.make(view, text, length);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
        TextView snackBarTextView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        snackBarTextView.setTextColor(view.getResources().getColor(R.color.colorPrimaryText));
        return snackbar;
    }
}
