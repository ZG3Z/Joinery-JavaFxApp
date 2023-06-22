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
    List<RetailCustomer> getRetailCustomers();
    List<WholesaleCustomer> getWholesaleCustomers();
    List<Assembly> getAssemblyServices();
    List<Conservation> getConservationServices();
    List<WoodMaterial> getWoodMaterials();
    List<WoodLikeMaterial> getWoodLikeMaterials();
    List<Chemical> getChemicals();
    List<Employee> getEmployees();
    List<Specialization> getSpecializations();
    List<ServiceOrder> getServiceOrders();
    void addNewService(Service newService);
    void addNewServiceOrder(ServiceOrder newServiceOrder);
    void closeConnect();
}
