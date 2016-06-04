package ua.kostenko.mydictionary.core.local.database.dao.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.services.implementation.TranslateServiceResponse;

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

    @NonNull
    public static Unit createUnit(TranslateServiceResponse body, @NonNull String text) {
        return new Unit(text, body.getTranslation(), body.getAdditionalTranslations(), "", 0, getTechnologies(body));
    }

    private static String getTechnologies(TranslateServiceResponse body) {
        StringBuilder builder = new StringBuilder();
        List<String> technologies = body.getTechnologies();
        for (String technology : technologies) {
            builder.append(technology);
            builder.append(";\n\n");
        }
        return builder.toString();
    }
}
