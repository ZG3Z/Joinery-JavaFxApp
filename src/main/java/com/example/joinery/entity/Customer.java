package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {
    public enum PaymentPreference {cash, card}
    public enum ContactPreference {telephone, email}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long idC;

    @Basic
    private LocalDate dateJoined;

    @Enumerated(value = EnumType.STRING)
    private PaymentPreference paymentPreference;

    @Enumerated(value = EnumType.STRING)
    private ContactPreference contactPreference;

    @Basic
    private String telephone;

    @Basic
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    private Map<Long, ServiceOrder> serviceOrders = new HashMap<>();

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

    @Transient
    public int getMembershipAge(){return Period.between(dateJoined, LocalDate.now()).getYears();}

    public PaymentPreference getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(PaymentPreference paymentPreference) {
        this.paymentPreference = paymentPreference;
    }

    public ContactPreference getContactPreference() {
        return contactPreference;
    }

    public void setContactPreference(ContactPreference contactPreference) {
        this.contactPreference = contactPreference;
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

    public Map<Long, ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void addServiceOrder(ServiceOrder newServiceOrder){
        if(!serviceOrders.containsKey(newServiceOrder.getId())){
            serviceOrders.put(newServiceOrder.getId(), newServiceOrder);
            newServiceOrder.addCustomer(this);
        }
    }

    public void removeServiceOrder(ServiceOrder serviceOrder){
        if(serviceOrders.containsKey(serviceOrder.getId())){
            serviceOrders.remove(serviceOrder.getId());
            serviceOrder.removeCustomer();
        }
    }

    public void setServiceOrders(Map<Long, ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    @Transient
    public abstract int getDiscount();
}
