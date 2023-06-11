package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workOrder")
public class WorkOrder {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private LocalDate date;
    @Column(name = "idCustomer")
    private long idCustomer;
    @Column(name = "idService")
    private long idService;
    @Column(name = "idEmployee")
    private long idEmployee;

    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idC", insertable = false, updatable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "idService", referencedColumnName = "id", insertable = false, updatable = false)
    private Service service;
    @ManyToOne
    @JoinColumn(name = "idEmployee", referencedColumnName = "idE", insertable = false, updatable = false)
    private Employee employee;

    public WorkOrder(){}

    public WorkOrder(long id, LocalDate date, Customer customer, Service service, Employee employee){
        this.id = id;
        this.date = date;
        setCustomer(customer);
        setService(service);
        setEmployee(employee);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public long getIdService() {
        return idService;
    }

    public void setIdService(long idService) {
        this.idService = idService;
    }

    public long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        idCustomer = customer.getIdC();
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        idService = service.getId();
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        idEmployee = employee.getId();
        this.employee = employee;
    }
    public float getTotalPrice(){
        return service.calculateServicePrice() * (float) (100 - customer.calculateDiscount()) /100;
    }
    @Override
    public String toString() {
        return "ORDER: \n" +
                "\tCustomer - " + getCustomer().toString()
                + "\n\tService - " + getService().toString()
                + "\n\tEmployee - " + getEmployee().toString();
    }
}
