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
    /**
     * A constant representing the cost per day for assembly, set to 100.
     */
    public static int COST_PER_DAY_ASSEMBLY = 100;

    /**
     * A constant representing the cost per day for conservation, set to 200.
     */
    public static int COST_PER_DAY_CONSERVATION = 200;

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
