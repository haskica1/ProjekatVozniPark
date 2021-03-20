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

public class StorageWindowController {

    public TextField fieldStorageName;
    public TableView<Storage> storageTableView;
    public TableColumn<Storage, Integer> columnStorageId;
    public TableColumn<Storage, String> columnStorageName;
    public TableColumn<Storage, String> columnStorageManagerName;
    public Button buttonSearch;
    public Button buttonListAllStorage;
    public Button buttonAddStorage;
    public Button buttonUpdateStorage;
    public Button buttonDeleteStorage;
    public Button buttonListOfParts;
    public Button buttonBack;

    private MotorFleetDAO dao;
    private ObservableList<Storage> storageObservableList;
    private User user;
    private MotorFleet motorFleet;

    public StorageWindowController() {
    }

    public StorageWindowController(ArrayList<Storage> storages, User user, MotorFleet motorFleet){
        storageObservableList = FXCollections.observableArrayList(storages);
        this.user = user;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        storageTableView.setItems(storageObservableList);
        columnStorageId.setCellValueFactory(new PropertyValueFactory("storageId"));
        columnStorageName.setCellValueFactory(new PropertyValueFactory("nameOfStorage"));
        columnStorageManagerName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameOfManager()));

        if(user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR && user.getLevelOfResponsibility() != LevelOfResponsibility.STORAGE){
            buttonAddStorage.setDisable(true);
            buttonUpdateStorage.setDisable(true);
            buttonDeleteStorage.setDisable(true);
        }
    }

    public void actionSearch(){
        storageObservableList.setAll(dao.getStoragesByParametar(fieldStorageName.getText(),motorFleet));
        fieldStorageName.clear();
    }

    public void actionListAllStorage() {
        storageObservableList.setAll(dao.getAllStorages(motorFleet));
    }

    public void actionAddStorage(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_storage.fxml"));
            //StorageController storageController= new StorageController(dao.getAllUsers());
            StorageController storageController= new StorageController(dao.getAllUsersByType(LevelOfResponsibility.STORAGE,motorFleet));
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Storage storage = storageController.getStorage();
                if (storage != null) {
                    dao.addStorage(storage, motorFleet);
                    storageObservableList.setAll(dao.getAllStorages(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionUpdateStorage(){
        Storage storage = storageTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_storage.fxml"));
            StorageController storageController= new StorageController(dao.getAllUsers(), storage);
            loader.setController(storageController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Storage storage2 = storageController.getStorage();
                if (storage2 != null) {
                    storage2.setStorageId(storage.getStorageId());
                    dao.updateStorage(storage2);
                    storageObservableList.setAll(dao.getAllStorages(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actiondeleteStorage() {
        dao.deleteStorage(storageTableView.getSelectionModel().getSelectedItem().getStorageId(), motorFleet);
        storageObservableList.setAll(dao.getAllStorages(motorFleet));
    }

    public void actionListOfParts(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/part_window.fxml"));
            PartWindowController partWindowController = new PartWindowController(dao.getAllPartsOfStorage(storageTableView.getSelectionModel().getSelectedItem().getStorageId(), motorFleet), storageTableView.getSelectionModel().getSelectedItem(), user, motorFleet);
            loader.setController(partWindowController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                /*Storage storage2 = storageController.getStorage();
                if (storage2 != null) {
                    storage2.setStorageId(storage.getStorageId());
                    dao.updateStorage(storage2);
                    storageObservableList.setAll(dao.getAllStorages());
                }*/
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionBack() {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }
}
