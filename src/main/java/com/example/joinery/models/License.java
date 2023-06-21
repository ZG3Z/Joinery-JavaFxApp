/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idS")
    private Long idS;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idE")
    private Long idE;

    @Basic
    private LocalDate dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "idS", referencedColumnName = "id")
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "idE", referencedColumnName = "idE")
    private Employee employee;

    public License(){}

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public Long getIdE() {
        return idE;
    }

    public void setIdE(Long idE) {
        this.idE = idE;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void removeLicense(){
        employee.removeLicense(this);
        specialization.removeLicense(this);
    }
}
