package ua.kostenko.mydictionary.ui.fragments.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.ui.dialogs.UnitCreateDialog;
import ua.kostenko.mydictionary.ui.fragments.BaseFragment;

public class UnitsFragment extends BaseFragment {
    private static final String TAG = UnitsFragment.class.getSimpleName();
    private UnitRecyclerViewAdapter adapter;
    @Bind(R.id.units_list) RecyclerView recyclerViewUnitList;
    @Inject UnitDao unitDao;

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
        App.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list_view, container, false);
        ButterKnife.bind(this, view);
        setTheAdapterAndData(view);
        return view;
    }

    private void setTheAdapterAndData(@NonNull final View view) {
        if (recyclerViewUnitList != null) {
            Context context = view.getContext();
            recyclerViewUnitList.setLayoutManager(new LinearLayoutManager(context));
            adapter = new UnitRecyclerViewAdapter();
            recyclerViewUnitList.setAdapter(adapter);
        }
    }

    @OnClick(R.id.fab)
    public void fabOnClick(FloatingActionButton floatingActionButton) {
        openAddWordDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void openAddWordDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        UnitCreateDialog unitCreateDialog = new UnitCreateDialog(getActivity(), inflater, adapter);
        unitCreateDialog.show();
    }

    @Override
    public void onResume() {
        adapter.update();
        super.onResume();
    }
}
