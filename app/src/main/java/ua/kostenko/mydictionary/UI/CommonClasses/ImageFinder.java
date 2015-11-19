package ua.kostenko.mydictionary.UI.CommonClasses;

import android.content.Context;
import android.graphics.drawable.Drawable;

import ua.kostenko.mydictionary.R;

// TODO: finalize this class and method
public class ImageFinder {
    public static Drawable findImageById(int id, Context context) {
        if (id == 0) {
            return context.getResources().getDrawable(R.drawable.no_image_available);
        }
        return null;
    }

    public static Drawable findImageById(String id, Context context) {
        if ("0".equals(id)) return findImageById(0, context);
        return null;
    }
}
