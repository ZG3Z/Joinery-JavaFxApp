package com.example.joinery.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "retailCustomer")
@PrimaryKeyJoinColumn(name = "idCR")
public class RetailCustomer extends Customer {
    enum LoyaltyCardLevel {STANDARD, SILVER, GOLD}
    private String loyaltyCardLevel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCR", insertable = false, updatable = false)
    private Person person = new Person();

    public RetailCustomer(){}

    public RetailCustomer(Long id, String firstName, String lastName, LocalDate dateOfBirth,  LocalDate dateJoined, String paymentPreference, String contactPreference,
                          String telephone, String email, String loyaltyCardLevel){
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
        if (Objects.equals(loyaltyCardLevel, LoyaltyCardLevel.STANDARD.name()) || Objects.equals(loyaltyCardLevel, LoyaltyCardLevel.SILVER.name()) || Objects.equals(loyaltyCardLevel, LoyaltyCardLevel.GOLD.name())) {
            this.loyaltyCardLevel = loyaltyCardLevel;
        } else {
            throw new IllegalArgumentException("Invalid loyalty card level value");
        }
    }

    private int getDiscountFromLoyaltyCard(){
        switch (loyaltyCardLevel) {
            case "STANDARD" -> {
                return 2;
            }
            case "SILVER" -> {
                return 5;
            }
            case "GOLD" -> {
                return 7;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public int calculateDiscount() {
        return getMembershipAge() + getDiscountFromLoyaltyCard();
    }

    @Override
    public String toString() {
        return "Retail customer: " +
                person.toString() +
                super.toString() +
                ", loyaltyCardLevel: " + getLoyaltyCardLevel();
    }
}
