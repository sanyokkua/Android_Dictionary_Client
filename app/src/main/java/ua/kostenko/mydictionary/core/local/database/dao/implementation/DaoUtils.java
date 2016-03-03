package ua.kostenko.mydictionary.core.local.database.dao.implementation;

import android.util.Log;

public class DaoUtils {
    private static final String TAG = DaoUtils.class.getSimpleName();
    private static final int CORRECT_NUMBER_OF_UPDATED_ROWS = 1;

    public static boolean validateCorrectNumberOfRows(final int numbOfRowsUpdated) {
        boolean resultOfValidation = true;
        if (numbOfRowsUpdated != CORRECT_NUMBER_OF_UPDATED_ROWS) {
            resultOfValidation = false;
            Log.w(TAG, "validation rows failed. Number of rows expected = " + CORRECT_NUMBER_OF_UPDATED_ROWS
                    + " but given = " + numbOfRowsUpdated);
        }
        return resultOfValidation;
    }
}
