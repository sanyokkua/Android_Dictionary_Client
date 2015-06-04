package ua.nure.mydictionary.UI.SecondaryClasses;

public class Word {
    private String mWord;
    private String mTranslation;
    private int mCount;

    public Word(String word, String translation, int count) {
        mWord = word;
        mTranslation = translation;
        mCount = count;
    }

    public String getWord() {
        return mWord;
    }

    public String getTranslation() {
        return mTranslation;
    }

    public int getCount() {
        return mCount;
    }

}
