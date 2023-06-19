package com.example.joinery.entity;

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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public Employee() {}

    public Employee(Long id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate employmentDate) {
        super();
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);

        setEmploymentDate(employmentDate);
    }

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

    public List<ServiceOrder> getWorkOrders() {
        return serviceOrders;
    }

    public void addServiceOrder(ServiceOrder newServiceOrder) {
        if (!serviceOrders.contains(newServiceOrder)){
            serviceOrders.add(newServiceOrder);
            newServiceOrder.addEmployee(this);
        }
    }

    public void removeServiceOrder(ServiceOrder serviceOrder){
        if (serviceOrders.contains(serviceOrder)){
            serviceOrders.remove(serviceOrder);
            serviceOrder.removeEmployee();
        }
    }

    public void setWorkOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    @Transient
    public int getTenure(){
        return Period.between(employmentDate, LocalDate.now()).getYears();
    }
}

