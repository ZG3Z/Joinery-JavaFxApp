package com.example.joinery.controllers;

import com.example.joinery.Services.IDatabase;
import com.example.joinery.Services.MySqlDatabase;
import com.example.joinery.enums.Status;
import com.example.joinery.models.*;
import com.example.joinery.enums.CategorySpecialization;
import com.example.joinery.enums.LevelOfDamage;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.joinery.models.Service.COST_PER_DAY_ASSEMBLY;
import static com.example.joinery.models.Service.COST_PER_DAY_CONSERVATION;

public class Controller {

    /**
     * An instance of the IDatabase interface used for data management and communication with the database.
     * By default, it utilizes the MySqlDatabase implementation.
     * It is possible to use different implementations of IDatabase tailored to handle different databases.
     *
     * @see IDatabase
     * @see MySqlDatabase
     */
    private final IDatabase database = new MySqlDatabase();

    ObservableList<RetailCustomer> retailCustomers = FXCollections.observableArrayList();
    ObservableList<WholesaleCustomer> wholesaleCustomers = FXCollections.observableArrayList();
    ObservableList<Assembly> assemblyServices = FXCollections.observableArrayList();
    ObservableList<Conservation> conservationServices = FXCollections.observableArrayList();
    ObservableList<WoodMaterial> woodMaterials = FXCollections.observableArrayList();
    ObservableList<WoodLikeMaterial> woodLikeMaterials = FXCollections.observableArrayList();
    ObservableList<Chemical> chemicals = FXCollections.observableArrayList();
    ObservableList<Specialization> specializations = FXCollections.observableArrayList();
    ObservableList<Employee> employees = FXCollections.observableArrayList();
    ObservableList<ServiceOrder> serviceOrders = FXCollections.observableArrayList();

    private Service newService;
    private ServiceOrder newOrder = new ServiceOrder();

    @FXML
    private GridPane ordersView;
    @FXML
    private GridPane newOrderView;
    @FXML
    private Button saveCustomer;
    @FXML
    private Button saveService;
    @FXML
    private Button saveEmployee;
    @FXML
    private TextField selectedCustomerText;
    @FXML
    private TextField selectedEmployeeText;
    @FXML
    private TextField selectedServiceText;
    @FXML
    private ChoiceBox customerTypeChoiceBox;
    @FXML
    private Text serviceTypeText;
    @FXML
    private ChoiceBox serviceChoiceBox;
    @FXML
    private Text assemblyProductNameText;
    @FXML
    private Text assemblySizeText;
    @FXML
    private TextField assemblyProductNameTextField;
    @FXML
    private Slider assemblySizeSlider;
    @FXML
    private Label assemblySizeLabel;
    @FXML
    private Text materialText;
    @FXML
    private ChoiceBox materialChoiceBox;
    @FXML
    private Text conservationLevelOfDamageText;
    @FXML
    private ChoiceBox conservationLevelOfDamageChoiceBox;
    @FXML
    private ChoiceBox selectedAssociates;
    @FXML
    private Button removeAssociate;
    @FXML
    private Text specializationText;
    @FXML
    private ChoiceBox specializationChoiceBox;
    @FXML
    private TableView tableViewCustomer;
    @FXML
    private TableView tableViewElement;
    @FXML
    private TableView tableViewEmployee;
    @FXML
    private ChoiceBox customerChoiceBox;
    @FXML
    private TableView tableViewOrder;

    /**
     * Initializes the controller and sets up event listeners for UI components.
     *
     * It loads data from the database for the given entity types: "Order", "Retail customer", "Wholesale customer".
     *
     * The method sets up choice boxes, text fields, sliders, and other UI components with their respective event listeners
     * to handle changes in user input.
     */
    @FXML
    public void initialize() {
        loadDataFromDatabase(List.of("Order", "Retail customer", "Wholesale customer"));

        setChoiceBoxData();

        customerChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeCustomer(newValue)
        );

        customerTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeCustomerType(newValue)
        );


        serviceChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeService(newValue)
        );

        assemblyProductNameTextField.textProperty().addListener(
                (observable, oldValue, newValue)
                        -> handleTextFieldChangeProductName(newValue)
        );

        assemblySizeSlider.valueProperty().addListener(
                (observable, oldValue, newValue)
                        -> handleSliderChangeSize(newValue)
        );

        conservationLevelOfDamageChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeLevelOfDamage(newValue)
        );

        materialChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeMaterial()
        );

        specializationChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue)
                        -> handleChoiceBoxChangeSpecialization(newValue)
        );
    }

    /**
     * Sets up the choice boxes by populating them with the item names.
     */
    private void setChoiceBoxData(){
        customerTypeChoiceBox.getItems().addAll("Retail customer", "Wholesale customer");
        serviceChoiceBox.getItems().addAll("Assembly", "Conservation");
        materialChoiceBox.getItems().addAll("Wood material", "Wood like material");
        specializationChoiceBox.getItems().addAll("Assembly", "Conservation");
        customerChoiceBox.getItems().addAll(Stream.concat(
                        retailCustomers.stream().map(customer -> customer.getFirstName() + " " + customer.getLastName()),
                        wholesaleCustomers.stream().map(customer -> customer.getCompanyName()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Handles the change event of the customer type choice box.
     * Updates the table view based on the selected customer type.
     *
     * @param newValue The newly selected customer type.
     */
    private void handleChoiceBoxChangeCustomerType(String newValue){
        switch (newValue) {
            case "Retail customer" -> {
                loadDataToTable(List.of("First name", "Last name", "Date of birth", "Date joined",
                        "Payment preference", "Contact preference", "Telephone", "Email", "Loyalty card level"), tableViewCustomer, retailCustomers);
                tableViewCustomer.setItems(retailCustomers);
            }
            case "Wholesale customer" -> {
                loadDataToTable(List.of("Company name", "NIP", "Date joined", "" +
                        "Payment preference", "Contact preference", "Telephone", "Email"), tableViewCustomer, wholesaleCustomers);
                tableViewCustomer.setItems(wholesaleCustomers);
            }
        }
    }

    /**
     * Handles the event of selecting a customer.
     * Checks the type of the selected customer (Retail or Wholesale) and assigns it to the order.
     *
     * @throws NullPointerException if no customer is selected or the value of customerTypeChoiceBox is null
     */
    @FXML
    private void selectCustomer(){
        switch (customerTypeChoiceBox.getValue().toString()){
            case "Retail customer" -> {
                RetailCustomer selectedCustomer = (RetailCustomer) tableViewCustomer.getSelectionModel().getSelectedItem();
                assigmentCustomer(selectedCustomer, selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
            }
            case "Wholesale customer" -> {
                WholesaleCustomer selectedCustomer = (WholesaleCustomer) tableViewCustomer.getSelectionModel().getSelectedItem();
                assigmentCustomer(selectedCustomer, selectedCustomer.getCompanyName());
            }
        }
    }

    /**
     * Assigns a customer to the new order and updates the selected customer text and save customer visibility.
     *
     * @param customer The customer to be assigned
     * @param name The name to be displayed as the selected customer
     */
    private void assigmentCustomer(Customer customer, String name){
        newOrder.removeCustomer();
        newOrder.addCustomer(customer);

        selectedCustomerText.setText(name);
        saveCustomer.setVisible(true);
    }

    /**
     * Saves the assigned customer and updates the visibility of UI elements accordingly.
     * Loads data for additional elements from the database.
     */
    @FXML
    private void saveAssignedCustomer(){
        loadDataFromDatabase(List.of("Wood material", "Wood like material", "Chemical"));

        saveCustomer.setVisible(false);
        tableViewCustomer.setVisible(false);
        customerTypeChoiceBox.setDisable(true);

        serviceTypeText.setVisible(true);
        serviceChoiceBox.setVisible(true);

        selectedAssociates.setDisable(false);
        removeAssociate.setVisible(true);

        saveService.setVisible(true);
    }

    /**
     * Handles the change event of the service choice box.
     *
     * @param selectedValue the selected value in the service choice box
     */
    private void handleChoiceBoxChangeService(String selectedValue) {
        if ("Assembly".equals(selectedValue)) {
            changeService(false, "Assembly");
        } else if ("Conservation".equals(selectedValue)) {
            changeService(true, "Conservation");
        }
    }

    /**
     * Changes the display settings based on the selected service value.
     *
     * @param isConservation true if the selected service is "Conservation", false if it is "Assembly"
     * @param title the title of the selected service
     */
    private void changeService(boolean isConservation, String title){
        conservationLevelOfDamageText.setVisible(isConservation);
        conservationLevelOfDamageChoiceBox.setVisible(isConservation);

        assemblyProductNameText.setVisible(!isConservation);
        assemblySizeText.setVisible(!isConservation);
        assemblyProductNameTextField.setVisible(!isConservation);
        assemblyProductNameTextField.setText("");
        assemblySizeSlider.setVisible(!isConservation);
        assemblySizeSlider.setValue(5.0);
        assemblySizeLabel.setVisible(!isConservation);

        materialText.setVisible(!isConservation);
        materialChoiceBox.setVisible(!isConservation);
        materialChoiceBox.getSelectionModel().select(0);

        if(isConservation){
            conservationLevelOfDamageChoiceBox.getItems().setAll("low", "high");
            loadDataToTable(List.of("Name", "Toxicity level", "Price"), tableViewElement, chemicals);
        } else {
            handleChoiceBoxChangeMaterial();
        }

        selectedAssociates.getItems().clear();

        selectedServiceText.setText(title);

        tableViewElement.setVisible(true);
        saveService.setDisable(true);
        createNewService();
    }

    /**
     * Creates a new service object based on the selected service choice.
     */
    private void createNewService(){
        switch (serviceChoiceBox.getValue().toString()) {
            case "Assembly" -> {
                newService = new Assembly();
                newService.setId(assemblyServices.size() + conservationServices.size());
                newService.setCostPerDay(COST_PER_DAY_ASSEMBLY);
            }
            case "Conservation" -> {
                newService = new Conservation();
                newService.setId(assemblyServices.size() + conservationServices.size());
                newService.setCostPerDay(COST_PER_DAY_CONSERVATION);
            }
        }
    }

    /**
     * Handles the change event of the product name text field.
     * Updates the new service object with the new product name.
     * Disables the save service button if the product name is empty.
     *
     * @param newValue The new value of the product name text field.
     */
    private void handleTextFieldChangeProductName(String newValue) {
        saveService.setDisable(newValue.isEmpty());
        Assembly assembly = (Assembly) newService;
        assembly.setProductName(newValue);
    }

    /**
     * Handles the change event of the size slider.
     * Updates the new service object with the new size value.
     *
     * @param newValue The new value of the size slider.
     */
    private void handleSliderChangeSize(Number newValue) {
        assemblySizeLabel.setText(String.valueOf(newValue.intValue()));
        Assembly assembly = (Assembly) newService;
        assembly.setSize(newValue.intValue());
    }

    /**
     * Handles the change event of the level of damage choice box.
     * Updates the new service object with the new level of damage value.
     * Disables the save service button if the level of damage is null or empty.
     *
     * @param newValue The new value of the level of damage choice box.
     */
    private void handleChoiceBoxChangeLevelOfDamage(String newValue) {
        saveService.setDisable(newValue == null || newValue.isEmpty());
        if(conservationLevelOfDamageChoiceBox.getValue() != null){
            Conservation conservation = (Conservation) newService;
            conservation.setLevelOfDamage(LevelOfDamage.valueOf(newValue));
        }
    }

    /**
     * Handles the change event of the material choice box.
     * Loads the corresponding data to the table view based on the selected material.
     */
    private void  handleChoiceBoxChangeMaterial(){
        if(materialChoiceBox.getValue() != null) {
            switch (materialChoiceBox.getValue().toString()) {
                case "Wood material" -> {
                    loadDataToTable(List.of("Wood type", "Hardness", "Price"), tableViewElement, woodMaterials);
                    tableViewElement.setVisible(true);
                }
                case "Wood like material" -> {
                    loadDataToTable(List.of("Material", "Manufacturer", "Price"), tableViewElement, woodLikeMaterials);
                    tableViewElement.setVisible(true);
                }
            }
        }
    }

    /**
     * Handles the selection event of the element in the table view.
     * Adds the selected element to the service based on the selected service type.
     *
     * @throws IllegalStateException if the service choice box value is null.
     */
    @FXML
    private void selectElement(){
        switch(serviceChoiceBox.getValue().toString()) {
            case "Assembly" -> {
                if (materialChoiceBox.getValue() != null) {
                    switch (materialChoiceBox.getValue().toString()) {
                        case "Wood material" -> {
                            WoodMaterial selectedMaterial = (WoodMaterial) tableViewElement.getSelectionModel().getSelectedItem();

                            addMaterialToService(selectedMaterial);
                            addElementToChoiceBox(selectedMaterial.getWoodType());
                        }
                        case "Wood like material" -> {
                            WoodLikeMaterial selectedMaterial = (WoodLikeMaterial) tableViewElement.getSelectionModel().getSelectedItem();

                            addMaterialToService(selectedMaterial);
                            addElementToChoiceBox(selectedMaterial.getMaterial());
                        }
                    }
                }
            }
            case "Conservation" -> {
                Chemical selectedChemical = (Chemical) tableViewElement.getSelectionModel().getSelectedItem();

                Conservation conservation = (Conservation) newService;
                conservation.addChemical(selectedChemical);

                addElementToChoiceBox(selectedChemical.getName());
            }
        }
    }

    /**
     * Adds the given material to the assembly service.
     *
     * @param material the material to add to the assembly service
     */
    private void addMaterialToService(Material material){
        Assembly assembly = (Assembly) newService;
        assembly.addMaterial(material);
    }

    /**
     * Adds the given name as an element to the selected associates choice box.
     *
     * @param name the name to add to the selected associates choice box
     */
    private void addElementToChoiceBox(String name){
        if (!selectedAssociates.getItems().contains(name)) {
            selectedAssociates.getItems().add(name);
        }
    }

    /**
     * Removes the selected associate from the assembly service or conservation service.
     * It updates the associated materials or chemicals accordingly.
     */
    @FXML
    private void removeAssociate(){
        if(selectedAssociates.getValue() != null){
            switch(serviceChoiceBox.getValue().toString()){
                case "Assembly" -> {
                    Assembly assembly = (Assembly) newService;
                    long idSelectedMaterial = Stream.concat(
                            woodMaterials.stream()
                                    .filter(material -> material.getWoodType().equals(selectedAssociates.getValue().toString()))
                                    .map(WoodMaterial::getId),
                            woodLikeMaterials.stream()
                                    .filter(material -> material.getMaterial().equals(selectedAssociates.getValue().toString()))
                                    .map(WoodLikeMaterial::getId)
                    ).findFirst().get();

                    assembly.removeMaterial(assembly.getMaterials().stream().filter(m -> m.getId() == idSelectedMaterial).findFirst().get());
                }
                case "Conservation" -> {
                    Conservation conservation = (Conservation) newService;
                    long idSelectedChemical = chemicals.stream()
                            .filter(chemical -> chemical.getName().equals(selectedAssociates.getValue().toString()))
                            .map(Chemical::getId).findFirst().get();

                    conservation.removeChemical(conservation.getChemicalList().stream().filter(ch -> ch.getId() == idSelectedChemical).findFirst().get());
                }
            }

            selectedAssociates.getItems().remove(selectedAssociates.getSelectionModel().getSelectedIndex());
            selectedAssociates.getSelectionModel().clearSelection();
            selectedAssociates.getSelectionModel().select(null);
        }
    }

    /**
     * Saves the assigned service by adding it to the database and updating the UI.
     * It loads data from the database for employees and specializations.
     * The material-related UI elements are hidden, and the service-related UI elements are disabled.
     * The employee-related UI elements are shown, and the table view is loaded with employee data.
     */
    @FXML
    private void saveAssignedService(){
        loadDataFromDatabase(List.of("Employee", "Specialization"));

        database.addNewService(newService);

        materialText.setVisible(false);
        materialChoiceBox.setVisible(false);
        tableViewElement.setVisible(false);
        removeAssociate.setVisible(false);

        serviceChoiceBox.setDisable(true);
        saveService.setVisible(false);

        tableViewEmployee.setVisible(true);
        specializationText.setVisible(true);
        specializationChoiceBox.setVisible(true);

        loadDataToTable(List.of("First name", "Last name", "Date of birth", "Age", "Employment date", "Tenure"), tableViewEmployee, employees);
    }

    /**
     * Handles the change in the specialization choice box.
     * Updates the employee table view based on the selected specialization.
     * It filters the employees based on the licenses that match the selected specialization category.
     * Loads the filtered employee data into the table view.
     *
     * @param value The selected value from the specialization choice box.
     */
    private void handleChoiceBoxChangeSpecialization(String value){
        ObservableList<Employee> data = FXCollections.observableArrayList( employees.stream()
                .filter(employee -> employee.getLicenses().stream().anyMatch(license -> license.getSpecialization().getCategory()
                        .equals(CategorySpecialization.valueOf(value))))
                .collect(Collectors.toList())
        );

        loadDataToTable(List.of("First name", "Last name", "Age", "Date of birth", "Employment date", "Tenure"), tableViewEmployee, data);
    }

    /**
     * Selects an employee from the employee table view.
     * Assigns the selected employee to the new order.
     * Updates the selected employee text and enables the save employee button.
     */
    @FXML
    private void selectEmployee(){
        Employee selectedEmployee = (Employee) tableViewEmployee.getSelectionModel().getSelectedItem();
        assigmentEmployee(selectedEmployee, selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName());
    }

    /**
     * Assigns an employee to the new order.
     * Updates the selected employee text and enables the save employee button.
     * Displays an error message if the employee does not have a license in the selected type of service.
     *
     * @param employee The employee to be assigned.
     * @param name     The name of the employee.
     */
    private void assigmentEmployee(Employee employee, String name){
        if(checkLicense(employee)) {
            newOrder.removeEmployee();
            newOrder.addEmployee(employee);

            selectedEmployeeText.setText(name);
            saveEmployee.setVisible(true);
        } else {
            alertMessage("Employee cannot be assigned",
                    "the selected employee does not have a license in the selected type of service");
            saveEmployee.setVisible(false);
        }
    }

    /**
     * Checks if the employee has a license in the selected type of service.
     *
     * @param employee The employee to check.
     * @return True if the employee has a license in the selected type of service, false otherwise.
     */
    private boolean checkLicense(Employee employee){
        return employee.getLicenses().stream()
                .anyMatch(license -> license.getSpecialization().getCategory()
                        .equals(CategorySpecialization.valueOf(serviceChoiceBox.getValue().toString())));
    }

    /**
     * Saves the assigned employee to the new order.
     * Disables the save employee button, specialization choice box, and employee table view.
     * Adds the service to the order.
     * Displays an alert message with the order details.
     * Closes the current window.
     */
    @FXML
    private void saveEmployee(){
        saveEmployee.setVisible(false);
        specializationChoiceBox.setDisable(true);
        tableViewEmployee.setDisable(true);

        addServiceToOrder();
        alertMessage("Added a new order",
                "Discount: " + newOrder.getCustomer().getDiscount() + "%\nTotal price: " + newOrder.getTotalPrice() + "$");

        Stage stage = (Stage) saveEmployee.getScene().getWindow();
        stage.close();
    }


    private void addServiceToOrder(){
        newOrder.setId(serviceOrders.size()+1);
        newOrder.setStatus(Status.planned);
        newOrder.setDate(LocalDate.now());
        newOrder.addService(newService);
        database.addNewServiceOrder(newOrder);
        database.closeConnect();
    }

    /**
     * Handles the change in the customer choice box.
     * Retrieves the ID of the selected customer based on the choice box value.
     * Filters the service orders to display only the ones associated with the selected customer.
     * Loads the filtered data into the table view.
     *
     * @param newValue The new value selected in the customer choice box.
     */
    private void handleChoiceBoxChangeCustomer(String newValue) {
        long idSelectedCustomer = Stream.concat(
                retailCustomers.stream()
                        .filter(customer -> customer.getFirstName().equals(newValue.split(" ")[0]) && customer.getLastName().equals(newValue.split(" ")[1]))
                        .map(RetailCustomer::getId),
                wholesaleCustomers.stream()
                        .filter(customer -> customer.getCompanyName().equals(newValue))
                        .map(WholesaleCustomer::getIdC)
        ).findFirst().get();

        ObservableList<ServiceOrder> data = FXCollections.observableArrayList(serviceOrders.stream()
                .filter(order -> order.getCustomer().getIdC() == (idSelectedCustomer))
                .collect(Collectors.toList())
        );

        loadDataToTable(List.of("Date","Start date","End date", "Total price", "Status"), tableViewOrder, data);
    }

    @FXML
    private void viewOrders(){
        newOrderView.setVisible(false);
        ordersView.setVisible(true);
    }

    @FXML
    private void returnButton(){
        ordersView.setVisible(false);
        newOrderView.setVisible(true);
    }

    /**
     * Displays an information alert message with the specified header and content.
     *
     * @param header the header text of the alert
     * @param content the content text of the alert
     */
    private void alertMessage(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JOINERY");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }

    /**
     * Loads data to the TableView based on the provided columns, TableView, and items.
     *
     * @param columns a list of column names for the TableView
     * @param tableView the TableView to load data into
     * @param items the ObservableList of items to display in the TableView
     */
    private void loadDataToTable(List<String> columns, TableView tableView, ObservableList<?> items){
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

    /**
     * Loads data from the database based on the provided table names.
     *
     * @param tableNames a list of table names to load data from
     */
    private void loadDataFromDatabase(List<String> tableNames) {
        for (String tableName : tableNames) {
            switch (tableName) {
                case "Retail customer" -> retailCustomers.setAll(database.getRetailCustomers());
                case "Wholesale customer" -> wholesaleCustomers.setAll(database.getWholesaleCustomers());
                case "Assembly" -> assemblyServices.setAll(database.getAssemblyServices());
                case "Conservation" -> conservationServices.setAll(database.getConservationServices());
                case "Wood material" -> woodMaterials.setAll(database.getWoodMaterials());
                case "Wood like material" -> woodLikeMaterials.setAll(database.getWoodLikeMaterials());
                case "Chemical" -> chemicals.setAll(database.getChemicals());
                case "Employee" -> employees.setAll(database.getEmployees());
                case "Specialization" -> specializations.setAll(database.getSpecializations());
                case "Order" -> serviceOrders.setAll(database.getServiceOrders());
            }
        }
    }
}