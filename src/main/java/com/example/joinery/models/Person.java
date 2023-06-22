/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * This class represents a person in the system.
 * It implements the {@link IPerson} interface, allowing other classes to inherit its methods.
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements IPerson{
    @Id
    private long id;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private LocalDate dateOfBirth;

    public Person() {}

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Calculates the age of the person based on their date of birth.
     *
     * @return The number of years that have passed since the customer was born.
     */
    @Transient
    @Override
    public int getAge(){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
