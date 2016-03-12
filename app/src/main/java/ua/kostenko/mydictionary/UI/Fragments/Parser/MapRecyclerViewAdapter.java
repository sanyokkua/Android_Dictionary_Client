package ua.kostenko.mydictionary.UI.Fragments.Parser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.OnClickCustomListener;


public class MapRecyclerViewAdapter extends RecyclerView.Adapter<MapRecyclerViewAdapter.ViewHolder> {

    @NonNull private final List<Map.Entry<String, Long>> entriesList = new ArrayList<>();
    @NonNull private final OnClickCustomListener<Map.Entry<String, Long>> onClickCustomListener;

    public MapRecyclerViewAdapter(@NonNull final List<Map.Entry<String, Long>> items,
                                  @NonNull final OnClickCustomListener<Map.Entry<String, Long>> onClick) {
        Preconditions.checkNotNull(onClick, "You try to set null in OnClickCustomListener");
        onClickCustomListener = onClick;
        entriesList.addAll(items);
    }

    @Override
    public MapRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_parser_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setFields(entriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View view;
        @Bind(R.id.source_text)
        public TextView source;
        @Bind(R.id.counter)
        public TextView counter;
        Map.Entry<String, Long> entry;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void setFields(Map.Entry<String, Long> entry) {
            this.entry = entry;
            source.setText(entry.getKey());
            counter.setText(String.valueOf(entry.getValue()));
        }

        @Override
        public void onClick(View v) {
            Preconditions.checkNotNull(onClickCustomListener, "OnClickCustomListener is not set");
            onClickCustomListener.onItemClick(entry);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + entry + "'";
        }
    }
}
