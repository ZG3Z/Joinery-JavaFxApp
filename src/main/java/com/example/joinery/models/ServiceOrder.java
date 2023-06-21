/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import com.example.joinery.enums.Status;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "serviceOrder")
public class ServiceOrder {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Basic
    private LocalDate date;

    /**
     * The field represents the status of the service order.
     * It is an enumerated type {@link Status}.
     */
    @Enumerated(value = EnumType.STRING)
    private Status status;

    /**
     * The number of days it takes for an order to progress from the "planned" status to completion.
     * This value is a constant set to 7 days.
     */
    private static int DAYS_TO_PLANNED = 7;

    /**
     * A map used to ensure the uniqueness of order numbers.
     * Each order number is associated with a corresponding service order object.
     */
    private static Map<Long, ServiceOrder> uniqueIds = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idC")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idService", referencedColumnName = "id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "idEmployee", referencedColumnName = "idE")
    private Employee employee;

    public ServiceOrder(){}

    public ServiceOrder(long id, Status status, LocalDate date, Customer customer, Service service, Employee employee){
        setId(id);
        setStatus(status);
        setDate(date);
        setCustomer(customer);
        setService(service);
        setEmployee(employee);
    }

    public ServiceOrder(long id){
        setId(id);
        setStatus(Status.planned);
        setDate(LocalDate.now());
    }


    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the service order, ensuring uniqueness.
     *
     * @param id The ID to set for the service order.
     * @throws IllegalArgumentException if the ID already exists in another order.
     */
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


    /**
     * Calculates the start date of the order
     * based on the order date and the duration of the in-progress period.
     *
     * @return The start date of the order.
     */
    @Transient
    public LocalDate getStartDate() {return date.plusDays(DAYS_TO_PLANNED);}

    /**
     * Calculates the end date of the order based on the start date
     * and the duration required to complete the service.
     *
     * @return The end date of the order.
     */
    @Transient
    public LocalDate getEndDate() {
        return getStartDate().plusDays(service.getDaysToComplete());
    }

    /**
     * Calculates the total price of the service order,
     * taking into account the customer's discount.
     *
     * @return The total price of the service order after applying the customer's discount.
     */
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
        }
    }

    public void removeCustomer(){
        if(this.customer != null){
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
        }
    }

    public void removeService(){
        if(this.service != null){
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
        }
    }

    public void removeEmployee(){
        if(this.employee != null){
            setService(null);
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Cancels the service order if its date is not the same as the start date,
     * indicating that it is already in progress.
     */
    public void cancelOrder() {}
}
