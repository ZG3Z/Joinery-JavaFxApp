/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import java.time.LocalDate;

/**
 * The IPerson interface represents a person in the system.
 * It defines methods to retrieve and modify the personal information of a person.
 */
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
