/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import com.example.joinery.enums.ContactPreference;
import com.example.joinery.enums.LoyaltyCardLevel;
import com.example.joinery.enums.PaymentPreference;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "retailCustomer")
@PrimaryKeyJoinColumn(name = "idCR")
public class RetailCustomer extends Customer implements IPerson{

    /**
     * The field represents the loyalty card level of the retail customer.
     * It is an enumerated type {@link LoyaltyCardLevel}.
     */
    @Enumerated(value = EnumType.STRING)
    private LoyaltyCardLevel loyaltyCardLevel;

    /**
     * A person-related object for a retail client.
     * This means customer personal information.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCR", insertable = false, updatable = false)
    private final Person person = new Person();

    public RetailCustomer(){}
    public RetailCustomer(Long id, String firstName, String lastName, LocalDate dateOfBirth,
                          LocalDate dateJoined, PaymentPreference paymentPreference, ContactPreference contactPreference,
                          String telephone, String email,
                          LoyaltyCardLevel loyaltyCardLevel){
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
        setLoyaltyCardLevel(loyaltyCardLevel);
    }

    @Override
    public long getId() {
        return person.getId();
    }

    @Override
    public void setId(long id) {
        this.person.setId(id);
    }

    @Override
    public String getFirstName() {
        return person.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        this.person.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return person.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        this.person.setLastName(lastName);
    }

    @Override
    public LocalDate getDateOfBirth() {
        return person.getDateOfBirth();
    }
    @Override
    public void setDateOfBirth(LocalDate dateOfBirth) {
       this.person.setDateOfBirth(dateOfBirth);
    }

    @Transient
    @Override
    public int getAge() {return this.person.getAge();}

    public LoyaltyCardLevel getLoyaltyCardLevel() {
        return loyaltyCardLevel;
    }

    public void setLoyaltyCardLevel(LoyaltyCardLevel loyaltyCardLevel) {
        this.loyaltyCardLevel = loyaltyCardLevel;
    }

    /**
     * Calculates retail customer discount based on membership age (1 year = 1%)
     * and loyalty card level (standard - 2%, silver - 5%, gold - 7%).
     *
     * @return The discount amount, which is the sum of the membership age and the loyalty card level discount.
     */
    @Transient
    @Override
    public int getDiscount() {
        int loyaltyCardLevelDiscount = LoyaltyCardLevel.STANDARD.equals(loyaltyCardLevel)?
                2 : LoyaltyCardLevel.SILVER.equals(loyaltyCardLevel)?
                5: LoyaltyCardLevel.GOLD.equals(loyaltyCardLevel)? 7 : 0;

        return getMembershipAge() + loyaltyCardLevelDiscount;
    }
}
