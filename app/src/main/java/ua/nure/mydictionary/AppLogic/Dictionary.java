package ua.nure.mydictionary.AppLogic;

import java.util.Set;

public interface Dictionary {
    String getLanguage();

    String getTranslateLanguage();

    Set<Word> getDictionary();

    void addWord(Word word);

    void addWordSet(Set<Word> words);

    void removeWord(Word delWord);

    void removeWordSet(Set<Word> delWords);

    public long getWordsCount();
}
