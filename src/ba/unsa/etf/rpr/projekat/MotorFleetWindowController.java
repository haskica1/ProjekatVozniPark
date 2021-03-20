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

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MotorFleetWindowController {

    public TableView<MotorFleet> motorFleetTableView;
    public TableColumn<MotorFleet, Integer> columnMotorFleetId;
    public TableColumn<MotorFleet, String> columnNameOfMotorFleet;
    public Button buttonNext;
    public TextField fieldNameOfMotorFleet;
    public Button buttonSearch;
    public Button buttonlistOfAllMotorFleets;
    public Button buttonAddMotorFleet;
    public Button buttonUpdateMotorFleet;
    public Button buttonDeleteMotorFleet;


    private MotorFleetDAO dao;
    private ObservableList<MotorFleet> motorFleetObservableList;

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        motorFleetObservableList = FXCollections.observableArrayList(dao.getAllMotorFleet());
        motorFleetTableView.setItems(motorFleetObservableList);
        columnMotorFleetId.setCellValueFactory(new PropertyValueFactory("motorFleetId"));
        columnNameOfMotorFleet.setCellValueFactory(new PropertyValueFactory("nameOfMotorFleet"));
    }

    public MotorFleetWindowController() {

    }

    public void actionNext(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
            LogInController logInController= new LogInController(motorFleetTableView.getSelectionModel().getSelectedItem());
            loader.setController(logInController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionSearch(){
        motorFleetObservableList.setAll(FXCollections.observableArrayList(dao.getMotorFleetsByName(fieldNameOfMotorFleet.getText())));
    }

    public void actionListOfAllMotorFleets(){
        motorFleetObservableList.setAll(FXCollections.observableArrayList(dao.getAllMotorFleet()));
        fieldNameOfMotorFleet.clear();
    }

    public void actionAddMotorFleet(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
            LogInController logInController= new LogInController(motorFleetObservableList, null);
            loader.setController(logInController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        motorFleetObservableList.setAll(FXCollections.observableArrayList(dao.getAllMotorFleet()));
    }

    public void actionUpdateMotorFleet(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
            LogInController logInController= new LogInController(motorFleetObservableList, motorFleetTableView.getSelectionModel().getSelectedItem());
            loader.setController(logInController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                motorFleetObservableList.setAll(logInController.getMotorFleetObservableList());
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionDeleteMotorFleet(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
            LogInController logInController= new LogInController(motorFleetTableView.getSelectionModel().getSelectedItem());
            loader.setController(logInController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                motorFleetObservableList.setAll(logInController.getMotorFleetObservableList());
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
