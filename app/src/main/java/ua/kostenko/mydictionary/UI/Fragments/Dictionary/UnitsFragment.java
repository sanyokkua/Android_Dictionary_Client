package ua.kostenko.mydictionary.UI.Fragments.Dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;

public class UnitsFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;

    @Bind(R.id.units_list)
    RecyclerView recyclerViewUnitList;

    public UnitsFragment() {
    }

    public static UnitsFragment newInstance() {
        UnitsFragment fragment = new UnitsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment_units_list, container, false);
        ButterKnife.bind(this, view);
        // Set the adapter
        if (recyclerViewUnitList != null) {
            Context context = view.getContext();
            recyclerViewUnitList.setLayoutManager(new LinearLayoutManager(context));
            //TODO: remove after
            ArrayList<Unit> tempList = new ArrayList<>();
            tempList.add(new Unit("Word", "Слово", 1));
            tempList.add(new Unit("Word2", "Слово2", 2));
            tempList.add(new Unit("Word3", "Слово3", 5));
            tempList.add(new Unit("Word4", "Слово4", 1));
            recyclerViewUnitList.setAdapter(new UnitRecyclerViewAdapter(tempList, mListener));
        }
        return view;
    }

    @OnClick(R.id.new_fab)
    public void fabOnClick(FloatingActionButton floatingActionButton) {
        //Snackbar.make(floatingActionButton, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        openAddWordDialog();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Unit item);
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
                //translateWord(translationEditText, wordEditText.getText().toString());
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
//                        mWords.add(new Word(wordEditText.getText().toString(), translationEditText.getText().toString(),
//                                new Random().nextInt(100)));
//                        mAdapter.notifyDataSetChanged();
                        dialog.cancel();
//                        mEmptyDataTextView.setVisibility(View.INVISIBLE);
//                        mEmptyDataTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).show();
    }
}
