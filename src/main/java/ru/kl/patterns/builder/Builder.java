package ru.kl.patterns.builder;

public class Builder {

    public static void main(String[] args) {
        Person person = new Person("John", "Doe", "Ivanovich", "email@email.com");
        PersonDTO builder = new PersonDTO();
        person.construct(builder);
        System.out.println(builder.asJson());
    }
}
