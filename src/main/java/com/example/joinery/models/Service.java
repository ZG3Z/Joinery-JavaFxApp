/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

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

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public Service(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    public abstract int getCostPerDay();

    /**
     * Calculates the estimated number of days needed to complete
     * the service order based on the service type.
     *
     * @return The estimated number of days to complete the service.
     */
    @Transient
    public abstract int getDaysToComplete();

    /**
     * Calculates the total cost of the service order,
     * taking into account the daily cost and additional costs specific to the service type.
     *
     * @return The total service cost.
     */
    @Transient
    public abstract int getTotalServiceCost();

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
}
