package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;
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

public class EquipmentWindowController {

    public TextField fieldEquipmentName;
    public TableView<Equipment> equipmentTableView;
    public TableColumn<Equipment, Integer> columnEquipmentId;
    public TableColumn<Equipment, String> columnEquipmentName;
    public TableColumn<Equipment, String> columnEquipmentInformation;
    public Button buttonSearch;
    public Button buttonListOfAllEqupiment;
    public Button buttonAddEquipment;
    public Button buttonUpdateEquipment;
    public Button buttonDeleteEquipment;
    public Button buttonBack;

    private Vehicle vehicle;
    private MotorFleetDAO dao;
    private ObservableList<Equipment> equipmentObservableList;
    private User user;
    private MotorFleet motorFleet;

    public EquipmentWindowController(ArrayList<Equipment> equipments, Vehicle vehicle, User user, MotorFleet motorFleet) {
        equipmentObservableList = FXCollections.observableArrayList(equipments);
        this.vehicle = vehicle;
        this.user = user;
        this.motorFleet = motorFleet;
    }

    public EquipmentWindowController(ArrayList<Equipment> equipments){
        equipmentObservableList = FXCollections.observableArrayList(equipments);
        this.user = null;
    }

    public EquipmentWindowController(ArrayList<Equipment> equipments, MotorFleet motorFleet){
        equipmentObservableList = FXCollections.observableArrayList(equipments);
        this.user = null;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        equipmentTableView.setItems(equipmentObservableList);
        columnEquipmentId.setCellValueFactory(new PropertyValueFactory("equipmentId"));
        columnEquipmentName.setCellValueFactory(new PropertyValueFactory("nameOfEquipment"));
        columnEquipmentInformation.setCellValueFactory(new PropertyValueFactory("information"));
        if(user != null)
            if(user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR && user.getLevelOfResponsibility() != LevelOfResponsibility.VEHICLE){
                buttonAddEquipment.setDisable(true);
                buttonUpdateEquipment.setDisable(true);
                buttonDeleteEquipment.setDisable(true);
            }
    }

    public void actionAddEquipment(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_equipment.fxml"));
            EquipmentController equipmentController= new EquipmentController(user, motorFleet);
            loader.setController(equipmentController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Equipment equipment = equipmentController.getEquipment();
                if (equipment != null) {

                    if(dao.isThereEquipment(equipment, motorFleet))
                        equipment.setEquipmentId(dao.getEquipmentIdByName(equipment.getNameOfEquipment()));
                    else
                        equipment.setEquipmentId(dao.getNextIdEquipment());

                    dao.addEquipmentForVehicle(vehicle, equipment, motorFleet);
                    if(vehicle != null)
                        equipmentObservableList.setAll(dao.getAllEquipmentOfVehicle(vehicle, motorFleet));
                    else    equipmentObservableList.setAll(dao.getAllEquipment(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionUpdate(){
        Equipment oldEquipment = equipmentTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_equipment.fxml"));
            EquipmentController equipmentController= new EquipmentController(equipmentTableView.getSelectionModel().getSelectedItem(), motorFleet);
            loader.setController(equipmentController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Equipment equipment = equipmentController.getEquipment();
                if (equipment != null) {

                    if(dao.isThereEquipment(equipment, motorFleet)){
                        int newId = dao.getEquipmentIdByName(equipment.getNameOfEquipment());
                        dao.updateEquipmentOfVehicle(vehicle.getVehicleId(),oldEquipment.getEquipmentId(),equipment.getEquipmentId());
                        equipment.setEquipmentId(newId);
                    }else{
                        equipment.setEquipmentId(oldEquipment.getEquipmentId());
                        dao.updateEquipment(equipment);
                    }

                    if(vehicle != null)
                        equipmentObservableList.setAll(dao.getAllEquipmentOfVehicle(vehicle, motorFleet));
                    else equipmentObservableList.setAll(dao.getAllEquipment(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionDelete() {
        if(vehicle != null)
            dao.deleteEquipmentFromWehicle(vehicle,equipmentTableView.getSelectionModel().getSelectedItem(), motorFleet);
        else dao.deleteEquipmentFromWehicle(null,equipmentTableView.getSelectionModel().getSelectedItem(), motorFleet);
        if(vehicle != null)
            equipmentObservableList.setAll(dao.getAllEquipmentOfVehicle(vehicle, motorFleet));
        else equipmentObservableList.setAll(dao.getAllEquipment(motorFleet));
    }

    public void actionSearch(){
        if(vehicle != null)
            equipmentObservableList.setAll(dao.getEquipmentByParametar(fieldEquipmentName.getText(), vehicle));
        else    equipmentObservableList.setAll(dao.searchEquipment(fieldEquipmentName.getText()));
        fieldEquipmentName.clear();
    }

    public void actionListOfAllEquipment() {
        if(vehicle != null)
            equipmentObservableList.setAll(dao.getAllEquipmentOfVehicle(vehicle, motorFleet));
        else equipmentObservableList.setAll(dao.getAllEquipment(motorFleet));
    }

    public void actionBack(){
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

}
