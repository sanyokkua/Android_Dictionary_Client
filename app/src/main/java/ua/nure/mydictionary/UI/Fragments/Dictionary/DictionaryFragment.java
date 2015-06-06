package ua.nure.mydictionary.UI.Fragments.Dictionary;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarHandler;
import ua.nure.mydictionary.UI.SecondaryClasses.Word;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemLongClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnResultCallback;


public class DictionaryFragment extends Fragment implements Identifier {
    public static final String NAME = "DictionaryFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RVAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private Toolbar mToolbar;
    private ArrayList<Word> mWords = new ArrayList<>();
    private TextView mEmptyDataTextView;

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);
        mToolbar = ToolbarHandler.getToolbar(getActivity());
        ToolbarHandler.setOnlyTitleMode(mToolbar);
        ToolbarHandler.getTitleTextView(mToolbar).setText(getString(R.string.title_dictionary_fragment));
        initRecyclerView(rootView);
        mEmptyDataTextView = (TextView) rootView.findViewById(R.id.dictionary_empty_text_view);
        initRVAdapter(rootView, inflater, container);
        mRecyclerView.setAdapter(mAdapter);
        initFab(rootView);
        return rootView;
    }

    private void initRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dictionary_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new ScaleInAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(1000);
        mRecyclerView.getItemAnimator().setRemoveDuration(1000);
        mRecyclerView.getItemAnimator().setMoveDuration(1000);
        mRecyclerView.getItemAnimator().setChangeDuration(1000);
    }

    private void initRVAdapter(final View rootView, final LayoutInflater inflater, final ViewGroup container) {
        mAdapter = new RVAdapter(getTempData(), new OnResultCallback<Word>() {
            @Override
            public void resultCallback(Word resultItem) {
                Snackbar.make(rootView, "Deleted:" + resultItem.getWord(), Snackbar.LENGTH_LONG).show();
            }
        }, new OnResultCallback<Word>() {
            @Override
            public void resultCallback(Word resultItem) {
                View wordInfo = inflater.inflate(R.layout.dialog_word_info, container, false);
                TextView word = (TextView) wordInfo.findViewById(R.id.word_info_word_text_view);
                TextView translation = (TextView) wordInfo.findViewById(R.id.word_info_translation_text_view);
                TextView count = (TextView) wordInfo.findViewById(R.id.word_info_Count_text_view);
                word.setText(resultItem.getWord());
                translation.setText(resultItem.getTranslation());
                count.setText(resultItem.getCount() + "");
                new MaterialDialog.Builder(getActivity())
                        .customView(wordInfo, true)
                        .positiveText(getActivity().getResources()
                                .getString(R.string.std_ok))
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onNeutral(dialog);
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                //Toast.makeText(getActivity(), "Click!): ", Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                new MaterialDialog.Builder(getActivity())
                        .content(getActivity().getResources()
                                .getString(R.string.dictionary_del_dialog_title))
                        .positiveText(getActivity().getResources()
                                .getString(R.string.dictionary_del))
                        .negativeText(getActivity().getResources()
                                .getString(R.string.std_cancel))
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                mAdapter.removeItem(position);
                                Log.d("MyAppRESULT", "mWords.Size: " + mWords.size());
                                if (mWords.isEmpty()) {
                                    mEmptyDataTextView.setVisibility(View.VISIBLE);
                                } else {
                                    mEmptyDataTextView.setVisibility(View.INVISIBLE);
                                    mEmptyDataTextView.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNeutral(dialog);
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void initFab(View rootView) {
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.dictionary_fab);
        mFloatingActionButton.attachToRecyclerView(mRecyclerView);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWordDialog();
            }
        });
    }

    private void openAddWordDialog() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_word_dialog, null, false);
        final EditText word = (EditText) dialogView.findViewById(R.id.foreign_word_edit_text);
        final EditText translation = (EditText) dialogView.findViewById(R.id.translation_edit_text);
        Button translate = (Button) dialogView.findViewById(R.id.dialog_add_translate_button);

        new MaterialDialog.Builder(getActivity())
                .customView(dialogView, false)
                .positiveText(getActivity().getResources()
                        .getString(R.string.dictionary_add))
                .negativeText(getActivity().getResources()
                        .getString(R.string.std_cancel))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        mWords.add(new Word(word.getText().toString(), translation.getText().toString(), new Random().nextInt(100)));
                        mAdapter.notifyDataSetChanged();
                        dialog.cancel();
                        mEmptyDataTextView.setVisibility(View.INVISIBLE);
                        mEmptyDataTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).show();
    }

    private ArrayList<Word> getTempData() {
        for (int i = 0; i < 1; i++) {
            mWords.add(new Word("Word_" + i, "Translation_" + i, i * 11 % 10));
        }
        return mWords;
    }

    public static Fragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();
        //Bundle bundle = new Bundle();
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getIdentifier() {
        return NAME;
    }
}
