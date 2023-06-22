/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "idE")
public class Employee extends Person {
    @Basic
    private LocalDate employmentDate;

    @OneToMany(mappedBy = "idE", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<License> licenses = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public Employee() {}

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License newLicense){
        if(!licenses.contains(newLicense)) {
            licenses.add(newLicense);
        }
    }

    public void removeLicense(License license){
        if(licenses.contains(license)) {
            licenses.remove(license);
        }
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void addServiceOrder(ServiceOrder newServiceOrder) {
        if (!serviceOrders.contains(newServiceOrder)){
            serviceOrders.add(newServiceOrder);
        }
    }

    public void removeServiceOrder(ServiceOrder serviceOrder){
        if (serviceOrders.contains(serviceOrder)){
            serviceOrders.remove(serviceOrder);
        }
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    /**
     * Calculates the tenure of the employee based on the date of employment.
     *
     * @return The number of years that have passed since the employee was hired.
     */
    @Transient
    public int getTenure(){
        return Period.between(employmentDate, LocalDate.now()).getYears();
    }
}

