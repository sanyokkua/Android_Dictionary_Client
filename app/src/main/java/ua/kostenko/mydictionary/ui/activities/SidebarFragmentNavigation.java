package ua.kostenko.mydictionary.ui.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.ui.fragments.dictionary.UnitsFragment;
import ua.kostenko.mydictionary.ui.fragments.exercise.ExerciseFragment;
import ua.kostenko.mydictionary.ui.fragments.parser.UnitParserFragment;

public class SidebarFragmentNavigation implements NavigationView.OnNavigationItemSelectedListener {
    private final DrawerLayout drawer;

    public SidebarFragmentNavigation(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_units_fragment) {
            EventBus.getDefault().post(UnitsFragment.newInstance());
        } else if (id == R.id.nav_parser_fragment) {
            EventBus.getDefault().post(UnitParserFragment.newInstance());
        } else if (id == R.id.nav_exercise_fragment) {
            EventBus.getDefault().post(ExerciseFragment.newInstance());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
