package ua.nure.mydictionary.UI.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.Fragments.Dictionary.DictionaryFragment;
import ua.nure.mydictionary.UI.Fragments.Exercises.ExercisesFragment;
import ua.nure.mydictionary.UI.Fragments.Parser.ParserFragment;
import ua.nure.mydictionary.UI.Fragments.Web.InternetBrowserFragment;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarHandler;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MyApplication";
    private static final String LAST_FRAGMENT_NAME = "LAST_FRAGMENT_NAME";
    private static final String LAST_DRAWER_SELECTION = "LAST_DRAWER_SELECTION";
    private String mLastFragmentName;
    private Drawer mNavigationDrawer;
    private Toolbar mToolbar;
    private int mDrawerLastSelection;

    private interface Identifiers {
        int DICTIONARY = 0x01;
        int BROWSER = 0x02;
        int PARSER = 0x03;
        int EXERCISES = 0x04;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLastSelection = 0;
        createToolbar();
        createNavigationDrawer();
        openLastFragment();
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
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int id, long l, IDrawerItem iDrawerItem) {
                        drawerItemSelected(iDrawerItem.getIdentifier());
                        return false;
                    }
                }).withOnDrawerListener(new DrawerAdapter() {
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

    private void drawerItemSelected(int identifier) {
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
        Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mLastFragmentName = savedInstanceState.getString(LAST_FRAGMENT_NAME);
        mDrawerLastSelection = savedInstanceState.getInt(LAST_DRAWER_SELECTION);
        openLastFragment();
        Log.d(LOG_TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen()) mNavigationDrawer.closeDrawer();
        else super.onBackPressed();
    }

    private abstract class DrawerAdapter implements Drawer.OnDrawerListener {
        @Override
        public abstract void onDrawerOpened(View view); //Must be implemented in child class

        @Override
        public void onDrawerClosed(View view) {
            //Nothing do.
        }

        @Override
        public void onDrawerSlide(View view, float v) {
            //Nothing do.
        }
    }
}
