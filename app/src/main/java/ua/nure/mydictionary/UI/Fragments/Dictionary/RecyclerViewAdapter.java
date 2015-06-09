package ua.nure.mydictionary.UI.Fragments.Dictionary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.Word;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemLongClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnResultCallback;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Word> mWords;
    private OnResultCallback<Word> mOnRemoveCallback;
    private OnResultCallback<Word> mOnItemClickCallback;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean isLastRepeated = false;

    public RecyclerViewAdapter(ArrayList<Word> words, OnResultCallback callback) {
        mWords = words;
        mOnRemoveCallback = callback;
    }

    public RecyclerViewAdapter(ArrayList<Word> words, OnResultCallback<Word> onRemoveCallback, OnResultCallback<Word> onItemClickCallback) {
        mWords = words;
        mOnRemoveCallback = onRemoveCallback;
        mOnItemClickCallback = onItemClickCallback;
    }

    public RecyclerViewAdapter(ArrayList<Word> words) {
        mWords = words;
    }

    public void setOnRemoveCallback(OnResultCallback<Word> callback) {
        mOnRemoveCallback = callback;
    }

    public void setOnOnItemClickCallback(OnResultCallback<Word> callback) {
        mOnItemClickCallback = callback;
    }

    public void setOnItemClickListener(OnItemClickListener callback) {
        mOnItemClickListener = callback;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener callback) {
        mOnItemLongClickListener = callback;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_word, parent, false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getWordTextView().setText(mWords.get(position).getWord());
        holder.getTranslationTextView().setText(mWords.get(position).getTranslation());
        holder.getCountTextView().setText(mWords.get(position).getCount() + "");
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void removeItem(int position) {
        if (position < mWords.size()) {
            if (mOnRemoveCallback != null) {
                mOnRemoveCallback.resultCallback(mWords.get(position));
            }
            mWords.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mWords.size());
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mWordTextView;
        private TextView mTranslationTextView;
        private TextView mCountTextView;

        public ViewHolder(View view) {
            super(view);
            mWordTextView = (TextView) view.findViewById(R.id.dictionary_word_text_view);
            mTranslationTextView = (TextView) view.findViewById(R.id.dictionary_translation_text_view);
            mCountTextView = (TextView) view.findViewById(R.id.dictionary_count_text_view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public TextView getWordTextView() {
            return mWordTextView;
        }

        public TextView getTranslationTextView() {
            return mTranslationTextView;
        }

        public TextView getCountTextView() {
            return mCountTextView;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            if (mOnItemClickCallback != null)
                mOnItemClickCallback.resultCallback(mWords.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null)
                mOnItemLongClickListener.onItemLongClick(v, getAdapterPosition());
            return false;
        }
    }
}
