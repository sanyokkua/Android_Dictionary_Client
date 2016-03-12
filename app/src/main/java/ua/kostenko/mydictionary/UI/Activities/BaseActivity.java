package ua.kostenko.mydictionary.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.DbHelperFactory;

public abstract class BaseActivity extends AppCompatActivity {

    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.new_main_activity_content_holder, fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelperFactory.setDatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onStop() {
        DbHelperFactory.releaseHelper();
        super.onStop();
    }
}
