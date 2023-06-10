package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {
    enum PaymentPreference {cash, card}
    enum ContactPreference {telephone, email}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long idC;
    private LocalDate dateJoined;
    private String paymentPreference;
    private String contactPreference;
    private String telephone;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<WorkOrder> workOrders;

    public Customer(){}

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(String paymentPreference) {
        if (Objects.equals(paymentPreference, PaymentPreference.card.name()) || Objects.equals(paymentPreference, PaymentPreference.cash.name())) {
            this.paymentPreference = paymentPreference;
        } else {
            throw new IllegalArgumentException("Invalid payment preference value");
        }
    }

    public String getContactPreference() {
        return contactPreference;
    }

    public void setContactPreference(String contactPreference) {
        if (Objects.equals(contactPreference, ContactPreference.telephone.name()) || Objects.equals(contactPreference, ContactPreference.email.name())) {
            this.contactPreference = contactPreference;
        } else {
            throw new IllegalArgumentException("Invalid contact preference value");
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public int getMembershipAge(){
        return Period.between(dateJoined, LocalDate.now()).getYears();
    }

    public abstract int calculateDiscount();

    @Override
    public String toString() {
        return "date joined: " + getDateJoined()
                + " payment preference:  " + getPaymentPreference()
                + " contact preference: " + getContactPreference()
                + " telephone: " + getTelephone()
                + " email: " + getEmail();
    }
}
