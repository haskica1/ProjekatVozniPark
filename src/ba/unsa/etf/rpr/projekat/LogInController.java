package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LogInController {

    public TextField fieldUserName;
    public PasswordField fieldPassword;
    public Button buttonNext;

    private LevelOfResponsibility levelOfResponsibility;
    private MotorFleetDAO dao;
    private User user;
    private MotorFleet motorFleet;
    private ObservableList<MotorFleet> motorFleetObservableList;

    public LogInController(ObservableList<MotorFleet> motorFleetObservableList, MotorFleet motorFleet) {
        dao = MotorFleetDAO.getInstance();
        this.motorFleetObservableList = motorFleetObservableList;
        this.motorFleet = motorFleet;
    }
    public LogInController(MotorFleet motorFleet) {
        dao = MotorFleetDAO.getInstance();
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
    }

    public ObservableList<MotorFleet> getMotorFleetObservableList() {
        return motorFleetObservableList;
    }

    public void actionNext() throws WrongDataException {
        user = dao.getUserByUsernameAndPassword(fieldUserName.getText(), fieldPassword.getText());
        if(motorFleetObservableList == null && motorFleet != null && user != null && user.getLevelOfResponsibility() == LevelOfResponsibility.DIRECTOR){
            dao.deleteMotorFleet(motorFleet);
            motorFleetObservableList = FXCollections.observableArrayList(dao.getAllMotorFleet());
            Stage stage2 = (Stage) buttonNext.getScene().getWindow();
            stage2.close();
            return;
        }
        if(motorFleetObservableList != null && motorFleet != null && dao.getUserByUsernameAndPassword(fieldUserName.getText(), fieldPassword.getText()).getLevelOfResponsibility() == LevelOfResponsibility.DIRECTOR){
            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_motor_fleet.fxml"));
                MotorFleetController motorFleetController= new MotorFleetController(motorFleetObservableList, motorFleet);
                loader.setController(motorFleetController);
                root = loader.load();
                stage.setTitle("");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                //stage.setResizable(false);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
            Stage stage2 = (Stage) buttonNext.getScene().getWindow();
            stage2.close();
            return;
        }

        if(motorFleet == null && user != null && user.getLevelOfResponsibility() == LevelOfResponsibility.DIRECTOR){
            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getResource("/fxml/modification_creating_motor_fleet.fxml"));
                MotorFleetController motorFleetController= new MotorFleetController(motorFleetObservableList);
                loader.setController(motorFleetController);
                root = loader.load();
                stage.setTitle("");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                //stage.setResizable(false);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
            Stage stage2 = (Stage) buttonNext.getScene().getWindow();
            stage2.close();
        }else if(motorFleet != null) {

            Boolean noProblem = true;

            if (!dao.isThereUserWithUsernameAndPassword(fieldUserName.getText(), fieldPassword.getText()) || !dao.isThereUserInMotorFleet(motorFleet.getMotorFleetId(), fieldUserName.getText(), fieldPassword.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong data!!!");
                alert.setContentText("Wrong username or password!!!");

                alert.showAndWait();

                fieldUserName.getStyleClass().add("poljeNijeIspravno");
                fieldPassword.getStyleClass().add("poljeNijeIspravno");
                noProblem = false;
            }

            if (!noProblem) {
                throw new WrongDataException("Wrong data!!!");
                //return;
            }

            int level = dao.getLevelOfUser(fieldUserName.getText(), fieldPassword.getText());
            user = dao.getUserByUsernameAndPassword(fieldUserName.getText(), fieldPassword.getText());

            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getResource("/fxml/main_window.fxml"));
                MainWindowController mainWindowController = new MainWindowController(user, motorFleet);
                loader.setController(mainWindowController);
                root = loader.load();
                stage.setTitle("");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                //stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage2 = (Stage) buttonNext.getScene().getWindow();
            stage2.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong data!!!");
            alert.setContentText("Wrong username or password!!!");

            alert.showAndWait();

            fieldUserName.getStyleClass().add("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            //noProblem = false;
        }
    }

    public void actionClickField(){
        fieldUserName.getStyleClass().removeAll("poljeNijeIspravno");
        fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
    }


}
