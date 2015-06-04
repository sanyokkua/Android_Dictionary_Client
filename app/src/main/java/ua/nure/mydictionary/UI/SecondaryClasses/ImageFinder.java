package ua.nure.mydictionary.UI.SecondaryClasses;

import android.content.Context;
import android.graphics.drawable.Drawable;

import ua.nure.mydictionary.R;

// TODO: finalize this class and method
public class ImageFinder {
    public static Drawable findImageById(int id, Context context) {
        if (id == 0) {
            return context.getResources().getDrawable(R.drawable.no_image_available);
        }
        return null;
    }
}
