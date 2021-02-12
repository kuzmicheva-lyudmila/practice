package ru.kl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.kl.daterange.DateRange;
import ru.kl.daterange.Person;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.stream.Stream;

class DateRangeTest {

    private static final LocalDate MIN_DATE = LocalDate.of(2021, 4, 10);

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 100, 5000, 9000})
    void showDataForDateRangeByStreamTest(int count) {
        TreeMap<LocalDate, Person> map = prepareData();
        long duration = DateRange.showDataForDateRangeByStream(map, MIN_DATE, MIN_DATE.plusDays(count));
        System.out.printf("by stream: count = %d duration = %,d%n", count, duration);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 100, 5000, 9000})
    void showDataForDateRangeByIteratorTest(int count) {
        TreeMap<LocalDate, Person> map = prepareData();
        long duration = DateRange.showDataForDateRangeByIterator(map, MIN_DATE, MIN_DATE.plusDays(count));
        System.out.printf("by iterator: count = %d duration = %,d%n", count, duration);
    }

    private TreeMap<LocalDate, Person> prepareData() {
        TreeMap<LocalDate, Person> treeMap = new TreeMap<>();
        Stream.iterate(LocalDate.of(2021, 1, 1), d -> d.plusDays(1))
                .limit(10000)
                .forEach(d -> treeMap.put(d, new Person("")));

        return treeMap;
    }
}
