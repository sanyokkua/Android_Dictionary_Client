package ua.nure.mydictionary.UI.CommonClasses.DataAccess;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ua.nure.mydictionary.UI.Fragments.Web.AdditionItems.Bookmark;

public class BookmarkDataAccess {
    private final static String FILE_NAME = "Bookmark.bookmark";
    private Context mContext;
    private Throwable mainException;

    public BookmarkDataAccess(Context context) {
        mContext = context;
    }

    public void save(ArrayList<Bookmark> bookmarks) {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        createFile(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)));
            int counter = 0;
            for (Bookmark bookmark : bookmarks) {
                writer.write(counter + "");
                writer.write(";");
                writer.write(bookmark.getUrl());
                writer.write(";");
                writer.write(bookmark.getHeader());
                writer.write(";");
                writer.write(bookmark.getPictureId());
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

    public void addAndSave(Bookmark bookmark) {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        createFile(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_APPEND)));
            int counter = 0;
            writer.write(counter + "");
            writer.write(";");
            writer.write(bookmark.getUrl());
            writer.write(";");
            writer.write(bookmark.getHeader());
            writer.write(";");
            writer.write(bookmark.getPictureId());
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

    public ArrayList<Bookmark> getSavedData() {
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        createFile(file);
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.openFileInput(FILE_NAME)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] bookmarkData = line.split(";");
                bookmarks.add(new Bookmark(bookmarkData[1], bookmarkData[2], bookmarkData[3]));
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
        return bookmarks;
    }
}
