package ua.nure.mydictionary.UI.Fragments.Exercises.AdditionItems;

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
import ua.nure.mydictionary.UI.SecondaryClasses.ImageFinder;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnItemClickListener;
import ua.nure.mydictionary.UI.SecondaryInterfaces.OnResultCallback;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private static ViewHolder sLastViewHolder = null;
    private ArrayList<Exercise> mExercises;
    private Context context;
    private OnResultCallback<Exercise> mOnResultCallback;
    private OnItemClickListener mOnItemClickListener;

    public ExerciseAdapter(ArrayList<Exercise> exercises) {
        mExercises = exercises;
    }

    public void setOnRemoveCallback(OnResultCallback callback) {
        mOnResultCallback = callback;
    }

    public void setOnItemClickListener(OnItemClickListener callback) {
        mOnItemClickListener = callback;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise, parent, false);
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(v);
        sLastViewHolder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getNameTextView().setText(mExercises.get(position).getName());
        Drawable image = ImageFinder.findImageById(0, context);
        holder.getPictureImageView().setImageDrawable(image);
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mName;
        private ImageView mPicture;

        public ViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.exercise_name_text_view);
            mPicture = (ImageView) view.findViewById(R.id.exercise_image_view);
            view.setOnClickListener(this);
        }

        public TextView getNameTextView() {
            return mName;
        }

        public ImageView getPictureImageView() {
            return mPicture;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
        }

    }
}
