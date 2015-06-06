package ua.nure.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.Fragments.Web.AdditionItems.Bookmark;
import ua.nure.mydictionary.UI.Fragments.Web.AdditionItems.BookmarkAdapter;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemLongClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnResultCallback;


public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BookmarkAdapter mAdapter;
    private ArrayList<Bookmark> mBookmarks = new ArrayList<>();

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.common_rv_container, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.common_rv_container);
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new ScaleInAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(1000);
        mRecyclerView.getItemAnimator().setRemoveDuration(1000);
        mRecyclerView.getItemAnimator().setMoveDuration(1000);
        mRecyclerView.getItemAnimator().setChangeDuration(1000);


        mBookmarks = createItems();// TODO: create real bookmarks
        mAdapter = new BookmarkAdapter(mBookmarks);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(rootView, "onItemClick: ", Snackbar.LENGTH_LONG).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                //Snackbar.make(rootView, "onItemLongClick: ", Snackbar.LENGTH_LONG).show();
                new MaterialDialog.Builder(getActivity())
                        .content(getActivity().getResources()
                                .getString(R.string.browser_delete_bookmark))
                        .positiveText(getActivity().getResources()
                                .getString(R.string.dictionary_del))
                        .negativeText(getActivity().getResources()
                                .getString(R.string.std_cancel))
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                mAdapter.removeItem(position);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNeutral(dialog);
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        mAdapter.setOnRemoveCallback(new OnResultCallback<Bookmark>() {
            @Override
            public void resultCallback(Bookmark resultItem) {
                Snackbar.make(rootView, "onResultCallback: ", Snackbar.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    // TODO:delete before release
    private ArrayList<Bookmark> createItems() {
        ArrayList<Bookmark> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
