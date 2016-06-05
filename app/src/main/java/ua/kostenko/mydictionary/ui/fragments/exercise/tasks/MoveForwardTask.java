package ua.kostenko.mydictionary.ui.fragments.exercise.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.exercises.QuestionExercise;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;
import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotNull;

public class MoveForwardTask extends AsyncTask<QuestionExercise, Void, QuestionExercise> {
    private static final String TAG = MoveForwardTask.class.getSimpleName();
    @Bind(R.id.firstTextView) TextView firstTextView;
    @Bind(R.id.secondTextView) TextView secondTextView;
    @Bind(R.id.thirdTextView) TextView thirdTextView;
    @Bind(R.id.fourthTextView) TextView fourthTextView;
    @Bind(R.id.leftNumber) TextView leftNumberTextView;
    @Bind(R.id.rightNumber) TextView rightNumberTextView;
    @Bind(R.id.source_text) TextView sourceTextView;
    private MaterialDialog progressDialog;
    private Map<Integer, String> map;

    public MoveForwardTask(@NonNull final View currentView) {
        checkNotNull(currentView);
        ButterKnife.bind(this, currentView);
        progressDialog = new MaterialDialog.Builder(currentView.getContext())
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
    protected QuestionExercise doInBackground(QuestionExercise... questionExercises) {
        QuestionExercise exercise = questionExercises[0];
        checkNotNull(exercise);
        List<Integer> generated = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            generated.add(i + 1);
        }
        Collections.shuffle(generated);
        Iterator<Integer> iterator = generated.iterator();
        map = new HashMap<>();
        map.put(iterator.next(), exercise.getCurrentUnit().getTranslations());
        while (iterator.hasNext()) {
            map.put(iterator.next(), exercise.getRandomUnit().getTranslations());
        }
        return exercise;
    }

    @Override
    protected void onPostExecute(QuestionExercise exercise) {
        super.onPostExecute(exercise);
        initQuestions(exercise);
        progressDialog.dismiss();
        progressDialog = null;
    }

    private void initQuestions(QuestionExercise questionExercise) {
        leftNumberTextView.setText(String.valueOf(questionExercise.getCurrentPos() + 1));
        rightNumberTextView.setText(String.valueOf(questionExercise.getMaxSize()));
        putUnitsIntoView(questionExercise);
    }

    private void putUnitsIntoView(QuestionExercise questionExercise) {
        for (Map.Entry<Integer, String> unitVariant : map.entrySet()) {
            setTextInTextView(unitVariant);
        }
        if (isNotNull(questionExercise.getCurrentUnit())) {
            sourceTextView.setText(questionExercise.getCurrentUnit().getSource());
        }
    }

    private void setTextInTextView(Map.Entry<Integer, String> unitVariant) {
        switch (unitVariant.getKey()) {
            case 1:
                firstTextView.setText(unitVariant.getValue());
                break;
            case 2:
                secondTextView.setText(unitVariant.getValue());
                break;
            case 3:
                thirdTextView.setText(unitVariant.getValue());
                break;
            case 4:
                fourthTextView.setText(unitVariant.getValue());
                break;
        }
    }
}
