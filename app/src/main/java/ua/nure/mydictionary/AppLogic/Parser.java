package ua.nure.mydictionary.AppLogic;

import java.io.File;
import java.util.Set;

public interface Parser {
    void parse(File file);

    Set<Word> getWordSet();
}