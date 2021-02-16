package ru.kl;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import ru.kl.model.JdkModel;
import ru.kl.model.LombokModel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    private static final String NAME_VALUE = "marry";

    @Test
    void jdkModelBuilderTest() {
        int id = new Random().nextInt();
        List<String> targetList = Arrays.asList(NAME_VALUE);
        JdkModel jdkModel = JdkModel.builder()
                .id(id)
                .name(NAME_VALUE)
                .list(targetList)
                .build();

        assertAll(
                "jdk builder",
                () -> assertEquals(id, jdkModel.getId()),
                () -> assertEquals(NAME_VALUE, jdkModel.getName()),
                () -> assertNotSame(targetList, jdkModel.getList()),
                () -> assertEquals(targetList, jdkModel.getList())
        );

        List<String> list = jdkModel.getList();
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, NAME_VALUE));
    }

    @Test
    void lombokModelBuilder() {
        int id = new Random().nextInt();
        List<String> targetList = Arrays.asList(NAME_VALUE);
        LombokModel lombokModel = LombokModel.builder()
                .id(id)
                .name(NAME_VALUE)
                .list(targetList)
                .build();

        assertAll(
                "lombok builder",
                () -> assertEquals(id, lombokModel.getId()),
                () -> assertEquals(NAME_VALUE, lombokModel.getName()),
                () -> assertNotSame(targetList, lombokModel.getList()),
                () -> assertEquals(targetList, lombokModel.getList())
        );

        List<String> list = lombokModel.getList();
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, NAME_VALUE));
    }
}
