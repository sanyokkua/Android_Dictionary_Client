package ua.kostenko.mydictionary.ui.fragments.exercise;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.exercises.QuestionExercise;
import ua.kostenko.mydictionary.ui.fragments.BaseFragment;
import ua.kostenko.mydictionary.ui.fragments.exercise.tasks.InitExerciseTask;
import ua.kostenko.mydictionary.ui.fragments.exercise.tasks.MoveForwardTask;
import ua.kostenko.mydictionary.ui.iterfaces.OnFinish;
import ua.kostenko.mydictionary.ui.utils.SnackBarHelper;

public class ExerciseFragment extends BaseFragment {
    private static final String TAG = ExerciseFragment.class.getSimpleName();
    @Bind(R.id.firstTextView) TextView firstTextView;
    @Bind(R.id.secondTextView) TextView secondTextView;
    @Bind(R.id.thirdTextView) TextView thirdTextView;
    @Bind(R.id.fourthTextView) TextView fourthTextView;
    @Bind(R.id.leftNumber) TextView leftNumberTextView;
    @Bind(R.id.rightNumber) TextView rightNumberTextView;
    @Bind(R.id.source_text) TextView sourceTextView;
    @Bind(R.id.exerciseLayout) LinearLayout exerciseLayout;
    @Bind(R.id.exerciseLayoutGone) FrameLayout exerciseLayoutGone;
    @BindString(R.string.fragment_exercise_correct_answer) String correctAnswer;
    @BindString(R.string.fragment_exercise_wrong_answer) String wrongAnswer;
    @Inject UnitDao unitDao;
    private QuestionExercise questionExercise;
    private View inflatedCurrentView;
    private boolean hasEnoughUnits;

    public ExerciseFragment() {
    }

    public static ExerciseFragment newInstance() {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.fragment_exercise, container, false);
        ButterKnife.bind(this, inflateView);
        inflatedCurrentView = inflateView;
        hasEnoughUnits = unitDao.findAll().size() > 4;
        if (hasEnoughUnits) {
            questionExercise = new QuestionExercise(new OnExerciseRestart() {
                @Override public void onRestart() {
                    reInitExercise();
                }
            });
        } else {
            exerciseLayout.setVisibility(View.GONE);
            exerciseLayoutGone.setVisibility(View.VISIBLE);
        }
        return inflateView;
    }

    private void reInitExercise() {
        InitExerciseTask initExerciseTask = new InitExerciseTask(getContext(), new OnFinish() {
            @Override public void onFinish() {
                moveNext();
            }
        });
        initExerciseTask.execute(questionExercise);
    }

    private void moveNext() {
        MoveForwardTask moveForwardTask = new MoveForwardTask(inflatedCurrentView);
        moveForwardTask.execute(questionExercise);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasEnoughUnits) {
            reInitExercise();
        }
    }

    @OnClick(R.id.first) void onFirstClick() {
        String text = firstTextView.getText().toString();
        checkAnswer(text);
    }

    @OnClick(R.id.second) void onSecondClick() {
        String text = secondTextView.getText().toString();
        checkAnswer(text);
    }

    @OnClick(R.id.third) void onThirdClick() {
        String text = thirdTextView.getText().toString();
        checkAnswer(text);
    }

    @OnClick(R.id.fourth) void onFourthClick() {
        String text = fourthTextView.getText().toString();
        checkAnswer(text);
    }

    private void checkAnswer(String text) {
        if (hasEnoughUnits) {
            String source = sourceTextView.getText().toString();
            boolean answerResult = questionExercise.answer(source, text);
            String result = answerResult ? correctAnswer : wrongAnswer;
            SnackBarHelper.make(inflatedCurrentView, result, Snackbar.LENGTH_SHORT).show();
            moveNext();
        }
    }
}
