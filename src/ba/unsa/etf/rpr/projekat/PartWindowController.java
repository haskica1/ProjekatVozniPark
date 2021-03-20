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

public class PartWindowController {

    public TextField fieldPartId;
    public TextField fieldPartName;
    public TableView<Part> partTableView;
    public TableColumn<Part,Integer> columnPartId;
    public TableColumn<Part, String> columnPartName;
    public TableColumn<Part, String> columnPartUseful;
    public Button buttonAddPart;
    public Button buttonUpdatePart;
    public Button buttonDeletePart;
    public Button buttonSearch;
    public Button buttonBack;
    public Button buttonListOfAllParts;

    private MotorFleetDAO dao;
    private ObservableList<Part> partObservableList;
    private Storage storage;
    private User user;
    private MotorFleet motorFleet;


    public PartWindowController(ArrayList<Part> parts, Storage storage) {
        partObservableList = FXCollections.observableArrayList(parts);
        this.storage = storage;
    }

    public PartWindowController(ArrayList<Part> parts, Storage storage, User user, MotorFleet motorFleet) {
        partObservableList = FXCollections.observableArrayList(parts);
        this.storage = storage;
        this.user = user;
        this.motorFleet = motorFleet;
    }

    public PartWindowController(ArrayList<Part> parts, User user, MotorFleet motorFleet){
        partObservableList = FXCollections.observableArrayList(parts);
        this.user = user;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        partTableView.setItems(partObservableList);
        columnPartId.setCellValueFactory(new PropertyValueFactory("partId"));
        columnPartName.setCellValueFactory(new PropertyValueFactory("nameOfPart"));
        columnPartUseful.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsefulByString()));

        if(user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR && user.getLevelOfResponsibility() != LevelOfResponsibility.STORAGE){
            buttonAddPart.setDisable(true);
            buttonUpdatePart.setDisable(true);
            buttonDeletePart.setDisable(true);
        }
    }

    public void actionAddPart(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_part.fxml"));
            PartController partController= new PartController(storage, motorFleet);
            loader.setController(partController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Part part = partController.getPart();
                if (part != null) {

                    if(dao.isTherePart(part, motorFleet))
                        part.setPartId(dao.getPartIdByName(part.getNameOfPart()));
                    else part.setPartId(dao.getNextIDPart());

                    dao.addPartToStorage(storage, part, motorFleet);
                    if(storage != null)
                        partObservableList.setAll(dao.getAllPartsOfStorage(storage.getStorageId(), motorFleet));
                    else    partObservableList.setAll(dao.getAllParts(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionUpdatePart() {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_part.fxml"));
            PartController partController= new PartController(part, motorFleet);
            loader.setController(partController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Part part2 = partController.getPart();
                if (part2 != null) {

                    if(dao.isTherePart(part2, motorFleet)){
                        int newId = dao.getPartIdByName(part2.getNameOfPart());
                        dao.updatePartOfStorage(storage.getStorageId(),part.getPartId(), newId);
                        part2.setPartId(newId);
                    }else {
                        part2.setPartId(part.getPartId());
                        dao.updatePart(part2);
                        //dao.addPart(part2);
                        //ao.updatePartOfStorage(storage.getStorageId(),part.getPartId(), part2.getPartId());
                    }
                    if(storage != null)
                        partObservableList.setAll(dao.getAllPartsOfStorage(storage.getStorageId(),motorFleet));
                    else partObservableList.setAll(dao.getAllParts(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionDeletPart(){

        if(storage == null)
            dao.deletePart(partTableView.getSelectionModel().getSelectedItem(),null,motorFleet);
        else dao.deletePart(partTableView.getSelectionModel().getSelectedItem(), storage, motorFleet);
        if(storage != null)
            partObservableList.setAll(dao.getAllPartsOfStorage(storage.getStorageId(), motorFleet));
        else    partObservableList.setAll(dao.getAllParts(motorFleet));
    }

    public void actionBack(){
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

    public void actionSearch() {
        int id;
        if(fieldPartId.getText().equals(""))    id = 0;
        else id = Integer.parseInt(fieldPartId.getText());
        if(storage != null)
            partObservableList.setAll(dao.getPartsOfStorageByParametars(storage, id, fieldPartName.getText()));
        else    partObservableList.setAll(dao.getPartsByParametars(id, fieldPartName.getText()));
        fieldPartId.clear();
        fieldPartName.clear();
    }

    public void actionListOfAllParts(){
        if(storage != null)
            partObservableList.setAll(dao.getAllPartsOfStorage(storage.getStorageId(),motorFleet));
        else partObservableList.setAll(dao.getAllParts(motorFleet));

    }
}
