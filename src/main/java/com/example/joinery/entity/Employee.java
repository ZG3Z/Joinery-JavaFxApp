package com.example.joinery.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "idE")
public class Employee extends Person {

    private LocalDate employmentDate;

    @OneToMany(mappedBy = "idE")
    private List<License> licenses = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<WorkOrder> workOrders = new ArrayList<>();

    public Employee() {}

    public Employee(Long id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate employmentDate, List<License> licenses) {
        super();
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        this.employmentDate = employmentDate;
        setLicenses(licenses);
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
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

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public List<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public int getTenure(){
        return Period.between(employmentDate, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Employee: " +
                super.toString() +
                ", employment date: " + getEmploymentDate();
    }
}

