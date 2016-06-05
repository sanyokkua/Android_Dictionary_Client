package ua.kostenko.mydictionary.ui.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ua.kostenko.mydictionary.R;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected void replaceFragment(@NonNull final Fragment fragment) {
        replaceFragment(fragment, false);
    }

    protected void replaceFragment(@NonNull final Fragment fragment, boolean addToBackStack) {
        checkNotNull(fragment, "Fragment can't be null!");
        final String fragmentName = fragment.getClass().getSimpleName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(fragmentName, 0);
            if (!fragmentPopped && manager.findFragmentByTag(fragmentName) == null) {
                transaction.add(R.id.main_activity_content_holder, fragment);
                transaction.addToBackStack(fragmentName);
            }
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.main_activity_content_holder, fragment);
        transaction.commit();
        Log.d(TAG, String.format("Fragment was replaced to : %s", fragmentName));
    }
}
