package ua.kostenko.mydictionary.core.local.exercises;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.core.commonutils.Utils;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.fragments.exercise.OnExerciseRestart;

import static java.lang.Math.abs;
import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotEmpty;

public class QuestionExercise {
    private static final int MAX_UNITS_NUMBER_PER_EXERCISE = 20;
    private final OnExerciseRestart onLast;
    @Inject UnitDao unitDao;
    private Random random;
    private List<Unit> all;
    private List<Unit> units;
    private Iterator<Unit> unitIterator;
    private Unit current;
    private int currentPosition;

    public QuestionExercise(@NonNull final OnExerciseRestart onLast) {
        App.getAppComponent().inject(this);
        Utils.checkNotNull(onLast);
        this.onLast = onLast;
        random = new Random(System.currentTimeMillis());
    }

    public void init() {
        all = unitDao.findAll();
        if (isNotEmpty(all)) {
            units = getPartForExercise(all);
            unitIterator = units.iterator();
            currentPosition = 0;
            current = unitIterator.next();
        }
    }

    private List<Unit> getPartForExercise(List<Unit> all) {
        int counter = 0;
        List<Unit> unitList = new LinkedList<>();
        Iterator<Unit> iterator = all.iterator();
        while (iterator.hasNext() && counter < getMaxSize()) {
            unitList.add(iterator.next());
            counter++;
        }
        return unitList;
    }

    public int getMaxSize() {
        return Math.min(all.size(), MAX_UNITS_NUMBER_PER_EXERCISE);
    }

    private void moveForward() {
        if (currentPosition + 1 == getMaxSize()) {
            onLast.onRestart();
        } else if (unitIterator.hasNext()) {
            current = unitIterator.next();
            currentPosition++;
        }
    }

    public int getCurrentPos() {
        return currentPosition;
    }

    public Unit getCurrentUnit() {
        return current;
    }

    public Unit getRandomUnit() {
        Unit unit = units.get(getNextRandomInteger());
        while (unit.getSource().equals(current.getSource())) {
            unit = units.get(getNextRandomInteger());
        }
        return unit;
    }

    private int getNextRandomInteger() {
        return abs(random.nextInt()) % getMaxSize();
    }

    public boolean answer(@NonNull String source, @NonNull String answer) {
        Unit unit = unitDao.findBySource(source);
        boolean result = false;
        if (unit.getTranslations().equals(answer)) {
            correctAnswer(unit);
            result = true;
        } else {
            wrongAnswer(unit);
        }
        moveForward();
        return result;
    }

    private void correctAnswer(@NonNull Unit unit) {
        unit.setCounter(-1);
        unitDao.saveUnit(unit);
    }

    private void wrongAnswer(@NonNull Unit unit) {
        unit.incrementCounter();
        unitDao.saveUnit(unit);
    }
}
