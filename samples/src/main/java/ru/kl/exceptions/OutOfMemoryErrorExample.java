package ru.kl.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OutOfMemoryErrorExample {

    public static void heapError() {
        Integer[] arr = new Integer[100000 * 100000];
    }

    public static void gcLimitError() {
        Map<Integer, String> m = new HashMap<>();
        Random r = new Random();
        while (true) {
            m.put(r.nextInt(), "randomValue");
        }
    }
}
