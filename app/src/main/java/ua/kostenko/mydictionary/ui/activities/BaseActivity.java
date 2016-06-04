package ua.kostenko.mydictionary.ui.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.commonutils.Utils;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected void replaceFragment(@NonNull final Fragment fragment) {
        replaceFragment(fragment, false);
    }

    protected void replaceFragment(@NonNull final Fragment fragment, boolean addToBackStack) {
        Utils.checkNotNull(fragment, "Fragment can't be null!");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (addToBackStack) {
            String backStateName = fragment.getClass().getName();
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) {
                transaction.add(R.id.main_activity_content_holder, fragment);
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }
        }
        transaction.replace(R.id.main_activity_content_holder, fragment);
        transaction.commit();
        Log.d(TAG, String.format("Fragment was replaced to : %s", fragment.getClass().getSimpleName()));
    }
}