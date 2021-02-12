package ru.kl.exceptions;

class Animal {}

class Fox extends Animal {}

public class ClassCastExceptionExample {

    public static void createFox() {
        Animal animal = new Animal();
        Fox fox = (Fox) animal;
    }
}
