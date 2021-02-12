package ru.kl;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import ru.kl.daterange.DateRange;
import ru.kl.daterange.Person;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Fork(value = 1, warmups = 1)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class DateRangeBenchmarkTest {

    private static final LocalDate MIN_DATE = LocalDate.of(2021, 4, 10);

    @Param({"10", "30", "100", "5000", "9000"})
    private int count;

    private TreeMap<LocalDate, Person> PREPARED_DATA;

    @Setup
    public void setup() {
        PREPARED_DATA = prepareData();
    }

    @Benchmark
    public void showDataForDateRangeByStreamTest(Blackhole blackhole) {
        blackhole.consume(DateRange.showDataForDateRangeByStream(PREPARED_DATA, MIN_DATE, MIN_DATE.plusDays(count)));
    }

    @Benchmark
    public void showDataForDateRangeByIteratorTest(Blackhole blackhole) {
        blackhole.consume(DateRange.showDataForDateRangeByIterator(PREPARED_DATA, MIN_DATE, MIN_DATE.plusDays(count)));
    }

    private TreeMap<LocalDate, Person> prepareData() {
        TreeMap<LocalDate, Person> treeMap = new TreeMap<>();
        Stream.iterate(LocalDate.of(2021, 1, 1), d -> d.plusDays(1))
                .limit(10000)
                .forEach(d -> treeMap.put(d, new Person("")));

        return treeMap;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
