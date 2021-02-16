package ru.kl.exceptions;

public class ExceptionInInitializerErrorExample {

    static {
        String s = null;
        System.out.println(s.isEmpty());
    }
}
