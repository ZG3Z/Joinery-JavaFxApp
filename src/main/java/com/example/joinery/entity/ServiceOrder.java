package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "serviceOrder")
public class ServiceOrder {
    public enum Status{planned, in_progress, canceled, completed}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Basic
    private LocalDate date;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private static int DAYS_TO_IN_PROGRESS = 7;

    private static Map<Long, ServiceOrder> uniqueIds = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idC", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idService", referencedColumnName = "id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "idEmployee", referencedColumnName = "idE", insertable = false, updatable = false)
    private Employee employee;

    public ServiceOrder(){}

    public ServiceOrder(long id, Status status, LocalDate date){
        setId(id);
        setStatus(status);
        setDate(date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(!uniqueIds.containsKey(id)) {
            uniqueIds.put(id, this);
            this.id = id;
        } else
            throw new IllegalArgumentException("Id: " + id + " already exists in other order");
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Transient
    public LocalDate getStartDate() {
        return date.plusDays(DAYS_TO_IN_PROGRESS);
    }

    @Transient
    public LocalDate getEndDate() {
        return getStartDate().plusDays(service.getDaysToComplete());
    }

    @Transient
    public float getTotalPrice(){
        return service.getTotalServiceCost() * (float) (100 - customer.getDiscount()) /100;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addCustomer(Customer newCustomer){
        if(this.customer == null){
            setCustomer(newCustomer);
            newCustomer.addServiceOrder(this);
        }
    }

    public void removeCustomer(){
        if(this.customer != null){
            this.customer.removeServiceOrder(this);
            setCustomer(null);
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void addService(Service newService){
        if(this.service == null){
            setService(newService);
            newService.addServiceOrder(this);
        }
    }

    public void removeService(){
        if(this.service != null){
            this.service.removeServiceOrder(this);
            setService(null);
        }
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void addEmployee(Employee newEmployee){
        if(this.employee == null){
            setEmployee(newEmployee);
            newEmployee.addServiceOrder(this);
        }
    }

    public void removeEmployee(){
        if(this.employee != null){
            this.employee.removeServiceOrder(this);
            setService(null);
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
