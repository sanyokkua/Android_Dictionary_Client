package ua.kostenko.mydictionary.ui.iterfaces;

import android.view.View;

public interface OnLongClickCustomListener<T> {
    void onItemLongClick(T item, View view);
}
