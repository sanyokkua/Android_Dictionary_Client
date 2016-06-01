package ua.kostenko.mydictionary.ui.fragments.drilling;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.ui.fragments.BaseFragment;

public class DrillingFragment extends BaseFragment {
    private static final String TAG = DrillingFragment.class.getSimpleName();

    public DrillingFragment() {
    }

    public static DrillingFragment newInstance() {
        DrillingFragment fragment = new DrillingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drilling, container, false);
    }

}
