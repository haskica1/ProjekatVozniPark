package ba.unsa.etf.rpr.projekat;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ServiceWindowController {

    public TextField fieldServiceName;
    public DatePicker datePickerService;
    public TextField fieldServicePerson;
    public TableView<Service> tableViewServices;
    public TableColumn<Service,Integer> columnServiceId;
    public TableColumn<Service, String> columnServiceName;
    public TableColumn<Service, String> columnServiceDate;
    public TableColumn<Service, String> columnServicePersone;
    public TableColumn<Service, String> columnServiceInformation;
    public Button buttonSearch;
    public Button buttonAddService;
    public Button buttonDeleteService;
    public Button buttonUpdateService;
    public Button buttonListOfAllService;
    public Button buttonBack;

    private Vehicle vehicle = null;
    private MotorFleetDAO dao;
    private ObservableList<Service> serviceObservableList;
    private User user;
    private MotorFleet motorFleet;

    public ServiceWindowController() {
    }

    public ServiceWindowController(Vehicle vehicle, User user, MotorFleet motorFleet){
        switch(vehicle.getVehicleType()){
            case "MOTOR VEHICLE": this.vehicle = new MotorVehicle();
            case "HEAVY VEHICLE": this.vehicle = new HeavyVehicle();
            default: this.vehicle = new TrailerVehicle();
        }
        this.vehicle.setVehicleId(vehicle.getVehicleId());
        this.vehicle.setVehicleName(vehicle.getVehicleName());
        this.vehicle.setLicencePlate(vehicle.getLicencePlate());
        this.vehicle.setColor(vehicle.getColor());
        this.vehicle.setVehicleType(vehicle.getVehicleType());

        this.user = user;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize() {
        dao = MotorFleetDAO.getInstance();

        serviceObservableList = FXCollections.observableArrayList(dao.getAllServiceOfVehicle(vehicle));
        tableViewServices.setItems(serviceObservableList);
        columnServiceId.setCellValueFactory(new PropertyValueFactory("serviceId"));
        columnServiceName.setCellValueFactory(new PropertyValueFactory("nameOfService"));
        columnServiceDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getServiceDateAsString()));
        columnServicePersone.setCellValueFactory(new PropertyValueFactory("servicePerson"));
        columnServiceInformation.setCellValueFactory(new PropertyValueFactory("information"));
        if(user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR && user.getLevelOfResponsibility() != LevelOfResponsibility.SERVICE){
            buttonAddService.setDisable(true);
            buttonUpdateService.setDisable(true);
            buttonDeleteService.setDisable(true);
        }
    }

    public void actionSearch(){
        String dataOfService;
        if(datePickerService.getValue() == null) dataOfService = "";
        else dataOfService = datePickerService.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ArrayList<Service> services = dao.getAllServiceOfVehicleByParametars(vehicle, fieldServiceName.getText(),dataOfService, fieldServicePerson.getText());
        serviceObservableList = FXCollections.observableArrayList(services);
        tableViewServices.setItems(serviceObservableList);
        datePickerService.getEditor().clear();
        fieldServiceName.clear();
        fieldServicePerson.clear();
    }

    public void actionAddServiceToVehicle() {

        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_service.fxml"));
            ServiceController serviceController= new ServiceController();
            loader.setController(serviceController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Service service = serviceController.getService();
                if (service != null) {
                    int idService = dao.getNextIdService();
                    service.setServiceId(idService);
                    dao.addServiceForVehicle(vehicle,service,motorFleet);
                    serviceObservableList.setAll(dao.getAllServiceOfVehicle(vehicle));

                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteService(){
        Service service = tableViewServices.getSelectionModel().getSelectedItem();
        dao.deleteServiceForVehicle(vehicle,service,motorFleet);
        serviceObservableList.setAll(dao.getAllServiceOfVehicle(vehicle));
    }

    public void updateService(){
        Service selectedService = tableViewServices.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_service.fxml"));
            ServiceController serviceController= new ServiceController(selectedService);
            loader.setController(serviceController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Service service = serviceController.getService();
                if (service != null) {
                    service.setServiceId(selectedService.getServiceId());
                    dao.updateServiceForVehicle(service);
                    serviceObservableList.setAll(dao.getAllServiceOfVehicle(vehicle));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionListOfAllService(){
        serviceObservableList.setAll(dao.getAllServiceOfVehicle(vehicle));
    }

    public void actionBack() {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }
}
