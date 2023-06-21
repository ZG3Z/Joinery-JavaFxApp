/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import com.example.joinery.enums.ContactPreference;
import com.example.joinery.enums.PaymentPreference;
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
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long idC;

    @Basic
    private LocalDate dateJoined;

    /**
     * The field represents the payment preference for a customer.
     * It is an enumerated type {@link PaymentPreference}.
     */
    @Enumerated(value = EnumType.STRING)
    private PaymentPreference paymentPreference;

    /**
     * The field represents the contact preference for a customer.
     * It is an enumerated type {@link ContactPreference}.
     */
    @Enumerated(value = EnumType.STRING)
    private ContactPreference contactPreference;

    @Basic
    private String telephone;

    @Basic
    private String email;

    /**
     * The qualified association between service orders and the customer
     * is represented as a map with the service order ID as the key
     * and the service order object as the value.
     */
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

    /**
     * Calculates customer's membership age based on join date.
     *
     * @return The number of years that have passed since the customer became a member.
     */
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
        }
    }

    public void removeServiceOrder(ServiceOrder serviceOrder){
        if(serviceOrders.containsKey(serviceOrder.getId())){
            serviceOrders.remove(serviceOrder.getId());
        }
    }

    public void setServiceOrders(Map<Long, ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    /**
     * Calculates the discount percentage for the customer based on the customer's type.
     *
     * @return Percentage discount for the customer.
     */
    @Transient
    public abstract int getDiscount();
}
