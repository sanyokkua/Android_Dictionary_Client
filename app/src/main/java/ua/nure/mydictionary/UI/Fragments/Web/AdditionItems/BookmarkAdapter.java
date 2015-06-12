package ua.nure.mydictionary.UI.Fragments.Web.AdditionItems;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.nure.mydictionary.R;
import ua.nure.mydictionary.UI.CommonClasses.ImageFinder;
import ua.nure.mydictionary.UI.CommonInterfaces.OnItemClickListener;
import ua.nure.mydictionary.UI.CommonInterfaces.OnItemLongClickListener;
import ua.nure.mydictionary.UI.CommonInterfaces.OnResultListener;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private ArrayList<Bookmark> mBookmarks;
    private Context context;
    private OnResultListener<Bookmark> mOnRemoveCallback;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BookmarkAdapter(ArrayList<Bookmark> bookmarks) {
        mBookmarks = bookmarks;
    }

    public void setOnRemoveListener(OnResultListener<Bookmark> callback) {
        mOnRemoveCallback = callback;
    }

    public void setOnItemClickListener(OnItemClickListener callback) {
        mOnItemClickListener = callback;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener callback) {
        mOnItemLongClickListener = callback;
    }

    public void removeItem(int position) {
        if (mOnRemoveCallback != null) {
            mOnRemoveCallback.onResult(mBookmarks.get(position));
        }
        mBookmarks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mBookmarks.size());
        notifyDataSetChanged();
    }

    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark, parent, false);
        BookmarkAdapter.ViewHolder viewHolder = new BookmarkAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getHeaderTextView().setText(mBookmarks.get(position).getHeader());
        holder.getUrlTextView().setText(mBookmarks.get(position).getUrl().toString());
        Drawable image = ImageFinder.findImageById(mBookmarks.get(position).getPictureId(), context);
        holder.getPictureImageView().setImageDrawable(image);
    }

    @Override
    public int getItemCount() {
        return mBookmarks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mHeader;
        private TextView mUrl;
        private ImageView mPicture;

        public ViewHolder(View view) {
            super(view);
            mHeader = (TextView) view.findViewById(R.id.bookmark_header_text_view);
            mUrl = (TextView) view.findViewById(R.id.bookmark_url_text_view);
            mPicture = (ImageView) view.findViewById(R.id.bookmark_image_view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public TextView getHeaderTextView() {
            return mHeader;
        }

        public TextView getUrlTextView() {
            return mUrl;
        }

        public ImageView getPictureImageView() {
            return mPicture;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null)
                mOnItemLongClickListener.onItemLongClick(v, getAdapterPosition());
            return false;
        }
    }
}
