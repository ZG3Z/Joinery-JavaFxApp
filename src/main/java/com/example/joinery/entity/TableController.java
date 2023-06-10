package com.example.joinery.entity;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TableController {

    @FXML
    private TableView tableView;

/*
    @FXML
    public void initialize() {

        System.out.println("yes");
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        firstNameColumn.setPrefWidth(200);
        lastNameColumn.setPrefWidth(200);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));



        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();


        tableView.getColumns().addAll(firstNameColumn, lastNameColumn);
        ObservableList<Employee> data = FXCollections.observableArrayList();
      //  List<Employee> employees = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).getResultList();

            for(Employee employee : employees){
                data.add(new Employee(employee.getId(), employee.getFirstName(), employee.getLastName()));
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            // Obsługa błędów
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            // Zamknięcie sesji
            session.close();
            sessionFactory.close();
        }
      //  ObservableList<Employee> data = FXCollections.observableArrayList();

        tableView.setItems(data);

    }

 */
}
