package ua.nure.mydictionary.UI.Fragments.Exercises;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.Fragments.Exercises.AdditionItems.Exercise;
import ua.nure.mydictionary.UI.Fragments.Exercises.AdditionItems.ExerciseAdapter;
import ua.nure.mydictionary.UI.SecondaryClasses.ToolbarHandler;
import ua.nure.mydictionary.UI.SecondaryInterfaces.Identifier;

public class ExercisesFragment extends Fragment implements Identifier {
    public static final String NAME = "ExerciseFragment";
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExerciseAdapter mAdapter;
    private ArrayList<Exercise> mExercises = new ArrayList<>();

    public ExercisesFragment() {
        // Required empty public constructor
    }

    public static ExercisesFragment newInstance() {
        ExercisesFragment fragment = new ExercisesFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_rv_container, container, false);
        mToolbar = ToolbarHandler.getToolbar(getActivity());
        ToolbarHandler.setOnlyTitleMode(mToolbar);
        ToolbarHandler.getTitleTextView(mToolbar).setText(getString(R.string.title_exercise_fragment));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.common_rv_container);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mExercises = createItems();// TODO: create real exercises
        mAdapter = new ExerciseAdapter(mExercises);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // TODO:delete before relize
    private ArrayList<Exercise> createItems() {
        ArrayList<Exercise> items = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            items.add(new Exercise("Exercise(" + i + ")", i));
        }
        return items;
    }

    @Override
    public String getIdentifier() {
        return NAME;
    }
}
