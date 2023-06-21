module com.example.joinery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;


    opens com.example.joinery to javafx.fxml, org.hibernate.orm.core;
    exports com.example.joinery;
    exports com.example.joinery.controllers;
    opens com.example.joinery.controllers to javafx.fxml, org.hibernate.orm.core;
    exports com.example.joinery.models;
    opens com.example.joinery.models to javafx.fxml, org.hibernate.orm.core;
}