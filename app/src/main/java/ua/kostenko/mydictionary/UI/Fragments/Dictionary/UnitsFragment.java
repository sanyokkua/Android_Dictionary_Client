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
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.OnClickCustomListener;
import ua.kostenko.mydictionary.UI.OnLongClickCustomListener;
import ua.kostenko.mydictionary.UI.UnitCreateDialog;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;

public class UnitsFragment extends Fragment {

    @Bind(R.id.units_list) RecyclerView recyclerViewUnitList;

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
            for (int i = 0; i < 100; i++) {
                tempList.add(new Unit("Word" + i, "Слово" + i, i));
            }
            UnitRecyclerViewAdapter adapter = new UnitRecyclerViewAdapter(tempList,
                    new OnClickCustomListener<Unit>() {
                        @Override
                        public void onItemClick(Unit item) {
                            Toast.makeText(getActivity(), item.toString() + " Click", Toast.LENGTH_LONG).show();
                        }
                    },
                    new OnLongClickCustomListener<Unit>() {
                        @Override
                        public void onItemLongClick(Unit item) {
                            Toast.makeText(getActivity(), item.toString() + " Long Click", Toast.LENGTH_LONG).show();
                        }
                    });
            recyclerViewUnitList.setAdapter(adapter);
        }
        return view;
    }

    @OnClick(R.id.new_fab)
    public void fabOnClick(FloatingActionButton floatingActionButton) {
        openAddWordDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void openAddWordDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        UnitCreateDialog unitCreateDialog = new UnitCreateDialog(getActivity(), inflater);
        unitCreateDialog.getDialog().show();
    }
}
