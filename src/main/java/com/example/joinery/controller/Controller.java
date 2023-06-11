package com.example.joinery.controller;

import com.example.joinery.entity.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();
    private Session session;
    ObservableList<RetailCustomer> retailCustomers = FXCollections.observableArrayList();
    ObservableList<WholesaleCustomer> wholesaleCustomers = FXCollections.observableArrayList();
    ObservableList<Assembly> assemblyServices = FXCollections.observableArrayList();
    ObservableList<Conservation> conservationServices = FXCollections.observableArrayList();
    ObservableList<WoodMaterial> woodMaterials = FXCollections.observableArrayList();
    ObservableList<WoodLikeMaterial> woodLikeMaterials = FXCollections.observableArrayList();
    ObservableList<Chemical> chemicals = FXCollections.observableArrayList();
    ObservableList<Specialization> specializations = FXCollections.observableArrayList();
    ObservableList<Employee> employees = FXCollections.observableArrayList();
    ObservableList<WorkOrder> workOrders = FXCollections.observableArrayList();
    private Customer selectedCustomer;
    private Service selectedService;
    private List<Pair<Long, Integer>> selectedAssociated = new ArrayList<>();
    private Employee selectedEmployee;
    private int totalPrice;
    @FXML
    private Button removeAssociate;
    @FXML
    private ChoiceBox specializationChoiceBox;
    @FXML
    private TextField selectedEmployeeText;
    @FXML
    private TextField selectedServiceText;
    @FXML
    private ChoiceBox selectedAssociates;
    @FXML
    private TextField totalPriceField;
    @FXML
    private Button saveService;
    @FXML
    private Text materialText;
    @FXML
    private ChoiceBox materialChoiceBox;
    @FXML
    private ChoiceBox serviceChoiceBox;
    @FXML
    private Button saveCustomer;
    @FXML
    private Button saveEmployee;
    @FXML
    private TextField selectedCustomerText;
    @FXML
    private ChoiceBox customerChoiceBox;
    @FXML
    private TableView tableViewCustomer;
    @FXML
    private TableView tableViewService;
    @FXML
    private TableView tableViewMaterial;
    @FXML
    private TableView tableViewEmployee;
    @FXML
    private Button addNewCustomer;

    @FXML
    private TextField test;

    @FXML
    private Button testB;

    @FXML
    public void initialize() {
        loadDataCustomer();

        customerChoiceBox.getItems().addAll("Retail customer", "Wholesale customer");
        serviceChoiceBox.getItems().addAll("Assembly", "Conservation");
        materialChoiceBox.getItems().addAll("Wood material", "Wood like material");
        specializationChoiceBox.getItems().addAll("Assembly", "Conservation");

        tableViewCustomer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && customerChoiceBox.getValue().equals("Retail customer")) {
                RetailCustomer sCustomer = (RetailCustomer) tableViewCustomer.getSelectionModel().getSelectedItem();
                selectedCustomerText.setText(sCustomer.getFirstName() + " " + sCustomer.getLastName());
                selectedCustomer = sCustomer;

                selectedCustomerText.setVisible(true);
                saveCustomer.setVisible(true);

            } else if (event.getClickCount() == 1 && customerChoiceBox.getValue().equals("Wholesale customer")) {
                WholesaleCustomer sCustomer = (WholesaleCustomer) tableViewCustomer.getSelectionModel().getSelectedItem();
                selectedCustomerText.setText(sCustomer.getCompanyName());
                selectedCustomer = sCustomer;

                selectedCustomerText.setVisible(true);
                saveCustomer.setVisible(true);
            }
        });

        tableViewService.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && serviceChoiceBox.getValue().equals("Assembly")) {
                Assembly assembly = (Assembly) tableViewService.getSelectionModel().getSelectedItem();
                selectedService = assembly;
                handleChoiceBoxChangeMaterial();

                totalPrice = assembly.getCostPerDay() * assembly.getDaysToComplete();
                totalPriceField.setText(String.valueOf(totalPrice));

                selectedServiceText.setText("Assembly - " + assembly.getProductName());
                selectedServiceText.setVisible(true);

                selectedAssociated.clear();
                selectedAssociates.getItems().clear();
                saveService.setVisible(true);

                materialText.setVisible(true);
                materialChoiceBox.setVisible(true);
                tableViewMaterial.setVisible(true);

            } else if (event.getClickCount() == 1 && serviceChoiceBox.getValue().equals("Conservation")) {
                Conservation conservation = (Conservation) tableViewService.getSelectionModel().getSelectedItem();
                selectedService = conservation;

                loadTable(List.of("Name", "Toxicity level", "Price"), tableViewMaterial, chemicals);
                tableViewMaterial.setVisible(true);

                totalPrice = conservation.getCostPerDay() * conservation.getDaysToComplete();
                totalPriceField.setText(String.valueOf(totalPrice));

                selectedServiceText.setText("Conservation");
                selectedServiceText.setVisible(true);

                selectedAssociated.clear();
                selectedAssociates.getItems().clear();
                saveService.setVisible(true);
            }
        });

        tableViewMaterial.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && serviceChoiceBox.getValue().equals("Assembly")) {
                if(materialChoiceBox.getValue() != null){
                    switch (materialChoiceBox.getValue().toString()) {
                        case "Wood material" -> {
                            WoodMaterial selectedMaterial = (WoodMaterial) tableViewMaterial.getSelectionModel().getSelectedItem();

                            if (!selectedAssociated.contains(new Pair<>(selectedMaterial.getId(), selectedMaterial.getPrice()))) {
                                selectedAssociated.add(new Pair<>(selectedMaterial.getId(), selectedMaterial.getPrice()));
                                totalPrice += selectedAssociated.stream().mapToLong(Pair::getValue).sum();
                                totalPriceField.setText(String.valueOf(totalPrice));
                            }

                            if (!selectedAssociates.getItems().contains(selectedMaterial.getWoodType())) {
                                selectedAssociates.getItems().add(selectedMaterial.getWoodType());
                            }
                        }
                        case "Wood like material" -> {
                            WoodLikeMaterial selectedMaterial = (WoodLikeMaterial) tableViewMaterial.getSelectionModel().getSelectedItem();

                            if (!selectedAssociated.contains(new Pair<>(selectedMaterial.getId(), selectedMaterial.getPrice()))) {
                                selectedAssociated.add(new Pair<>(selectedMaterial.getId(), selectedMaterial.getPrice()));
                                totalPrice += selectedAssociated.stream().mapToLong(Pair::getValue).sum();
                                totalPriceField.setText(String.valueOf(totalPrice));
                            }

                            if (!selectedAssociates.getItems().contains(selectedMaterial.getMaterial())) {
                                selectedAssociates.getItems().add(selectedMaterial.getMaterial());
                            }
                        }
                    }
                }
            } else if (event.getClickCount() == 1 && serviceChoiceBox.getValue().equals("Conservation")) {
                Chemical chemical = (Chemical) tableViewMaterial.getSelectionModel().getSelectedItem();

                if (!selectedAssociated.contains(new Pair<>(chemical.getId(), chemical.getPrice()))) {
                    selectedAssociated.add(new Pair<>(chemical.getId(), chemical.getPrice()));
                    totalPrice += selectedAssociated.stream().mapToLong(Pair::getValue).sum();
                    totalPriceField.setText(String.valueOf(totalPrice ));
                }


                if (!selectedAssociates.getItems().contains(chemical.getName())) {
                    selectedAssociates.getItems().add(chemical.getName());
                }
            }
        });

        tableViewEmployee.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 ){
                Employee employee = (Employee) tableViewEmployee.getSelectionModel().getSelectedItem();
                selectedEmployeeText.setText(employee.getFirstName() + " " + employee.getLastName());
                selectedEmployee = employee;
                selectedEmployeeText.setVisible(true);

                saveEmployee.setVisible(specializationChoiceBox.getValue().equals(serviceChoiceBox.getValue()));
            }
        });

        customerChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleChoiceBoxChangeCustomer(newValue);
            }
        });

        serviceChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleChoiceBoxChangeService(newValue);
            }
        });

        materialChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleChoiceBoxChangeMaterial();
            }
        });

        removeAssociate.setOnMouseClicked(event -> {

        });

        specializationChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleChoiceBoxChangeSpecialization(newValue);
            }
        });
    }
    private void handleChoiceBoxChangeSpecialization(String value){
        switch (value) {
            case "Assembly", "Conservation" -> {
                ObservableList<Employee> data = FXCollections.observableArrayList( employees.stream()
                        .filter(employee -> employee.getLicenses().stream()
                                .anyMatch(license -> license.getSpecialization().getCategory().equals(value)))
                        .collect(Collectors.toList())
                );
                loadTable(List.of("First name", "Last name", "Date of birth", "Employment date"), tableViewEmployee, data); }
        }
    }
    private void handleChoiceBoxChangeCustomer(String value){
        switch (value) {
            case "Retail customer" -> {
                loadTable(List.of("First name", "Last name", "Date of birth", "Date joined",
                        "Payment preference", "Contact preference", "Telephone", "Email", "Loyalty card level"), tableViewCustomer, retailCustomers);
                tableViewCustomer.setItems(retailCustomers);
            }
            case "Wholesale customer" -> {
                loadTable(List.of("Company name", "NIP", "Date joined", "" +
                        "Payment preference", "Contact preference", "Telephone", "Email"), tableViewCustomer, wholesaleCustomers);
                tableViewCustomer.setItems(wholesaleCustomers);
            }
        }

    }
    private void handleChoiceBoxChangeService(String selectedValue) {
        switch (selectedValue) {
            case "Assembly" -> {
                loadTable(List.of("Product name", "Days to complete", "Cost per day"), tableViewService, assemblyServices);
                tableViewService.setVisible(true);
                tableViewMaterial.setVisible(false);
                saveService.setVisible(false);
            }
            case "Conservation" -> {
                materialText.setVisible(false);
                materialChoiceBox.setVisible(false);
                loadTable(List.of("Level of damage", "Days to complete", "Cost per day"), tableViewService, conservationServices);
                tableViewMaterial.setVisible(false);
                tableViewService.setVisible(true);
                saveService.setVisible(false);
            }
        }
    }
    private void  handleChoiceBoxChangeMaterial(){
        if(materialChoiceBox.getValue() != null) {
            switch (materialChoiceBox.getValue().toString()) {
                case "Wood material" -> {
                    loadTable(List.of("Wood type", "Hardness", "Price"), tableViewMaterial, woodMaterials);
                    tableViewMaterial.setVisible(true);
                }
                case "Wood like material" -> {
                    loadTable(List.of("Material", "Manufacturer", "Price"), tableViewMaterial, woodLikeMaterials);
                    tableViewMaterial.setVisible(true);
                }
            }
        }
    }
    private void loadTable(List<String> columns, TableView tableView, ObservableList<?> items){
        List<TableColumn> tableColumns = new ArrayList<>();

        for(String col : columns)
            tableColumns.add(new TableColumn(col));


       tableView.getColumns().setAll(tableColumns);

        for(TableColumn col : tableColumns) {
            float width = (float) ((tableView.widthProperty().floatValue() - 5) / tableColumns.size());
            col.setPrefWidth(width);
            col.setCellValueFactory(new PropertyValueFactory<>(Arrays.stream(col.getText().split(" "))
                    .map(String::toLowerCase)
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(""))));
        }

        tableView.setItems(items);
    }
    @FXML
    private void saveCustomer(){
        saveCustomer.setVisible(false);
        loadDataService();
        tableViewCustomer.setVisible(false);
        tableViewService.setVisible(true);

        customerChoiceBox.setDisable(true);
        serviceChoiceBox.setDisable(false);

        selectedAssociates.setDisable(false);
        removeAssociate.setVisible(true);
        saveService.setVisible(true);
    }
    @FXML
    private void removeAssociate(){
        if(selectedAssociates.getValue() != null){
            totalPrice -= selectedAssociated.get(selectedAssociates.getSelectionModel().getSelectedIndex()).getValue();
            totalPriceField.setText(String.valueOf(totalPrice));
            selectedAssociated.remove(selectedAssociates.getSelectionModel().getSelectedIndex());
            selectedAssociates.getItems().remove(selectedAssociates.getSelectionModel().getSelectedIndex());
            selectedAssociates.getSelectionModel().clearSelection();
            selectedAssociates.getSelectionModel().select(null);
        }
    }
    @FXML
    private void saveService(){
        loadDataEmployee();
        tableViewService.setVisible(false);
        tableViewMaterial.setVisible(false);
        tableViewEmployee.setVisible(true);

        saveService.setVisible(false);
        materialText.setVisible(false);
        materialChoiceBox.setVisible(false);

        serviceChoiceBox.setDisable(true);
        specializationChoiceBox.setDisable(false);
    }
    @FXML
    private void saveEmployee(){
        saveEmployee.setVisible(false);
        specializationChoiceBox.setDisable(true);
        tableViewEmployee.setDisable(true);
        show();
    }

    private void show(){
        Service service = null;

        switch (serviceChoiceBox.getValue().toString()) {
            case "Assembly" -> {
                Assembly assembly = (Assembly) selectedService;
                service = new Assembly(assemblyServices.size() + conservationServices.size(),
                        assembly.getProductName(),
                        assembly.getDaysToComplete(),
                        assembly.getCostPerDay());
            }
            case "Conservation" -> {
                Conservation conservation = (Conservation) selectedService;
                service = new Conservation(assemblyServices.size() + conservationServices.size(),
                        conservation.getLevelOfDamage(),
                        conservation.getDaysToComplete(),
                        conservation.getCostPerDay());
            }
        }

        for(Pair<Long, Integer> associate : selectedAssociated){
            switch (serviceChoiceBox.getValue().toString()) {
                case "Assembly" -> {
                    if (woodMaterials.stream().anyMatch(material -> material.getId() == associate.getKey())) {
                        Assembly assembly = (Assembly) service;
                        assembly.addMaterial(woodMaterials.stream().filter(c -> c.getId() == associate.getKey()).findFirst().get());
                   } else  if (woodLikeMaterials.stream().anyMatch(material -> material.getId() == associate.getKey())) {
                        Assembly assembly = (Assembly) service;
                        assembly.addMaterial(woodLikeMaterials.stream().filter(c -> c.getId() == associate.getKey()).findFirst().get());
                   }
                }
                case "Conservation" -> {
                    if (chemicals.stream().anyMatch(material -> material.getId() == associate.getKey())) {
                        Conservation conservation = (Conservation) service;
                        conservation.addChemical(chemicals.stream().filter(c -> c.getId() == associate.getKey()).findFirst().get());
                    }
                }
            }
        }

        WorkOrder newOrder = new WorkOrder(workOrders.size()+1, LocalDate.now(), selectedCustomer, service, selectedEmployee);

        addNewOrderToDatabase(service, newOrder);
        Stage stage = (Stage) saveEmployee.getScene().getWindow();
        stage.close();

       alertMessage(newOrder);
    }

    private void alertMessage(WorkOrder order){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ADDED NEW ORDER");
        alert.setHeaderText(order.toString());
        alert.setContentText("Total price: " + String.valueOf(order.getTotalPrice() + "$"));
        alert.showAndWait();
    }

    private void loadDataCustomer(){
        session  = sessionFactory.openSession();
        try {
            session.beginTransaction();

            List<RetailCustomer> rCustomers = session.createQuery("FROM RetailCustomer ", RetailCustomer.class).getResultList();
            List<WholesaleCustomer> wCustomers = session.createQuery("FROM WholesaleCustomer ", WholesaleCustomer.class).getResultList();


            for(RetailCustomer customer : rCustomers){
                this.retailCustomers.add(new RetailCustomer(
                        customer.getIdC(),
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
            }

            for(WholesaleCustomer customer : wCustomers){
                this.wholesaleCustomers.add(new WholesaleCustomer(
                        customer.getIdC(),
                        customer.getCompanyName(),
                        customer.getNip(),
                        customer.getDateJoined(),
                        customer.getPaymentPreference(),
                        customer.getContactPreference(),
                        customer.getTelephone(),
                        customer.getEmail()
                ));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    private void loadDataService(){
        try {
            session.beginTransaction();

            List<Assembly> aServices = session.createQuery("FROM Assembly ", Assembly.class).getResultList();
            List<Conservation> cServices = session.createQuery("FROM Conservation ", Conservation.class).getResultList();
            List<WoodMaterial>  wMaterial= session.createQuery("FROM WoodMaterial ", WoodMaterial.class).getResultList();
            List<WoodLikeMaterial> wlMaterial = session.createQuery("FROM WoodLikeMaterial ", WoodLikeMaterial.class).getResultList();
            List<Chemical> chemicals = session.createQuery("FROM Chemical ", Chemical.class).getResultList();

            for(Assembly service : aServices){
                this.assemblyServices.add(new Assembly(
                        service.getId(),
                        service.getProductName(),
                        service.getDaysToComplete(),
                        service.getCostPerDay()
                ));
            }

            for(Conservation service : cServices){
                this.conservationServices.add(new Conservation(
                        service.getId(),
                        service.getLevelOfDamage(),
                        service.getDaysToComplete(),
                        service.getCostPerDay()
                ));
            }

            for(WoodMaterial material : wMaterial){
                this.woodMaterials.add(new WoodMaterial(
                        material.getId(),
                        material.getWoodType(),
                        material.getHardness(),
                        material.getPrice()
                ));
            }

            for(WoodLikeMaterial material : wlMaterial){
                this.woodLikeMaterials.add(new WoodLikeMaterial(
                        material.getId(),
                        material.getMaterial(),
                        material.getManufacturer(),
                        material.getPrice()
                ));
            }

            for(Chemical chemical : chemicals){
                this.chemicals.add(new Chemical(
                        chemical.getId(),
                        chemical.getName(),
                        chemical.getToxicityLevel(),
                        chemical.getPrice()
                ));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    private void loadDataEmployee(){
        try {
            session.beginTransaction();

            List<Specialization> specializations = session.createQuery("FROM Specialization ", Specialization.class).getResultList();
            List<Employee> employees = session.createQuery("FROM Employee ", Employee.class).getResultList();
            List<WorkOrder> workOrders = session.createQuery("FROM WorkOrder", WorkOrder.class).getResultList();

            for(Specialization specialization : specializations){
                Specialization spec = new Specialization(
                        specialization.getId(),
                        specialization.getName(),
                        specialization.getCategory()
                );
                spec.setLicenses(spec.getLicenses());
                this.specializations.add(spec);
            }

            for(Employee employee : employees){
                Employee emp = new Employee(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDateOfBirth(),
                        employee.getEmploymentDate()
                );
                emp.setLicenses(employee.getLicenses());
                this.employees.add(emp);
            }

            for(WorkOrder order : workOrders) {
                this.workOrders.add(new WorkOrder(
                        order.getId(),
                        order.getDate(),
                        order.getCustomer(),
                        order.getService(),
                        order.getEmployee()
                ));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    private void addNewOrderToDatabase(Service service, WorkOrder workOrder){
        try {
            session.beginTransaction();

            session.save(service);
            session.save(workOrder);

            session.getTransaction().commit();
            session.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}