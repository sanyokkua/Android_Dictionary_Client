package ua.nure.mydictionary.AppLogic;

import java.util.ArrayList;

public class Word implements Comparable<Word> {
    private String mWord;
    private long mCount;
    private ArrayList<String> mTranslates = new ArrayList<>();
    private LearningState mLearningState;

    public Word(String word) {
        if (word == null || word.length() < 2) {
            throw new IllegalArgumentException("Word must have 2 symbol or above");
        }
        mWord = word;
        mCount = 1;
        mLearningState = LearningState.IS_NOT_STUDIED;
    }

    public Word(String word, long count) {
        this(word);
        this.mCount = count;
    }

    public String getWord() {
        return mWord;
    }

    public long getCount() {
        return mCount;
    }

    public ArrayList<String> getTranslates() {
        return mTranslates;
    }

    public void addTranslation(String translation) {
        if (translation == null || translation.length() < 1)
            throw new IllegalArgumentException("Translation must have 1 symbol or above");
        mTranslates.add(translation);
    }

    public void removeTranslation(String translation) {
        if (translation == null || translation.length() < 1)
            throw new IllegalArgumentException("Translation must have 1 symbol or above");
        mTranslates.remove(translation);
    }

    public void incrementCount() {
        mCount++;
    }

    public void resetCount() {
        mCount = 1;
    }

    @Override
    public String toString() {
        return mWord + "\t: " + mCount;
    }

    @Override
    public int compareTo(Word o) {
        return mWord.compareTo(o.getWord());
    }

    @Override
    public int hashCode() {
        return mWord.hashCode();
    }
}

