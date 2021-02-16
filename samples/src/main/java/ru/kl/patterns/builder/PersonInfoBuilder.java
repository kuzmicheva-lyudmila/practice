package ru.kl.patterns.builder;

public interface PersonInfoBuilder {

    void setLastName(String lastName);
    void setEmail(String email);
    void setMiddleName(String middleName);
    void setName(String name);
    void setPhone(String phone);
}
