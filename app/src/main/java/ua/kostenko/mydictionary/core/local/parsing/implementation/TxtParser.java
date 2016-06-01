package ua.kostenko.mydictionary.core.local.parsing.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;

public final class TxtParser implements Parser<ParserUnit> {
    private static final String TAG = TxtParser.class.getSimpleName();

    public TxtParser() {
    }

    @Override
    public List<ParserUnit> parse(@NonNull final String text) {
        String[] lines = text.split("(\n|\n\r)");
        Log.d(TAG, String.format("parse(): splited text: %s", lines.toString()));
        LinkedHashMap<String, Long> unitsMap = new LinkedHashMap<>();
        List<ParserUnit> list = new ArrayList<>();
        for (String line : lines) {
            line = deleteSymbols(line);
            addUniqueUnitToMap(unitsMap, line);
        }
        for (Map.Entry<String, Long> entry : unitsMap.entrySet()) {
            list.add(new ParserUnit(entry.getKey(), entry.getValue()));
        }
        Log.d(TAG, String.format("parse(): result list: %s", list.toString()));
        return list;
    }

    @NonNull
    protected String deleteSymbols(@NonNull final String line) {
        String symbols = "\\{\\}!@#$%^&*\\(\\);:\\|_-\\+=\\*/\\?\"\',\\.~<>\t\n\r";
        Log.d(TAG, String.format("deleteSymbols(): before deleting symbols: %s", line));
        String temp = line.replaceAll("<.+/?>", "\t");
        String result = temp.toLowerCase().replaceAll(symbols, "");
        Log.d(TAG, String.format("deleteSymbols(): after deleting symbols: %s", result));
        return result;
    }

    private void addUniqueUnitToMap(@NonNull final LinkedHashMap<String, Long> unitsMap, @NonNull final String line) {
        if (!unitsMap.containsKey(line)) {
            unitsMap.put(line, 1L);
        } else {
            Long counter = unitsMap.get(line);
            unitsMap.put(line, (counter == null) ? 1 : counter + 1);
        }
    }
}
