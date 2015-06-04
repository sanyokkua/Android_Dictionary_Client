package ua.nure.mydictionary.UI.Fragments.Dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarCreator;
import ua.nure.mydictionary.UI.SecondaryClasses.Word;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;


public class DictionaryFragment extends Fragment implements Identifier {
    public static final String NAME = "DictionaryFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RVAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private Toolbar mToolbar;
    private ArrayList<Word> mWords = new ArrayList<>();

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dictionary, container, false);

        mToolbar = ToolbarCreator.getToolbar(getActivity());
        mToolbar.setTitle(getString(R.string.title_dictionary_fragment));
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.dictionary_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVAdapter(getTempData(), new RVAdapter.OnRemoveCallback() {
            @Override
            public void removeCallback(Word removed) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.dictionary_fab);
        mFloatingActionButton.attachToRecyclerView(mRecyclerView);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWordDialog();
            }
        });

        return rootView;
    }

    private void openAddWordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_word_dialog, null, false);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();

        EditText word = (EditText) dialogView.findViewById(R.id.foreign_word_edit_text);
        EditText translation = (EditText) dialogView.findViewById(R.id.translation_edit_text);
        Button button = (Button) dialogView.findViewById(R.id.dialog_add_translate_button);
        Button addWord = (Button) dialogView.findViewById(R.id.dialog_add_word_button);
        Button cancel = (Button) dialogView.findViewById(R.id.dialog_dismiss_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private ArrayList<Word> getTempData() {
        for (int i = 0; i < 20; i++) {
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
