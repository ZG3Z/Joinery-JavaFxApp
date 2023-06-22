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

    /**
     * Creates a Hibernate SessionFactory based on the configuration specified in the "hibernate.cfg.xml" file.
     */
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    private final Session session;

    /**
     * Constructs a new instance of the MySqlDatabase class.
     * It initializes a new Hibernate session by opening a session from the session factory.
     */
    public MySqlDatabase(){
        session  = sessionFactory.openSession();
    }

    @Override
    public List<RetailCustomer> getRetailCustomers() {
        List<RetailCustomer> retailCustomers = new ArrayList<>();

        try {
            session.beginTransaction();

            retailCustomers = session.createQuery("FROM RetailCustomer ", RetailCustomer.class).getResultList();

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

            wholesaleCustomers = session.createQuery("FROM WholesaleCustomer ", WholesaleCustomer.class).getResultList();

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

            assemblyServices = session.createQuery("FROM Assembly ", Assembly.class).getResultList();

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

            conservationServices = session.createQuery("FROM Conservation ", Conservation.class).getResultList();

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

            woodMaterials = session.createQuery("FROM WoodMaterial").getResultList();

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

            woodLikeMaterials = session.createQuery("FROM WoodLikeMaterial ",  WoodLikeMaterial.class).getResultList();

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

            chemicalList = session.createQuery("FROM Chemical ", Chemical.class).getResultList();

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

            employeeList = session.createQuery("FROM Employee ", Employee.class).getResultList();

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

            specializationList = session.createQuery("FROM Specialization ", Specialization.class).getResultList();

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

            serviceOrders = session.createQuery("FROM ServiceOrder ", ServiceOrder.class).getResultList();

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
