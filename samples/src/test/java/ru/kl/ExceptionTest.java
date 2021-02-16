package ru.kl;

import org.junit.jupiter.api.Test;
import ru.kl.exceptions.ClassCastExceptionExample;
import ru.kl.exceptions.ExceptionInInitializerErrorExample;
import ru.kl.exceptions.NoClassDefFoundErrorExample;
import ru.kl.exceptions.OutOfMemoryErrorExample;
import ru.kl.exceptions.StackOverflowErrorExample;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionTest {

    @Test
    void classCastExceptionTest() {
        assertThrows(ClassCastException.class, ClassCastExceptionExample::createFox);
    }

    @Test
    void exceptionInitializerErrorTest() {
        assertThrows(ExceptionInInitializerError.class, ExceptionInInitializerErrorExample::new);
    }

    @Test
    void stackOverflowErrorTest() {
        assertThrows(StackOverflowError.class, StackOverflowErrorExample::new);
    }

    @Test
    void noClassDefFoundErrorTest() {
        assertThrows(NoClassDefFoundError.class, NoClassDefFoundErrorExample::checkClassWithInitError);
        assertThrows(NoClassDefFoundError.class, NoClassDefFoundErrorExample::checkWithoutClassInRuntimeError);
    }

    @Test
    void outOfMemoryHeapErrorTest() {
        assertThrows(OutOfMemoryError.class, OutOfMemoryErrorExample::heapError);
    }

    @Test
    void outOfMemoryGcLimitErrorTest() {
        // -Xmx256m
        assertThrows(OutOfMemoryError.class, OutOfMemoryErrorExample::gcLimitError);
    }
}
