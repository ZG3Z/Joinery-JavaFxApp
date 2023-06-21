package com.example.joinery;



import com.example.joinery.models.*;
import com.example.joinery.enums.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Konfiguracja Hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Otwarcie sesji
        Session session = sessionFactory.openSession();

        try {
            // Rozpoczęcie transakcji
            session.beginTransaction();

            // Wykonanie zapytania SELECT
               List<WholesaleCustomer> customers = session.createQuery("FROM WholesaleCustomer ", WholesaleCustomer.class).getResultList();
            //  List<Person> perosns = session.createQuery("FROM Person ", Person.class).getResultList();
              List<Employee> e = session.createQuery("FROM Employee", Employee.class).getResultList();
            // List<Specialization> s = session.createQuery("FROM Specialization ", Specialization.class).getResultList();
            // List<License> licenses = session.createQuery("FROM License ", License.class).getResultList();
            // List<Service> aServices = session.createQuery("FROM Service ", Service.class).getResultList();
             List<Conservation> cServices = session.createQuery("FROM Conservation ", Conservation.class).getResultList();
          //  List<RetailCustomer>  wMaterial= session.createQuery("FROM RetailCustomer ", RetailCustomer.class).getResultList();
            //  List<WoodLikeMaterial> wlMaterial = session.createQuery("FROM WoodLikeMaterial ", WoodLikeMaterial.class).getResultList();


            //session.save(service);

            session.getTransaction().commit();

            session.beginTransaction();
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setDate(LocalDate.now());
            serviceOrder.setStatus(Status.planned);

            serviceOrder.addCustomer(customers.get(0));
            serviceOrder.setService(cServices.get(cServices.size()-1));
            serviceOrder.addEmployee(e.get(0));

            System.out.println(serviceOrder.getCustomer().getIdC());
            session.save(serviceOrder);

            session.getTransaction().commit();
            session.close();
            // Wyświetlenie odczytanych danych
           //for(RetailCustomer service : wMaterial){
             //   System.out.println(service.getDiscount());
                //   System.out.println(service.getAssemblyList().stream().map(Assembly::getId).collect(Collectors.toList()));
           // }
/*
            for (Conservation c : cServices) {
                System.out.println(c);
            }
 */
/*
            for (License c : licenses) {
               System.out.println(c.getEmployee());
                System.out.println(c.getSpecialization().getLicenses());
              //  System.out.println(List.of(s.get(c.getIdS().intValue()).getLicenses()));
            }
 */




            // Zakończenie transakcji
            session.getTransaction().commit();
        } catch (Exception e) {
            // Obsługa błędów
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
