package com.example.joinery.entity;

import java.time.LocalDate;

public interface IPerson {
    long getId();
    void setId(long id);
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
    LocalDate getDateOfBirth();
    void setDateOfBirth(LocalDate dateOfBirth);
    int getAge();
}
