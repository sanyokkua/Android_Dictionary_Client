package ua.kostenko.mydictionary.UI.Fragments.Dictionary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.Fragments.Dictionary.UnitsFragment.OnListFragmentInteractionListener;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> {

    private final List<Unit> unitList;
    private final OnListFragmentInteractionListener onListFragmentInteractionListener;

    public UnitRecyclerViewAdapter(List<Unit> items, OnListFragmentInteractionListener listener) {
        unitList = items;
        onListFragmentInteractionListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_fragment_item_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.unit = unitList.get(position);
        holder.setFields();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onListFragmentInteractionListener) {
                    onListFragmentInteractionListener.onListFragmentInteraction(holder.unit);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        @Bind(R.id.new_unit_source)
        public TextView source;
        @Bind(R.id.new_unit_translation)
        public TextView translation;
        @Bind(R.id.new_unit_counter)
        public TextView counter;
        public Unit unit;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void setFields() {
            source.setText(unit.getSource());
            translation.setText(unit.getTranslations());
            counter.setText(String.valueOf(unit.getCounter()));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + translation.getText() + "'";
        }
    }
}
