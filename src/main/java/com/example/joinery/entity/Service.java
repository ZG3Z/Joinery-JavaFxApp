package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Service {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Basic
    private int costPerDay;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public Service(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(int costPerDay) {
        this.costPerDay = costPerDay;
    }

    @Transient
    public abstract int getDaysToComplete();

    @Transient
    public abstract int getTotalServiceCost();

    public List<ServiceOrder> getWorkOrders() {
        return serviceOrders;
    }

    public void addServiceOrder(ServiceOrder newServiceOrder) {
        if (!serviceOrders.contains(newServiceOrder)){
            serviceOrders.add(newServiceOrder);
            newServiceOrder.addService(this);
        }
    }

    public void removeServiceOrder(ServiceOrder serviceOrder){
        if (serviceOrders.contains(serviceOrder)){
            serviceOrders.remove(serviceOrder);
            serviceOrder.removeService();
        }
    }

    public void setWorkOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
}
