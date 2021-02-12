package ru.kl;

import org.junit.jupiter.api.Test;
import ru.kl.exceptions.SuppressedExceptionExample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SuppressedExceptionTest {

    @Test
    void suppressedExceptionTest() {
        Exception exception = assertThrows(
                IllegalStateException.class,
                SuppressedExceptionExample::suppressedException
        );
        assertEquals(IllegalArgumentException.class, exception.getSuppressed()[0].getClass());
    }
}
