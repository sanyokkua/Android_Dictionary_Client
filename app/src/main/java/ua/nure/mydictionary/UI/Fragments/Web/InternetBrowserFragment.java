package ua.nure.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarCreator;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;

public class InternetBrowserFragment extends Fragment implements Identifier {
    public static final String NAME = "BrowserFragment";
    private LinkedList<String> lastAddress = new LinkedList<>();
    private String mAddress = null;
    private Fragment webPageFragment;
    private Toolbar mToolbar;

    public InternetBrowserFragment() {
        // Required empty public constructor
    }

    public static InternetBrowserFragment newInstance() {
        InternetBrowserFragment fragment = new InternetBrowserFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_container, container, false);
        mToolbar = ToolbarCreator.getToolbar(getActivity());
        mToolbar.setTitle(getString(R.string.title_browser_fragment));
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        replaceFragment(BookmarkFragment.newInstance());
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.common_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public String getIdentifier() {
        return NAME;
    }

    public interface OnPageRefresh {
        void refreshPage();
    }

    public interface GetHtml {
        String getHtml();
    }
}
