package ua.kostenko.mydictionary.core.local.parsing.implementation;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import ua.kostenko.mydictionary.core.local.parsing.Parser;

public final class TxtParser implements Parser<Map<String, Long>> { //TODO: finish realization

    @Override
    public Map<String, Long> parse(@NonNull final String text) {
        String[] lines = text.split("(\n|\n\r)");
        Map<String, Long> unitsMap = new HashMap<>();
        for (String line : lines) {
            line = deleteSymbols(line);
            if (!unitsMap.containsKey(line)) {
                unitsMap.put(line, 1l);
            } else {
                Long counter = unitsMap.get(line);
                unitsMap.put(line, (counter == null) ? 1 : counter + 1);
            }
        }
        return unitsMap;
    }

    @NonNull
    protected String deleteSymbols(@NonNull final String line) {
        final String symbols = "{}!@#$%^&*();:|_-+=*/?\"\',.~\\<> \t\n\r";
        String temp = line.replaceAll("<.+/?>", "\t");
        temp = temp.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            if (symbols.indexOf(temp.charAt(i)) < 0) {
                builder.append(temp.charAt(i));
            }
        }
        return builder.toString();
    }
}
