package ua.kostenko.mydictionary.ui.fragments.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.OnClickCustomListener;
import ua.kostenko.mydictionary.ui.OnLongClickCustomListener;
import ua.kostenko.mydictionary.ui.dialogs.UnitCreateDialog;

public class UnitsFragment extends Fragment {
    private static final String TAG = UnitsFragment.class.getSimpleName();
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
            UnitRecyclerViewAdapter adapter = new UnitRecyclerViewAdapter(loadData(),
                    new OnClickCustomListener<Unit>() {
                        @Override
                        public void onItemClick(Unit item) {
                            onClick(item);
                        }
                    },
                    new OnLongClickCustomListener<Unit>() {
                        @Override
                        public void onItemLongClick(Unit item) {
                            onLongClick(item);
                        }
                    });
            recyclerViewUnitList.setAdapter(adapter);
        }
    }

    @NonNull
    private List<Unit> loadData() {
        final List<Unit> tempList = unitDao.findAll();
        return Collections.unmodifiableList(tempList);
    }

    private void onClick(@NonNull final Unit item) { //TODO: add realization
        Snackbar.make(recyclerViewUnitList, item.toString() + " Click", Snackbar.LENGTH_LONG).show();
    }

    private void onLongClick(@NonNull final Unit item) { //TODO: add realization
        Snackbar.make(recyclerViewUnitList, item.toString() + " Long Click", Snackbar.LENGTH_LONG).show();
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
        UnitCreateDialog unitCreateDialog = new UnitCreateDialog(getActivity(), inflater);
        unitCreateDialog.show();
    }
}
