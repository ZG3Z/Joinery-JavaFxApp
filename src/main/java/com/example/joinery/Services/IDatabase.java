/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.Services;

import com.example.joinery.models.*;
import java.util.List;

/**
 * The interface represents a database that provides access to various entities and operations related to a joinery system.
 *
 * It defines methods to retrieve different types of entities such as retail customers, wholesale customers,
 * services, materials, employees, specializations, and service orders.
 *
 * It also includes methods to add new services and service orders to the database.
 */
public interface IDatabase {

    /**
     * Retrieves a list of retail customers from the database.
     *
     * @return The list of retail customers.
     */
    List<RetailCustomer> getRetailCustomers();

    /**
     * Retrieves a list of wholesale customers from the database.
     *
     * @return The list of wholesale customers.
     */
    List<WholesaleCustomer> getWholesaleCustomers();

    /**
     * Retrieves a list of assembly services from the database.
     *
     * @return The list of assembly services.
     */
    List<Assembly> getAssemblyServices();

    /**
     * Retrieves a list of conservation services from the database.
     *
     * @return The list of conservation services.
     */
    List<Conservation> getConservationServices();

    /**
     * Retrieves a list of wood materials from the database.
     *
     * @return The list of wood materials.
     */
    List<WoodMaterial> getWoodMaterials();

    /**
     * Retrieves a list of wood-like materials from the database.
     *
     * @return The list of wood-like materials.
     */
    List<WoodLikeMaterial> getWoodLikeMaterials();

    /**
     * Retrieves a list of chemicals from the database.
     *
     * @return The list of chemicals.
     */
    List<Chemical> getChemicals();

    /**
     * Retrieves a list of employees from the database.
     *
     * @return The list of employees.
     */
    List<Employee> getEmployees();

    /**
     * Retrieves a list of specializations from the database.
     *
     * @return The list of specializations.
     */
    List<Specialization> getSpecializations();

    /**
     * Retrieves a list of specializations from the database.
     *
     * @return The list of specializations.
     */
    List<ServiceOrder> getServiceOrders();

    /**
     * Adds a new service to the database.
     *
     * @param newService The new service to be added.
     */
    void addNewService(Service newService);

    /**
     * Adds a new service order to the database.
     *
     * @param newServiceOrder The new service order to be added.
     */
    void addNewServiceOrder(ServiceOrder newServiceOrder);

    void closeConnect();
}
