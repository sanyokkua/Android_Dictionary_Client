package ua.kostenko.mydictionary.core.dao;

import java.util.List;

import ua.nure.mydictionary.AppLogic.Word;


public interface WordsDao {
    boolean saveWord(Word word);

    boolean removeWord(Word word);

    Word findByTranslation(String translation);

    List<Word> findAll();
}
