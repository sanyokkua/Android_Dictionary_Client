package ua.kostenko.mydictionary.core.local.parsing.implementation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.kostenko.mydictionary.core.local.parsing.Parser;
import ua.kostenko.mydictionary.core.local.parsing.ParserUnit;

public final class TxtParser implements Parser<ParserUnit> {

    @Override
    public List<ParserUnit> parse(@NonNull final String text) {
        String[] lines = text.split("(\n|\n\r)");
        LinkedHashMap<String, Long> unitsMap = new LinkedHashMap<>();
        List<ParserUnit> list = new ArrayList<>();
        for (String line : lines) {
            line = deleteSymbols(line);
            addUniqueUnitToMap(unitsMap, line);
        }
        for (Map.Entry<String, Long> entry : unitsMap.entrySet()) {
            list.add(new ParserUnit(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    @NonNull
    protected String deleteSymbols(@NonNull final String line) {
        String symbols = "\\{\\}!@#$%^&*\\(\\);:\\|_-\\+=\\*/\\?\"\',\\.~<>\t\n\r";
        String temp = line.replaceAll("<.+/?>", "\t");
        return temp.toLowerCase().replaceAll(symbols, "");
    }

    private void addUniqueUnitToMap(@NonNull final LinkedHashMap<String, Long> unitsMap, @NonNull final String line) {
        if (!unitsMap.containsKey(line)) {
            unitsMap.put(line, 1l);
        } else {
            Long counter = unitsMap.get(line);
            unitsMap.put(line, (counter == null) ? 1 : counter + 1);
        }
    }
}
