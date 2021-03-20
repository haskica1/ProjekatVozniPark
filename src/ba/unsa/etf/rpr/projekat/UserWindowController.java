package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class UserWindowController {

    public TableView<User> userTableView;
    public TableColumn<User, Integer> colUserId;
    public TableColumn<User, String> colUserFirstName;
    public TableColumn<User, String> colUserLastName;
    public TableColumn<User, String> colUserTelephoneNumber;
    public TableColumn<User, String> colUserAddress;
    public TableColumn<User, String> colUserLevelOfResponsibility;
    public TextField fieldFirstName, fieldLastName, fieldTelephoneNumber, fieldAddress;
    public Button buttonListOfAllUsers;
    public Button buttonSearch;
    public Button buttonUpdate;
    public Button buttonAdd;
    public Button buttonDelete;
    public Button buttonBack;


    private ObservableList<User> userObservableList;
    private MotorFleetDAO dao;
    private User user;
    private MotorFleet motorFleet;

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        userTableView.setItems(userObservableList);
        colUserId.setCellValueFactory(new PropertyValueFactory("userId"));
        colUserFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colUserLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colUserTelephoneNumber.setCellValueFactory(new PropertyValueFactory("telephoneNumber"));
        colUserAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colUserLevelOfResponsibility.setCellValueFactory(new PropertyValueFactory("levelOfResponsibility"));
        if(user.getLevelOfResponsibility() != LevelOfResponsibility.ADMINISTRATOR && user.getLevelOfResponsibility() != LevelOfResponsibility.EMPLOYEE){
            buttonUpdate.setDisable(true);
            buttonAdd.setDisable(true);
            buttonDelete.setDisable(true);
        }

    }

    public UserWindowController() {
    }

    public UserWindowController(ArrayList<User> users, User user, MotorFleet motorFleet){
        userObservableList = FXCollections.observableArrayList(users);
        this.user = user;
        this.motorFleet = motorFleet;
    }

    public void actionListOfAllUsers(){
        userObservableList = FXCollections.observableArrayList(dao.getAllUsers(motorFleet));
        userTableView.setItems(userObservableList);
    }

    public void actionSearch(){
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList = dao.getUsersByParametars(fieldFirstName.getText(),fieldLastName.getText(),fieldTelephoneNumber.getText(),fieldAddress.getText(), motorFleet);
        userObservableList = FXCollections.observableArrayList(userArrayList);
        userTableView.setItems(userObservableList);
        fieldLastName.clear();
        fieldAddress.clear();
        fieldFirstName.clear();
        fieldTelephoneNumber.clear();
    }

    public void actionUpdate(){
        User user = userTableView.getSelectionModel().getSelectedItem();
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_user.fxml"));
            UserController userController= new UserController(user);
            loader.setController(userController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                User user2 = userController.getUser();
                if (user2 != null) {
                    user2.setUserId(user.getUserId());
                    dao.updateUser(user2);
                    userObservableList.setAll(dao.getAllUsers(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionAddUser(){
        try{
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_user.fxml"));
            UserController userController= new UserController();
            loader.setController(userController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                User user = userController.getUser();
                if (user != null) {

                    dao.addUser(user, motorFleet);
                    userObservableList.setAll(dao.getAllUsers(motorFleet));
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionDelete(){
        User user = userTableView.getSelectionModel().getSelectedItem();
        dao.deleteUser(user,motorFleet);
        userObservableList.setAll(dao.getAllUsers(motorFleet));
    }

    public void actionBack(){
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();
    }
}
