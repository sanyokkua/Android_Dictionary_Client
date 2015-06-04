package ua.nure.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.Fragments.Web.AdditionItems.Bookmark;
import ua.nure.mydictionary.UI.Fragments.Web.AdditionItems.BookmarkAdapter;


public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Bookmark> mBookmarks = new ArrayList<>();

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_rv_container, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.common_rv_container);
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mBookmarks = createItems();// TODO: create real bookmarks
        mAdapter = new BookmarkAdapter(mBookmarks);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    // TODO:delete before relize
    private ArrayList<Bookmark> createItems() {
        ArrayList<Bookmark> items = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            try {
                items.add(new Bookmark(new URL("http:://someSite.com/" + i), "SomeSite " + i));
            } catch (MalformedURLException ex) {

            }
        }
        return items;
    }


    public static Fragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }
}
