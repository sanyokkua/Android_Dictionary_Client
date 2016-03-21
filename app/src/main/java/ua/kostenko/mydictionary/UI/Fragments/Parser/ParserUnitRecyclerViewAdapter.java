package ua.kostenko.mydictionary.ui.fragments.parser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;
import ua.kostenko.mydictionary.ui.OnClickCustomListener;


public class ParserUnitRecyclerViewAdapter extends RecyclerView.Adapter<ParserUnitRecyclerViewAdapter.ViewHolder> {

    @NonNull private final List<ParserUnit> parserUnitList = new ArrayList<>();
    @NonNull private final OnClickCustomListener<ParserUnit> onClickCustomListener;

    public ParserUnitRecyclerViewAdapter(@NonNull final List<ParserUnit> items,
                                         @NonNull final OnClickCustomListener<ParserUnit> onClick) {
        Preconditions.checkNotNull(onClick, "You try to set null in OnClickCustomListener");
        onClickCustomListener = onClick;
        parserUnitList.addAll(items);
    }

    @Override
    public ParserUnitRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parser_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setFields(parserUnitList.get(position));
    }

    @Override
    public int getItemCount() {
        return parserUnitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View view;
        @Bind(R.id.source_text)
        public TextView source;
        @Bind(R.id.counter)
        public TextView counter;
        public ParserUnit parserUnit;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void setFields(@NonNull final ParserUnit parserUnit) {
            this.parserUnit = parserUnit;
            source.setText(parserUnit.getSource());
            counter.setText(String.valueOf(parserUnit.getCounter()));
        }

        @Override
        public void onClick(View v) {
            Preconditions.checkNotNull(onClickCustomListener, "OnClickCustomListener is not set");
            onClickCustomListener.onItemClick(parserUnit);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + parserUnit + "'";
        }
    }
}
