/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.Services;

import com.example.joinery.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;


/**
 * The MySqlDatabase class implements the IDatabase interface and handles operations on the MySQL database.
 *
 *  It provides methods to interact with the MySQL database using Hibernate ORM.
 */
public class MySqlDatabase implements IDatabase{
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    private final Session session;

    /**
     * Creates a new instance of the MySqlDatabase class.
     * It initializes Hibernate session by opening a new session using the session factory.
     */
    public MySqlDatabase(){
        session  = sessionFactory.openSession();
    }

    @Override
    public List<RetailCustomer> getRetailCustomers() {
        List<RetailCustomer> retailCustomers = new ArrayList<>();

        try {
            session.beginTransaction();

            List<RetailCustomer> rCustomers = session.createQuery("FROM RetailCustomer ", RetailCustomer.class).getResultList();

            for(RetailCustomer customer : rCustomers){
                RetailCustomer retailCustomer = new RetailCustomer();

                retailCustomer.setId(customer.getId());
                retailCustomer.setIdC(customer.getId());
                retailCustomer.setFirstName(customer.getFirstName());
                retailCustomer.setLastName(customer.getLastName());
                retailCustomer.setDateOfBirth(customer.getDateOfBirth());
                retailCustomer.setDateJoined(customer.getDateJoined());
                retailCustomer.setContactPreference(customer.getContactPreference());
                retailCustomer.setPaymentPreference(customer.getPaymentPreference());
                retailCustomer.setTelephone(customer.getTelephone());
                retailCustomer.setEmail(customer.getEmail());
                retailCustomer.setLoyaltyCardLevel(customer.getLoyaltyCardLevel());
                retailCustomer.setServiceOrders(customer.getServiceOrders());

                retailCustomers.add(retailCustomer);
                /*
                retailCustomers.add(new RetailCustomer(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getDateOfBirth(),
                        customer.getDateJoined(),
                        customer.getPaymentPreference(),
                        customer.getContactPreference(),
                        customer.getTelephone(),
                        customer.getEmail(),
                        customer.getLoyaltyCardLevel()
                ));

                 */
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return retailCustomers;
    }

    @Override
    public List<WholesaleCustomer> getWholesaleCustomers() {
        List<WholesaleCustomer> wholesaleCustomers = new ArrayList<>();

        try {
            session.beginTransaction();

            List<WholesaleCustomer> wCustomers = session.createQuery("FROM WholesaleCustomer ", WholesaleCustomer.class).getResultList();

            for(WholesaleCustomer customer : wCustomers){
                WholesaleCustomer wholesaleCustomer = new WholesaleCustomer();

                wholesaleCustomer.setIdC(customer.getIdC());
                wholesaleCustomer.setCompanyName(customer.getCompanyName());
                wholesaleCustomer.setNip(customer.getNip());
                wholesaleCustomer.setDateJoined(customer.getDateJoined());
                wholesaleCustomer.setContactPreference(customer.getContactPreference());
                wholesaleCustomer.setPaymentPreference(customer.getPaymentPreference());
                wholesaleCustomer.setTelephone(customer.getTelephone());
                wholesaleCustomer.setEmail(customer.getEmail());
                wholesaleCustomer.setServiceOrders(customer.getServiceOrders());

                wholesaleCustomers.add(wholesaleCustomer);

                /*
                wholesaleCustomers.add(new WholesaleCustomer(
                        customer.getIdC(),
                        customer.getCompanyName(),
                        customer.getNip(),
                        customer.getDateJoined(),
                        customer.getPaymentPreference(),
                        customer.getContactPreference(),
                        customer.getTelephone(),
                        customer.getEmail()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return wholesaleCustomers;
    }

    @Override
    public List<Assembly> getAssemblyServices() {
        List<Assembly> assemblyServices = new ArrayList<>();

        try {
            session.beginTransaction();

            List<Assembly> aServices = session.createQuery("FROM Assembly ", Assembly.class).getResultList();

            for(Assembly service : aServices){
                Assembly assembly = new Assembly();
                assembly.setId(service.getId());
                assembly.setProductName(service.getProductName());
                assembly.setSize(service.getSize());
                assembly.setMaterialList(service.getMaterials());
                assembly.setServiceOrders(service.getServiceOrders());
                assemblyServices.add(assembly);
                /*
                assemblyServices.add(new Assembly(
                        service.getId(),
                        service.getProductName(),
                        service.getSize()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return assemblyServices;
    }

    @Override
    public List<Conservation> getConservationServices() {
        List<Conservation> conservationServices = new ArrayList<>();

        try {
            session.beginTransaction();

            List<Conservation> cServices = session.createQuery("FROM Conservation ", Conservation.class).getResultList();

            for(Conservation service : cServices){
                Conservation conservation = new Conservation();
                conservation.setId(service.getId());
                conservation.setLevelOfDamage(service.getLevelOfDamage());
                conservation.setChemicalList(service.getChemicalList());
                conservation.setServiceOrders(service.getServiceOrders());
                conservationServices.add(conservation);
                /*
                conservationServices.add(new Conservation(
                        service.getId(),
                        service.getLevelOfDamage()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return conservationServices;
    }

    @Override
    public List<WoodMaterial> getWoodMaterials() {
        List<WoodMaterial> woodMaterials = new ArrayList<>();

        try {
            session.beginTransaction();

            List<WoodMaterial>  wMaterials = session.createQuery("FROM WoodMaterial ", WoodMaterial.class).getResultList();

            for(WoodMaterial material : wMaterials){
                WoodMaterial woodMaterial = new WoodMaterial();

                woodMaterial.setId(material.getId());
                woodMaterial.setWoodType(material.getWoodType());
                woodMaterial.setHardness(material.getHardness());
                woodMaterial.setPrice(material.getPrice());
                woodMaterial.setAssemblyList(material.getAssemblyList());

                woodMaterials.add(woodMaterial);
                /*
                woodMaterials.add(new WoodMaterial(
                        material.getId(),
                        material.getWoodType(),
                        material.getHardness(),
                        material.getPrice()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return woodMaterials;
    }

    @Override
    public List<WoodLikeMaterial> getWoodLikeMaterials() {
        List<WoodLikeMaterial> woodLikeMaterials = new ArrayList<>();

        try {
            session.beginTransaction();

            List<WoodLikeMaterial> wlMaterials = session.createQuery("FROM WoodLikeMaterial ", WoodLikeMaterial.class).getResultList();

            for(WoodLikeMaterial material : wlMaterials){
                WoodLikeMaterial woodLikeMaterial = new WoodLikeMaterial();

                woodLikeMaterial.setId(material.getId());
                woodLikeMaterial.setMaterial(material.getMaterial());
                woodLikeMaterial.setManufacturer(material.getManufacturer());
                woodLikeMaterial.setPrice(material.getPrice());
                woodLikeMaterial.setAssemblyList(material.getAssemblyList());

                woodLikeMaterials.add(woodLikeMaterial);

                /*
                woodLikeMaterials.add(new WoodLikeMaterial(
                        material.getId(),
                        material.getMaterial(),
                        material.getManufacturer(),
                        material.getPrice()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return woodLikeMaterials;
    }

    @Override
    public List<Chemical> getChemicals() {
        List<Chemical> chemicalList = new ArrayList<>();

        try {
            session.beginTransaction();

            List<Chemical> chemicals = session.createQuery("FROM Chemical ", Chemical.class).getResultList();

            for(Chemical ch : chemicals){
                Chemical chemical = new Chemical();

                chemical.setId(ch.getId());
                chemical.setName(ch.getName());
                chemical.setToxicityLevel(ch.getToxicityLevel());
                chemical.setPrice(ch.getPrice());
                chemical.setConservationList(ch.getConservationList());

                chemicalList.add(chemical);
                /*
                chemicalList.add(new Chemical(
                        ch.getId(),
                        ch.getName(),
                        ch.getToxicityLevel(),
                        ch.getPrice()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return chemicalList;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            session.beginTransaction();

            List<Employee> employees = session.createQuery("FROM Employee ", Employee.class).getResultList();

            for(Employee emp : employees){
                Employee employee = new Employee();

                employee.setId(emp.getId());
                employee.setFirstName(emp.getFirstName());
                employee.setLastName(emp.getLastName());
                employee.setDateOfBirth(emp.getDateOfBirth());
                employee.setEmploymentDate(emp.getEmploymentDate());
                employee.setLicenses(emp.getLicenses());
                employee.setServiceOrders(emp.getServiceOrders());

                employeeList.add(employee);

                /*
                employeeList.add(new Employee(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getDateOfBirth(),
                        emp.getEmploymentDate(),
                        emp.getLicenses()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return employeeList;
    }

    @Override
    public List<Specialization> getSpecializations() {
        List<Specialization> specializationList = new ArrayList<>();

        try {
            session.beginTransaction();

            List<Specialization> specializations = session.createQuery("FROM Specialization ", Specialization.class).getResultList();

            for(Specialization spec : specializations){
                Specialization specialization = new Specialization();

                specialization.setId(spec.getId());
                specialization.setName(spec.getName());
                specialization.setCategory(spec.getCategory());
                specialization.setLicenses(spec.getLicenses());

                specializationList.add(specialization);
                /*
                specializationList.add(new Specialization(
                        spec.getId(),
                        spec.getName(),
                        spec.getCategory(),
                        spec.getLicenses()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return specializationList;
    }

    @Override
    public List<ServiceOrder> getServiceOrders() {
        List<ServiceOrder> serviceOrders = new ArrayList<>();

        try {
            session.beginTransaction();

            List<ServiceOrder> sOrders = session.createQuery("FROM ServiceOrder ", ServiceOrder.class).getResultList();

            for(ServiceOrder order : sOrders) {
                ServiceOrder serviceOrder = new ServiceOrder();
                serviceOrder.setId(order.getId());
                serviceOrder.setStatus(order.getStatus());
                serviceOrder.setDate(order.getDate());
                serviceOrder.setCustomer(order.getCustomer());
                serviceOrder.setService(order.getService());
                serviceOrder.setEmployee(order.getEmployee());

                serviceOrders.add(serviceOrder);
                /*
                serviceOrders.add(new ServiceOrder(
                        order.getId(),
                        order.getStatus(),
                        order.getDate(),
                        order.getCustomer(),
                        order.getService(),
                        order.getEmployee()
                ));

                 */
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return serviceOrders;
    }

    @Override
    public void addNewService(Service newService) {
        try {
            session.beginTransaction();

            session.save(newService);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void addNewServiceOrder(ServiceOrder newServiceOrder) {
        try {
            session.beginTransaction();

            session.save(newServiceOrder);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void closeConnect() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


}
