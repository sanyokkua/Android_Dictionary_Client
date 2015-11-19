package ua.kostenko.mydictionary.UI.Fragments.Dictionary;

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
import ua.kostenko.mydictionary.AppLogic.Dictionary;
import ua.kostenko.mydictionary.AppLogic.GeneralDictionary;
import ua.kostenko.mydictionary.AppLogic.yandex.Language;
import ua.kostenko.mydictionary.AppLogic.yandex.Translate;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.Activities.MainActivity;
import ua.kostenko.mydictionary.UI.CommonClasses.DataAccess.WordDataAccess;
import ua.kostenko.mydictionary.UI.CommonClasses.ToolbarHandler;
import ua.kostenko.mydictionary.UI.CommonClasses.Word;
import ua.kostenko.mydictionary.UI.CommonInterfaces.Identifier;
import ua.kostenko.mydictionary.UI.CommonInterfaces.OnItemLongClickListener;
import ua.kostenko.mydictionary.UI.CommonInterfaces.OnResultListener;


public class DictionaryFragment extends Fragment implements Identifier {
    public static final String NAME = "DictionaryFragment";
    private static final String API_KEY = "trnsl.1.1.20150522T195930Z.010ab143cb4576f2.568a0965d1a708e331f7af72fc325e30861ff083";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private Toolbar mToolbar;
    private ArrayList<Word> mWords = new ArrayList<>();
    private TextView mEmptyDataTextView;
    private String result = null;
    private Dictionary mDictionary = new GeneralDictionary("english", "russian");

    public DictionaryFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();
        return fragment;
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
        initRecyclerViewAdapter(rootView, inflater, container);
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

    private void initRecyclerViewAdapter(final View rootView, final LayoutInflater inflater, final ViewGroup container) {
        mAdapter = new RecyclerViewAdapter(mWords, new OnResultListener<Word>() {
            @Override
            public void onResult(Word resultItem) {
                Snackbar.make(rootView, "Deleted:" + resultItem.getWord(), Snackbar.LENGTH_LONG).show();
            }
        }, new OnResultListener<Word>() {
            @Override
            public void onResult(Word resultItem) {
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
                                Log.d(MainActivity.LOG_TAG, "mWords.Size: " + mWords.size());
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
        View dialogView = inflater.inflate(R.layout.dialog_add_word, null, false);
        final EditText wordEditText = (EditText) dialogView.findViewById(R.id.foreign_word_edit_text);
        final EditText translationEditText = (EditText) dialogView.findViewById(R.id.translation_edit_text);
        final Button translateButton = (Button) dialogView.findViewById(R.id.dialog_add_translate_button);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateWord(translationEditText, wordEditText.getText().toString());
            }
        });
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
                        mWords.add(new Word(wordEditText.getText().toString(), translationEditText.getText().toString(),
                                new Random().nextInt(100)));
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

    private void translateWord(final EditText translationEditText, final String word) {
        Translate.setKey(API_KEY);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result = Translate.execute(word, Language.ENGLISH, Language.RUSSIAN);
                } catch (Exception ex) {
                    Log.e(MainActivity.LOG_TAG, ex.toString());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        translationEditText.setText(result);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        WordDataAccess wordDataAccess = new WordDataAccess(getActivity());
        wordDataAccess.save(mWords);
    }

    @Override
    public void onResume() {
        super.onResume();
        ToolbarHandler.setOnlyTitleMode(mToolbar);
        WordDataAccess wordDataAccess = new WordDataAccess(getActivity());
        mWords.addAll(wordDataAccess.getSavedData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public String getIdentifier() {
        return NAME;
    }
}
