package ua.kostenko.mydictionary.UI.CommonClasses.DataAccess;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ua.kostenko.mydictionary.UI.CommonClasses.Word;


public class WordDataAccess {
    private final static String FILE_NAME = "Words.words";
    private Context mContext;
    private Throwable mainException;

    public WordDataAccess(Context context) {
        mContext = context;
    }

    public void save(ArrayList<Word> words) {
        File file = new File(mContext.getFilesDir(), FILE_NAME);

        file.delete();

        createFile(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)));
            int counter = 0;
            for (Word word : words) {
                writer.write(counter + "");
                writer.write(";");
                writer.write(word.getWord());
                writer.write(";");
                writer.write(word.getTranslation());
                writer.write(";");
                writer.write(word.getCount() + "");
                writer.write(";");
                writer.newLine();
            }

        } catch (IOException ex) {
            mainException = ex;
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                if (mainException != null) {
                    throw new RuntimeException("MainExeption: " + mainException.getMessage() +
                            "Secondary exeption" + ex.getMessage());
                } else new RuntimeException(ex.getMessage());
            }
        }
    }

    private void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    public void addAndSave(Word word) {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        createFile(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_APPEND)));
            int counter = 0;
            writer.write(counter + "");
            writer.write(";");
            writer.write(word.getWord());
            writer.write(";");
            writer.write(word.getTranslation());
            writer.write(";");
            writer.write(word.getCount());
            writer.write(";");
            writer.newLine();
        } catch (IOException ex) {
            mainException = ex;
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                if (mainException != null) {
                    throw new RuntimeException("MainExeption: " + mainException.getMessage() +
                            "Secondary exeption" + ex.getMessage());
                } else new RuntimeException(ex.getMessage());
            }
        }
    }

    public ArrayList<Word> getSavedData() {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        createFile(file);
        ArrayList<Word> words = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.openFileInput(FILE_NAME)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] allWords = line.split(";");
                int count = 0;
                try {
                    count = Integer.parseInt(allWords[3]);
                } catch (RuntimeException ex) {
                }
                words.add(new Word(allWords[1], allWords[2], count));
            }
        } catch (IOException ex) {
            mainException = ex;
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException | NullPointerException ex) {
                if (mainException != null) {
                    throw new RuntimeException("MainException: " + mainException.getMessage() +
                            "Secondary Exception" + ex.getMessage());
                } else new RuntimeException(ex.getMessage());
            }
        }
        return words;
    }
}
