module com.example.joinery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;


    opens com.example.joinery to javafx.fxml, org.hibernate.orm.core;
    exports com.example.joinery;
    exports com.example.joinery.controller;
    opens com.example.joinery.controller to javafx.fxml, org.hibernate.orm.core;
    exports com.example.joinery.entity;
    opens com.example.joinery.entity to javafx.fxml, org.hibernate.orm.core;
}