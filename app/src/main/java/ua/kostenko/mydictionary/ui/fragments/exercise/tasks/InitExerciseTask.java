package ua.kostenko.mydictionary.ui.fragments.exercise.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.exercises.QuestionExercise;
import ua.kostenko.mydictionary.ui.iterfaces.OnFinish;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;

public class InitExerciseTask extends AsyncTask<QuestionExercise, Void, Void> {
    private static final String TAG = InitExerciseTask.class.getSimpleName();
    private final OnFinish onUpdate;
    private MaterialDialog progressDialog;

    public InitExerciseTask(@NonNull final Context context, @NonNull final OnFinish onFinish) {
        checkNotNull(context);
        checkNotNull(onFinish);
        this.onUpdate = onFinish;
        progressDialog = new MaterialDialog.Builder(context)
                .title(R.string.fragment_exercise_next_unit_dialog_wait)
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .build();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(QuestionExercise... questionExercises) {
        QuestionExercise exercise = questionExercises[0];
        checkNotNull(exercise);
        exercise.init();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        progressDialog = null;
        onUpdate.onFinish();
    }
}
