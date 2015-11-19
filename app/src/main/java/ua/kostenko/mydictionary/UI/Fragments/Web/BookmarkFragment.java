package ua.kostenko.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.Fragments.Web.AdditionItems.Bookmark;
import ua.kostenko.mydictionary.UI.Fragments.Web.AdditionItems.BookmarkAdapter;
import ua.kostenko.mydictionary.UI.CommonClasses.DataAccess.BookmarkDataAccess;
import ua.kostenko.mydictionary.UI.CommonClasses.ToolbarHandler;
import ua.kostenko.mydictionary.UI.CommonInterfaces.OnItemClickListener;
import ua.kostenko.mydictionary.UI.CommonInterfaces.OnItemLongClickListener;
import ua.kostenko.mydictionary.UI.CommonInterfaces.OnResultListener;


public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BookmarkAdapter mAdapter;
    private ArrayList<Bookmark> mBookmarks = new ArrayList<>();
    private OnBookmarkOpenCallback mOnBookmarkOpenCallback;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.common_rv_container, container, false);
        mBookmarks = createItems();
        mRecyclerView = createRecyclerView(rootView);
        mAdapter = createAdapter(rootView);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ToolbarHandler.getEditText(ToolbarHandler.getToolbar(getActivity())).setText("");
        ToolbarHandler.setBrowserMode(ToolbarHandler.getToolbar(getActivity()));
    }

    private BookmarkAdapter createAdapter(final View rootView) {
        final BookmarkAdapter adapter = new BookmarkAdapter(mBookmarks);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mOnBookmarkOpenCallback != null)
                    mOnBookmarkOpenCallback.onBookmarkOpenCallback(mBookmarks.get(position).getUrl());
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
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
                                adapter.removeItem(position);
                                BookmarkDataAccess saver = new BookmarkDataAccess(getActivity());
                                saver.save(mBookmarks);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNeutral(dialog);
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        adapter.setOnRemoveListener(new OnResultListener<Bookmark>() {
            @Override
            public void onResult(Bookmark resultItem) {
                Snackbar.make(rootView, R.string.std_removed, Snackbar.LENGTH_LONG).show();
            }
        });
        return adapter;
    }

    private RecyclerView createRecyclerView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.common_rv_container);
        mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);
        return recyclerView;
    }

    private ArrayList<Bookmark> createItems() {
        ArrayList<Bookmark> items;
        BookmarkDataAccess saver = new BookmarkDataAccess(getActivity());
        items = saver.getSavedData();
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setOnBookmarkOpenCallback(OnBookmarkOpenCallback callback) {
        mOnBookmarkOpenCallback = callback;
    }

    public interface OnBookmarkOpenCallback {
        void onBookmarkOpenCallback(String address);
    }
}
