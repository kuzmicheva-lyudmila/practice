package ru.kl.patterns.builder;

import java.util.EnumMap;

public class PersonDTO implements PersonInfoBuilder{

    private enum Attributes {EMAIL, NAME}

    private EnumMap<Attributes, String> attributes;
    private String lastName;
    private String name;

    public PersonDTO() {
        attributes = new EnumMap<>(Attributes.class);
    }

    public String asJson() {
        attributes.put(Attributes.NAME, name + " " + lastName);
        return null;//new Gson().toJson(attributes);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setEmail(String email) {
        attributes.put(Attributes.EMAIL, email);
    }

    @Override
    public void setMiddleName(String middleName) {

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPhone(String phone) {

    }
}
