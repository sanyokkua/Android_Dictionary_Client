package ua.kostenko.mydictionary.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.CommonClasses.ToolbarHandler;
import ua.kostenko.mydictionary.UI.CommonInterfaces.Identifier;
import ua.kostenko.mydictionary.UI.Fragments.Dictionary.DictionaryFragment;
import ua.kostenko.mydictionary.UI.Fragments.Exercises.ExercisesFragment;
import ua.kostenko.mydictionary.UI.Fragments.Parser.ParserFragment;
import ua.kostenko.mydictionary.UI.Fragments.Web.InternetBrowserFragment;
import ua.kostenko.mydictionary.core.database.DbHelperFactory;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MY_DICTIONARY_LOG: ";
    private static final String LAST_FRAGMENT_NAME = "LAST_FRAGMENT_NAME";
    private static final String LAST_DRAWER_SELECTION = "LAST_DRAWER_SELECTION";
    private String mLastFragmentName;
    private Drawer mNavigationDrawer;
    private Toolbar mToolbar;
    private int mDrawerLastSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLastSelection = 0;
        createToolbar();
        createNavigationDrawer();
        openLastFragment();

        DbHelperFactory.setDatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onStop() {
        DbHelperFactory.releaseHelper();
        super.onStop();
    }

    private void createToolbar() {
        mToolbar = ToolbarHandler.getToolbar(this);
    }

    private void createNavigationDrawer() {
        mNavigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withDisplayBelowToolbar(false)
                .withToolbar(mToolbar)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_dictionary_fragment)
                                .withIcon(R.drawable.ic_action_icon_book_dark).withIdentifier(Identifiers.DICTIONARY),
                        new PrimaryDrawerItem().withName(R.string.title_browser_fragment)
                                .withIcon(R.drawable.ic_action_social_public_dark).withIdentifier(Identifiers.BROWSER),
                        new PrimaryDrawerItem().withName(R.string.title_parser_fragment)
                                .withIcon(R.drawable.ic_action_icon_parser_dark).withIdentifier(Identifiers.PARSER),
                        new PrimaryDrawerItem().withName(R.string.title_exercise_fragment)
                                .withIcon(R.drawable.ic_action_icon_exercise_dark).withIdentifier(Identifiers.EXERCISES),
                        new SectionDrawerItem().withName(R.string.title_settings),
                        new DividerDrawerItem())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                   @Override
                                                   public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                                                       selectDrawerItem(iDrawerItem.getIdentifier());
                                                       return false;
                                                   }
                                               }
                ).withOnDrawerListener(new DrawerAdapter() {
                    @Override
                    public void onDrawerOpened(View var1) {
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                })
                .build();
    }

    private void openLastFragment() {
        if (DictionaryFragment.NAME.equals(mLastFragmentName)) {
            replaceFragment(DictionaryFragment.newInstance());
            mDrawerLastSelection = 0;
        } else if (InternetBrowserFragment.NAME.equals(mLastFragmentName)) {
            replaceFragment(InternetBrowserFragment.newInstance());
            mDrawerLastSelection = 1;
        } else if (ParserFragment.NAME.equals(mLastFragmentName)) {
            replaceFragment(ParserFragment.newInstance());
            mDrawerLastSelection = 2;
        } else if (ExercisesFragment.NAME.equals(mLastFragmentName)) {
            replaceFragment(ExercisesFragment.newInstance());
            mDrawerLastSelection = 3;
        } else {
            replaceFragment(DictionaryFragment.newInstance());
            mDrawerLastSelection = 0;
        }
        mNavigationDrawer.setSelection(mDrawerLastSelection);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.main_activity_content_frame, fragment);
        transaction.commit();
        if (fragment instanceof Identifier) {
            mLastFragmentName = ((Identifier) fragment).getIdentifier();
            Log.d(LOG_TAG, "mLastFragmentName = " + mLastFragmentName);
        }
    }

    private void selectDrawerItem(int identifier) {
        switch (identifier) {
            case Identifiers.BROWSER:
                replaceFragment(InternetBrowserFragment.newInstance());
                break;
            case Identifiers.EXERCISES:
                replaceFragment(ExercisesFragment.newInstance());
                break;
            case Identifiers.PARSER:
                replaceFragment(ParserFragment.newInstance());
                break;
            case Identifiers.DICTIONARY:
                replaceFragment(DictionaryFragment.newInstance());
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LAST_FRAGMENT_NAME, mLastFragmentName);
        outState.putInt(LAST_DRAWER_SELECTION, mDrawerLastSelection);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mLastFragmentName = savedInstanceState.getString(LAST_FRAGMENT_NAME);
        mDrawerLastSelection = savedInstanceState.getInt(LAST_DRAWER_SELECTION);
        openLastFragment();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen())
            mNavigationDrawer.closeDrawer();
        else
            super.onBackPressed();
    }

    private interface Identifiers {
        int DICTIONARY = 0x01;
        int BROWSER = 0x02;
        int PARSER = 0x03;
        int EXERCISES = 0x04;
    }

    private abstract class DrawerAdapter implements Drawer.OnDrawerListener {
        @Override
        public abstract void onDrawerOpened(View view);

        @Override
        public void onDrawerClosed(View view) {
            //Do nothing
        }

        @Override
        public void onDrawerSlide(View view, float v) {
            //Do nothing
        }
    }
}
