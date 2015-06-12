package ua.nure.mydictionary.AppLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class BrutTextParser implements Parser {
    private Set<Word> mWords = new TreeSet<>();
    private Throwable mMainException;

    @Override
    public void parse(File file) {
        if (file == null)
            throw new IllegalArgumentException("File is null");
        if (!file.exists())
            throw new IllegalArgumentException("File is not exist");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            Map<String, Long> wordsMap = new HashMap<>();
            try {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] words = line.split("(\\d|\\s)");
                    for (String tmpWord : words) {
                        tmpWord = deleteSymbols(tmpWord);
                        if (!wordsMap.containsKey(tmpWord))
                            wordsMap.put(tmpWord, 1l);
                        else {
                            Long counter = wordsMap.get(tmpWord);
                            wordsMap.put(tmpWord, (counter == null) ? 1 : counter + 1);
                        }
                    }
                }
                fillWordsMap(wordsMap);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mMainException = e;
            throw new RuntimeException(e.getMessage() + "FILE not found!");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex + mMainException.getMessage());
            }
        }
    }

    private void fillWordsMap(Map<String, Long> wordsMap) {
        Set<Map.Entry<String, Long>> entrys = wordsMap.entrySet();
        for (Map.Entry<String, Long> entry : entrys) {
            try {
                Word word = new Word(entry.getKey(), entry.getValue());
                mWords.add(word);
            } catch (IllegalArgumentException ex) {
                continue;
            }
        }
    }

    private String deleteSymbols(String line) {
        String symbols = "{}!@#$%^&*();:|_-+=*/?\"\',.~\\<> \t\n\r";
        line = line.replaceAll("<.+/?>", "\t");
        line = line.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (symbols.indexOf(line.charAt(i)) < 0)
                builder.append(line.charAt(i));
        }
        return builder.toString();
    }

    @Override
    public Set<Word> getWordSet() {
        return mWords;
    }
}
