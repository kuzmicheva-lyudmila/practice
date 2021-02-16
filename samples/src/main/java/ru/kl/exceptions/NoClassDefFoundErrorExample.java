package ru.kl.exceptions;

import org.apache.commons.lang3.StringUtils;

public class NoClassDefFoundErrorExample {

    public static ExceptionInInitializerErrorExample checkClassWithInitError() {
        try {
            new ExceptionInInitializerErrorExample();
        } catch (Throwable t) {
            System.out.println("Initializer error: " + t);
        }

        return new ExceptionInInitializerErrorExample();
    }

    public static void checkWithoutClassInRuntimeError() {
        String string = "error example";
        System.out.println(StringUtils.containsIgnoreCase(string, "EXAMPLE"));
    }
}
