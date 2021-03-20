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
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class VehicleWindowController {

    public TextField fieldVehicleName;
    public TextField fieldVehicleLicencePlate;
    public TextField fieldVehicleColor;
    public TableView<Vehicle> vehicleTableView;
    public TableColumn<Vehicle, Integer> columnVehicleId;
    public TableColumn<Vehicle, String> columnVehicleName;
    public TableColumn<Vehicle, String> columnVehicleLicencePlate;
    public TableColumn<Vehicle, String> columnVehicleColor;
    public TableColumn<Vehicle, String> columnVehicleType;
    public Button buttonSearch;
    public Button buttonVehicleList;
    public Button buttonAddVehicle;
    public Button buttonUpdateVehicle;
    public Button buttonDeleteVehicle;
    public Button buttonBack;
    public Button buttonListOfAllEquipment;


    private MotorFleetDAO dao;
    private ObservableList<Vehicle> vehicleObservableList;
    private User user;
    private MotorFleet motorFleet;

    public VehicleWindowController() {
    }

    public VehicleWindowController(ArrayList<Vehicle> vehicles, User user, MotorFleet motorFleet){
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

        if(user.getLevelOfResponsibility() != LevelOfResponsibility.VEHICLE && user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR){
            buttonAddVehicle.setDisable(true);
            buttonUpdateVehicle.setDisable(true);
            buttonDeleteVehicle.setDisable(true);
            buttonListOfAllEquipment.setDisable(true);
        }
    }

    public void actionSearch(){
        vehicleObservableList.setAll(dao.getVehiclesByParametars2(fieldVehicleName.getText(),fieldVehicleLicencePlate.getText(),fieldVehicleColor.getText(),motorFleet.getMotorFleetId()));
        fieldVehicleName.clear();
        fieldVehicleLicencePlate.clear();
        fieldVehicleColor.clear();
    }

    public void actionVehicleList(){
        vehicleObservableList.setAll(dao.getAllVehicles(motorFleet));
    }

    public void actionAddVehicle(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_vehicle.fxml"));
            VehicleController vehicleController= new VehicleController();
            loader.setController(vehicleController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Vehicle vehicle = vehicleController.getVehicle();
                if (vehicle != null) {
                    int idVehicle = dao.getNextIDVehicle();
                    vehicle.setVehicleId(idVehicle);
                    dao.addVehicle(vehicle, motorFleet);
                    vehicleObservableList.setAll(dao.getAllVehicles(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionUpdateVehicle(){
        Vehicle vehicle = vehicleTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_vehicle.fxml"));
            VehicleController vehicleController= new VehicleController(vehicle);
            loader.setController(vehicleController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Vehicle vehicle2 = vehicleController.getVehicle();
                if (vehicle2 != null) {
                    vehicle2.setVehicleId(vehicle.getVehicleId());
                    dao.updateVehicle(vehicle2);
                    vehicleObservableList.setAll(dao.getAllVehicles(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buttonDeleteVehicle(){
        dao.deleteVehicle(vehicleTableView.getSelectionModel().getSelectedItem(), motorFleet);
        vehicleObservableList.setAll(dao.getAllVehicles(motorFleet));
    }

    public void actionBack() {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

    public void actionListOfAllEquipment() {
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/equipment_window.fxml"));
            EquipmentWindowController equipmentWindowController= new EquipmentWindowController(dao.getAllEquipmentOfVehicle(vehicleTableView.getSelectionModel().getSelectedItem(), motorFleet),vehicleTableView.getSelectionModel().getSelectedItem(), user, motorFleet);
            loader.setController(equipmentWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            /*stage.setOnHiding( event -> {
                Vehicle vehicle2 = vehicleController.getVehicle();
                if (vehicle2 != null) {
                    vehicle2.setVehicleId(vehicle.getVehicleId());
                    dao.updateVehicle(vehicle2);
                    vehicleObservableList.setAll(dao.getAllVehicles());
                }
            } );*/
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
