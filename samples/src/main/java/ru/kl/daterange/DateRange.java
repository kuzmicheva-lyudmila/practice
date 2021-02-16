package ru.kl.daterange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DateRange {

    private DateRange() {
    }

    public static long showDataForDateRangeByIterator(
            TreeMap<LocalDate, Person> treeMap,
            LocalDate minDate,
            LocalDate maxDate
    ) {
        checkParameters(treeMap, minDate, maxDate);

        List<String> result = new ArrayList<>();
        NavigableMap<LocalDate, Person> subMap = treeMap.subMap(minDate, true, maxDate, true);
        for (Map.Entry<LocalDate, Person> entry : subMap.entrySet()) {
            result.add(entry.getKey() + " : " + entry.getValue());
        }
        return result.size();
    }

    public static long showDataForDateRangeByStream(
            TreeMap<LocalDate, Person> treeMap,
            LocalDate minDate,
            LocalDate maxDate
    ) {
        checkParameters(treeMap, minDate, maxDate);

        List<String> result = treeMap.subMap(minDate, true, maxDate, true)
                .entrySet()
                .stream()
                .map(e -> e.getKey() + " : " + e.getValue())
                .collect(Collectors.toList());
        return result.size();
    }

    private static void checkParameters(TreeMap<LocalDate, Person> treeMap, LocalDate minDate, LocalDate maxDate) {
        checkNotNull("treeMap", treeMap);
        checkNotNull("minDate", minDate);
        checkNotNull("maxDate", maxDate);

        if (minDate.compareTo(maxDate) > 0) {
            throw new IllegalArgumentException("The date range is incorrect");
        }
    }

    private static void checkNotNull(String parameter, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("The '%s' parameter has not been specified", parameter));
        }
    }
}
