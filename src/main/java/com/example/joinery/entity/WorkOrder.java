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

    @ManyToOne
    @JoinColumn(name = "idC", referencedColumnName = "idC", insertable = false, updatable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "idS", referencedColumnName = "id", insertable = false, updatable = false)
    private Service service;
    @ManyToOne
    @JoinColumn(name = "idE", referencedColumnName = "idE", insertable = false, updatable = false)
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public float getTotalPrice(){
        return service.calculateServicePrice() * (float) (100 - customer.calculateDiscount()) /100;
    }
    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                ", service=" + service +
                ", employee=" + employee +
                '}';
    }
}
