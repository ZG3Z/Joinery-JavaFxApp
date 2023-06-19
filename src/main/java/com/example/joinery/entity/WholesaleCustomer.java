package com.example.joinery.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;

@Entity
@Table(name = "wholesaleCustomer")
@PrimaryKeyJoinColumn(name = "idCW")
public class WholesaleCustomer extends Customer {
    private String companyName;
    private String nip;
    private static int FIXED_BUSINESS_DISCOUNT = 5;
    public WholesaleCustomer(){}

    public WholesaleCustomer(Long id, String companyName, String nip,
                             LocalDate dateJoined, PaymentPreference paymentPreference, ContactPreference contactPreference,
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

    @Transient
    @Override
    public int getDiscount() {
        return getMembershipAge() + FIXED_BUSINESS_DISCOUNT;
    }
}
