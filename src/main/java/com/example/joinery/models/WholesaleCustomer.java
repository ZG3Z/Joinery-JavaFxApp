/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import com.example.joinery.enums.ContactPreference;
import com.example.joinery.enums.PaymentPreference;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "wholesaleCustomer")
@PrimaryKeyJoinColumn(name = "idCW")
public class WholesaleCustomer extends Customer {
    @Basic
    private String companyName;

    @Basic
    private String nip;

    /**
     * A constant representing the fixed discount percentage
     * for all wholesale customers' orders, set to 5%.
     */
    private static int FIXED_BUSINESS_DISCOUNT = 5;

    public WholesaleCustomer(){}
    public WholesaleCustomer(Long id, String companyName, String nip,
                             LocalDate dateJoined, PaymentPreference paymentPreference,
                             ContactPreference contactPreference,
                             String telephone, String email){
        super();
        setIdC(id);
        setDateJoined(dateJoined);
        setPaymentPreference(paymentPreference);
        setContactPreference(contactPreference);
        setTelephone(telephone);
        setEmail(email);
        setCompanyName(companyName);
        setNip(nip);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * Calculates the discount for the order based on the customer's membership age (1 year = 1%)
     * and a fixed business discount.
     *
     * @return The discount amount, which is the sum of the membership age and the fixed business discount.
     */
    @Transient
    @Override
    public int getDiscount() {
        return getMembershipAge() + FIXED_BUSINESS_DISCOUNT;
    }
}
