package ua.kostenko.mydictionary.ui.fragments.dictionary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.OnClickCustomListener;
import ua.kostenko.mydictionary.ui.OnLongClickCustomListener;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> {

    @NonNull private final List<Unit> unitList;
    @NonNull private final OnClickCustomListener<Unit> onClickCustomListener;
    @NonNull private final OnLongClickCustomListener<Unit> onLongClickCustomListener;

    public UnitRecyclerViewAdapter(@NonNull final List<Unit> items, @NonNull final OnClickCustomListener<Unit> onClick,
                                   @NonNull final OnLongClickCustomListener<Unit> onLonGClick) {
        Preconditions.checkNotNull(onClick, "You try to set null in OnClickCustomListener");
        Preconditions.checkNotNull(onLonGClick, "You try to set null in OnLongClickCustomListener");
        onClickCustomListener = onClick;
        onLongClickCustomListener = onLonGClick;
        unitList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setFields(unitList.get(position));
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final View view;
        @Bind(R.id.unit_source_text)
        public TextView source;
        @Bind(R.id.unit_translation_text)
        public TextView translation;
        @Bind(R.id.unit_counter_text)
        public TextView counter;
        public Unit unit;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setFields(Unit unit) {
            this.unit = unit;
            source.setText(unit.getSource());
            translation.setText(unit.getTranslations());
            counter.setText(String.valueOf(unit.getCounter()));
        }

        @Override
        public void onClick(View v) {
            Preconditions.checkNotNull(onClickCustomListener, "OnClickCustomListener is not set");
            onClickCustomListener.onItemClick(unit);
        }

        @Override
        public boolean onLongClick(View v) {
            Preconditions.checkNotNull(onLongClickCustomListener, "OnLongClickCustomListener is not set");
            onLongClickCustomListener.onItemLongClick(unit);
            return false;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + translation.getText() + "'";
        }
    }
}
