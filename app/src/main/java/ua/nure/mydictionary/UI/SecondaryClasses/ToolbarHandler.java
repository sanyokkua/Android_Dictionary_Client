package ua.nure.mydictionary.UI.SecondaryClasses;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ua.nure.mydictionary.R;

public class ToolbarHandler {
    public static Toolbar getToolbar(Activity appActivity) {
        Toolbar sToolbar = (Toolbar) appActivity.findViewById(R.id.toolbar);
        if (sToolbar == null)
            throw new NullPointerException("Toolbar is null");
        return sToolbar;
    }

    public static TextView getTitleTextView(Toolbar toolbar) {
        if (toolbar == null)
            throw new NullPointerException("Toolbar is null");
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title_text_view);
        return textView;
    }

    public static EditText getEditText(Toolbar toolbar) {
        if (toolbar == null)
            throw new NullPointerException("Toolbar is null");
        EditText editText = (EditText) toolbar.findViewById(R.id.toolbar_edit_text);
        return editText;
    }

    public static ImageButton getBackImageButton(Toolbar toolbar) {
        return getImageButton(toolbar, R.id.toolbar_back_image_button);
    }

    public static ImageButton getForwardImageButton(Toolbar toolbar) {
        return getImageButton(toolbar, R.id.toolbar_forward_image_button);
    }

    public static ImageButton getBookmarkImageButton(Toolbar toolbar) {
        return getImageButton(toolbar, R.id.toolbar_bookmark_image_button);
    }

    public static ImageButton getParseImageButton(Toolbar toolbar) {
        return getImageButton(toolbar, R.id.toolbar_parse_image_button);
    }

    private static ImageButton getImageButton(Toolbar toolbar, int id) {
        if (toolbar == null)
            throw new NullPointerException("Toolbar is null");
        ImageButton imageButton = (ImageButton) toolbar.findViewById(id);
        return imageButton;
    }

    public static void setOnlyTitleMode(Toolbar toolbar) {
        if (toolbar == null)
            throw new NullPointerException("Toolbar is null");
        getTitleTextView(toolbar).setVisibility(View.VISIBLE);
        getEditText(toolbar).setVisibility(View.GONE);
        getBackImageButton(toolbar).setVisibility(View.GONE);
        getForwardImageButton(toolbar).setVisibility(View.GONE);
        getBookmarkImageButton(toolbar).setVisibility(View.GONE);
        getParseImageButton(toolbar).setVisibility(View.GONE);
    }

    public static void setBrowserMode(Toolbar toolbar) {
        if (toolbar == null)
            throw new NullPointerException("Toolbar is null");
        getTitleTextView(toolbar).setVisibility(View.GONE);
        getEditText(toolbar).setVisibility(View.VISIBLE);
        getBackImageButton(toolbar).setVisibility(View.GONE);
        getForwardImageButton(toolbar).setVisibility(View.VISIBLE);
        getBookmarkImageButton(toolbar).setVisibility(View.GONE);
        getParseImageButton(toolbar).setVisibility(View.VISIBLE);
    }
}
