package com.example.joinery.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "retailCustomer")
@PrimaryKeyJoinColumn(name = "idCR")
public class RetailCustomer extends Customer {
    private String loyaltyCardLevel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCR", insertable = false, updatable = false)
    private Person person = new Person();

    public RetailCustomer(){}

    public RetailCustomer(Long id, String firstName, String lastName, LocalDate dateOfBirth,  LocalDate dateJoined, String paymentPreference, String contactPreference,
                          String telephone, String email, String loyaltyCardLevel, List<WorkOrder> workOrders){
        super();

        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);

        setIdC(id);
        setDateJoined(dateJoined);
        setPaymentPreference(paymentPreference);
        setContactPreference(contactPreference);
        setTelephone(telephone);
        setEmail(email);
        setWorkOrders(workOrders);

        this.loyaltyCardLevel = loyaltyCardLevel;
    }

    public long getId() {
        return person.getId();
    }


    public void setId(long id) {
        this.person.setId(id);
    }

    public String getFirstName() {
        return person.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.person.setFirstName(firstName);
    }

    public String getLastName() {
        return person.getLastName();
    }

    public void setLastName(String lastName) {
        this.person.setLastName(lastName);
    }

    public LocalDate getDateOfBirth() {
        return person.getDateOfBirth();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
       this.person.setDateOfBirth(dateOfBirth);
    }

    public String getLoyaltyCardLevel() {
        return loyaltyCardLevel;
    }

    public void setLoyaltyCardLevel(String loyaltyCardLevel) {
        this.loyaltyCardLevel = loyaltyCardLevel;
    }

    @Override
    public String toString() {
        return "Retail customer: " +
                person.toString() +
                super.toString() +
                ", loyaltyCardLevel: " + getLoyaltyCardLevel();
    }
}
