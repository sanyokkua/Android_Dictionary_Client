package ua.nure.mydictionary.UI.Fragments.Dictionary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.SecondaryClasses.Word;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private ArrayList<Word> mWords;
    private OnRemoveCallback mOnRemoveCallback;

    public RVAdapter(ArrayList<Word> words) {
        mWords = words;
    }

    public RVAdapter(ArrayList<Word> words, OnRemoveCallback callback) {
        mWords = words;
        mOnRemoveCallback = callback;
    }

    public void setOnRemoveCallback(OnRemoveCallback callback) {
        mOnRemoveCallback = callback;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_word, parent, false);
        RVAdapter.ViewHolder viewHolder = new RVAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getWordTextView().setText(mWords.get(position).getWord());
        holder.getTranslationTextView().setText(mWords.get(position).getTranslation());
        holder.getCountTextView().setText(mWords.get(position).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public void removeItem(int position) {
        if (mOnRemoveCallback != null) {
            mOnRemoveCallback.removeCallback(mWords.get(position));
        }
        mWords.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mWordTextView;
        private TextView mTranslationTextView;
        private TextView mCountTextView;

        public ViewHolder(View view) {
            super(view);
            mWordTextView = (TextView) view.findViewById(R.id.dictionary_word_text_view);
            mTranslationTextView = (TextView) view.findViewById(R.id.dictionary_translation_text_view);
            mCountTextView = (TextView) view.findViewById(R.id.dictionary_count_text_view);
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
    }

    public interface OnRemoveCallback {
        void removeCallback(Word removed);
    }
}
