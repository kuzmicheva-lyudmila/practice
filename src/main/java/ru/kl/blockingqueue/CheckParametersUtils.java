package ru.kl.blockingqueue;

import java.util.Objects;

public class CheckParametersUtils {

    private CheckParametersUtils() {
    }

    public static void checkNotNull(String parameter, Object value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(String.format("The '%s' parameter is null", parameter));
        }
    }

    public static void checkNotEmpty(String parameter, String value) {
        checkNotNull(parameter, value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException(String.format("The '%s' parameter is empty", parameter));
        }
    }

    public static void checkLeftRangeBorder(String parameter, long value) {
        if (value < 0) {
            throw new IllegalArgumentException(String.format("The '%s' parameter is less then 0", parameter));
        }
    }
}
