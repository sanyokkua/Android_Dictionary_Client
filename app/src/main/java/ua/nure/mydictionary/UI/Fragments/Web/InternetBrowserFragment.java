package ua.nure.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.LinkedList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarHandler;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;

public class InternetBrowserFragment extends Fragment implements Identifier {
    public static final String NAME = "BrowserFragment";
    private LinkedList<String> lastAddress = new LinkedList<>();
    private String mAddress = null;
    private Toolbar mToolbar;
    private BookmarkFragment.OnBookmarkOpenCallback mOnBookmarkOpenCallback = new BookmarkFragment.OnBookmarkOpenCallback() {
        @Override
        public void onBookmarkOpenCallback(String address) {
            replaceFragment(WebViewFragment.newInstance(address));
        }
    };

    public InternetBrowserFragment() {
        // Required empty public constructor
    }

    public static InternetBrowserFragment newInstance() {
        InternetBrowserFragment fragment = new InternetBrowserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_container, container, false);
        mToolbar = ToolbarHandler.getToolbar(getActivity());
        ToolbarHandler.setBrowserMode(mToolbar);
        setButtonsHandlers();
        return rootView;
    }

    private void setButtonsHandlers() {
        ImageButton forwardButton = ToolbarHandler.getForwardImageButton(mToolbar);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddress = ToolbarHandler.getEditText(mToolbar).getText().toString();
                lastAddress.add(mAddress);
                replaceFragment(WebViewFragment.newInstance(lastAddress.pop()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        BookmarkFragment fragment = (BookmarkFragment) BookmarkFragment.newInstance();
        fragment.setOnBookmarkOpenCallback(mOnBookmarkOpenCallback);
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.common_container, fragment);
            transaction.addToBackStack(null);
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
