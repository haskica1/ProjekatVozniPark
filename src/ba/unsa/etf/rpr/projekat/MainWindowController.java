package ba.unsa.etf.rpr.projekat;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainWindowController {

    public Button buttonVehicle;
    public Button buttonEmployee;
    public Button buttonStorage;
    public Button buttonService;
    public Button buttonNext;
    public Button buttonEquipment;
    public Button buttonListOfAllParts;
    public Button buttonVehiclesReport;
    public Button buttonEmployeeReport;
    public Button buttonStorageReport;
    public Button buttonEquipmentReport;
    public Button buttonPartReport;
    public Button buttonServiceReport;

    public Label labelWelcome;

    private MotorFleetDAO dao;
    private User user;
    private MotorFleet motorFleet;

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        labelWelcome.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
    }

    public MainWindowController(User user, MotorFleet motorFleet){
        this.user = user;
        this.motorFleet = motorFleet;
    }

    public void vehicleButtonAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicle_window.fxml"));
            VehicleWindowController vehicleWindowController= new VehicleWindowController(dao.getAllVehicles(motorFleet), user, motorFleet);
            loader.setController(vehicleWindowController);
            root = loader.load();
            stage.setTitle("Vehicles");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void employeeButtonAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user_window.fxml"));
            UserWindowController userWindowController= new UserWindowController(dao.getAllUsers(motorFleet), user, motorFleet);
            loader.setController(userWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void storageButtonAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storage_window.fxml"));
            StorageWindowController storageWindowController= new StorageWindowController(dao.getAllStorages(motorFleet),user, motorFleet);
            loader.setController(storageWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void serviceButtonAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/service_management_window.fxml"));
            ServiceManagementWindowController serviceManagementWindowController = new ServiceManagementWindowController(dao.getAllVehicles(motorFleet), user, motorFleet);
            loader.setController(serviceManagementWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void equipmentButtonAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/equipment_window.fxml"));
            EquipmentWindowController equipmentWindowController = new EquipmentWindowController(dao.getAllEquipment(motorFleet), motorFleet);
            loader.setController(equipmentWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listOfAllPartsAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/part_window.fxml"));
            PartWindowController partWindowController = new PartWindowController(dao.getAllParts(motorFleet), user, motorFleet);
            loader.setController(partWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionButtonVehiclesReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allVehicleFromMotorFleetReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionButtonEmployeeReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allEmployeesFromMotorFleet.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionButtonStorageReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allStoragesFromMotorFleet.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionButtonEquipmentReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allEquipmentFromMotorFleet.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionButtonPartReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allPartsFromMotorFleet.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void actionButtonServiceReport(){
        try {
            new Report().showReport(dao.getConnection(), motorFleet.getMotorFleetId(),"/reports/allServiceFromMotorFleet.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
}
