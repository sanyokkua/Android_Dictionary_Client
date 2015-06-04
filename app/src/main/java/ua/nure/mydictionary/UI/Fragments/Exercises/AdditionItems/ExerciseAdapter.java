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

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private ArrayList<Exercise> mExercises;
    private Context context;

    public ExerciseAdapter(ArrayList<Exercise> exercises) {
        mExercises = exercises;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise, parent, false);
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(v);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private ImageView mPicture;

        public ViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.exercise_name_text_view);
            mPicture = (ImageView) view.findViewById(R.id.exercise_image_view);
        }

        public TextView getNameTextView() {
            return mName;
        }

        public ImageView getPictureImageView() {
            return mPicture;
        }
    }
}
