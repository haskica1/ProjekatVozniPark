package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MotorFleetController {

    public TextField fieldNameOfMotorFleet;
    public Button buttonAddManager;
    public Button buttonSaveAndBack;

    private User user;
    private MotorFleetDAO dao;
    private ObservableList<MotorFleet> motorFleetObservableList;
    private MotorFleet motorFleet;

    public MotorFleetController() {
    }

    public MotorFleetController(ObservableList<MotorFleet> motorFleetObservableList) {
        this.motorFleetObservableList = motorFleetObservableList;
    }

    public MotorFleetController(ObservableList<MotorFleet> motorFleetObservableList, MotorFleet motorFleet) {
        this.motorFleetObservableList = motorFleetObservableList;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        if(motorFleet != null) {
            fieldNameOfMotorFleet.setText(motorFleet.getNameOfMotorFleet());
            buttonAddManager.setDisable(true);
        }
    }

    public void actionAddManager() {
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
                    this.user = user;
                    this.user.setUserId(dao.getNextUserId());
                }
            } );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionSaveAndBack() {
        if(motorFleet != null){
            motorFleet.setNameOfMotorFleet(fieldNameOfMotorFleet.getText());
            dao.updateNameOfMotorFleet(motorFleet);
            motorFleetObservableList.setAll(dao.getAllMotorFleet());
        }else {
            int nextMotorFleetId = dao.getNextIdMotorFleet();
            dao.addMotorFleet(fieldNameOfMotorFleet.getText(),user.getUserId());
            dao.addUser(user,nextMotorFleetId);
            motorFleetObservableList.setAll(dao.getAllMotorFleet());
        }
        Stage stage = (Stage) buttonSaveAndBack.getScene().getWindow();
        stage.close();
    }
}
