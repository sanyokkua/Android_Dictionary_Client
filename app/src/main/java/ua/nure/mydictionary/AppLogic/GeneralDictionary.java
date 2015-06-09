package ua.nure.mydictionary.AppLogic;

import java.util.Set;
import java.util.TreeSet;

public class GeneralDictionary implements Dictionary {
    private TreeSet<Word> words = new TreeSet<>();
    private String language;
    private String translation;
    private long wordsCount = 0;

    public GeneralDictionary(String language, String translation) {
        this.language = language;
        this.translation = translation;
    }

    public long getWordsCount() {
        return wordsCount;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getTranslateLanguage() {
        return translation;
    }

    @Override
    public Set<Word> getDictionary() {
        return words;
    }

    @Override
    public void addWord(Word word) {
        if (word == null)
            throw new IllegalArgumentException("Word is null");
        words.add(word);
        wordsCount++;
    }

    @Override
    public void addWordSet(Set<Word> words) {
        if (words == null)
            throw new IllegalArgumentException("Words set is null");
        this.words.addAll(words);
        wordsCount += words.size();
    }

    @Override
    public void removeWord(Word removeWord) {
        if (removeWord == null)
            throw new IllegalArgumentException("Word is null");
        words.remove(removeWord);
        wordsCount--;
    }

    @Override
    public void removeWordSet(Set<Word> delWords) {
        if (delWords == null)
            throw new IllegalArgumentException("Word set is null");
        words.removeAll(delWords);
        wordsCount -= delWords.size();
    }
}

