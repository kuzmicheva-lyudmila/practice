package ru.kl.exceptions;

public class SuppressedExceptionExample implements AutoCloseable {

    @Override
    public void close() throws IllegalStateException {
        throw new IllegalArgumentException();
    }

    public static void suppressedException() {
        try (SuppressedExceptionExample s1 = new SuppressedExceptionExample()) {
            throw new IllegalStateException();
        }
    }
}
