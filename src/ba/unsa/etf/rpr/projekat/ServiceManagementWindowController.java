package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ServiceManagementWindowController {

    public TextField fieldVehicleName;
    public TextField fieldVehicleLicencePlace;
    public TableView<Vehicle> vehicleTableView;
    public TableColumn<Vehicle, Integer> columnVehicleId;
    public TableColumn<Vehicle, String> columnVehicleName;
    public TableColumn<Vehicle, String> columnVehicleLicencePlate;
    public TableColumn<Vehicle, String> columnVehicleColor;
    public TableColumn<Vehicle, String> columnVehicleType;
    public Button buttonSearch;
    public Button buttoListOfAllVehicles;
    public Button buttonListoOfAllServices;
    public Button buttonBack;


    private MotorFleetDAO dao = null;
    private ObservableList<Vehicle> vehicleObservableList;
    private User user;
    private MotorFleet motorFleet;

    public ServiceManagementWindowController() {

    }

    public ServiceManagementWindowController(ArrayList<Vehicle> vehicles, User user, MotorFleet motorFleet){
        vehicleObservableList = FXCollections.observableArrayList(vehicles);
        this.user = user;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize() {

        dao = MotorFleetDAO.getInstance();
        vehicleTableView.setItems(vehicleObservableList);
        columnVehicleId.setCellValueFactory(new PropertyValueFactory("vehicleId"));
        columnVehicleName.setCellValueFactory(new PropertyValueFactory("vehicleName"));
        columnVehicleLicencePlate.setCellValueFactory(new PropertyValueFactory("licencePlate"));
        columnVehicleColor.setCellValueFactory(new PropertyValueFactory("color"));
        columnVehicleType.setCellValueFactory(new PropertyValueFactory("vehicleType"));
    }

    public void actionSearch(){
        vehicleObservableList.setAll(dao.getVehiclesByParametars(fieldVehicleName.getText(),fieldVehicleLicencePlace.getText(),motorFleet));
        vehicleTableView.setItems(vehicleObservableList);
    }

    public void actionListOfAllVehicle(){
        vehicleObservableList.setAll(dao.getAllVehicles(motorFleet));
        vehicleTableView.setItems(vehicleObservableList);
    }

    public void actionListOfServices(){
        Vehicle vehicle = vehicleTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/service_window.fxml"));
            ServiceWindowController serviceWindowController = new ServiceWindowController(vehicle, user, motorFleet);
            loader.setController(serviceWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionBack(){
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }
}
