package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UserController {

    public TextField fieldFirstName = new TextField();
    public TextField fieldLastName = new TextField();
    public TextField fieldTelephoneNumber = new TextField();
    public TextField fieldAddres = new TextField();
    public TextField fieldUsername = new TextField();
    public PasswordField fieldPassword = new PasswordField();
    public RadioButton radioButtonMainAdministrator = new RadioButton();
    public RadioButton radioButtonEmployeeAdministrator = new RadioButton();
    public RadioButton radioButtonStorageAdministrator = new RadioButton();
    public RadioButton radioButtonServiceAdministrator = new RadioButton();
    public RadioButton radioButtonVehicleAdministrator = new RadioButton();
    public RadioButton radioButtonWorker = new RadioButton();
    public ToggleGroup toggleGroup = new ToggleGroup();
    public Button buttonsaveAndBack;

    private User user = null;
    private MotorFleetDAO dao;
    private Boolean doAChach = true;

    public UserController(User user) {
        this.user = user;
        doAChach = false;
    }

    public UserController(){

    }

    public User getUser() {
        return user;
    }

    @FXML
    void initialize(){
        dao = MotorFleetDAO.getInstance();

        radioButtonEmployeeAdministrator.setToggleGroup(toggleGroup);
        radioButtonMainAdministrator.setToggleGroup(toggleGroup);
        radioButtonServiceAdministrator.setToggleGroup(toggleGroup);
        radioButtonStorageAdministrator.setToggleGroup(toggleGroup);
        radioButtonVehicleAdministrator.setToggleGroup(toggleGroup);
        radioButtonWorker.setToggleGroup(toggleGroup);

        if(user != null){
            fieldFirstName.setText(user.getFirstName());
            fieldLastName.setText(user.getLastName());
            fieldTelephoneNumber.setText(user.getTelephoneNumber());
            fieldAddres.setText(user.getAddress());
            fieldUsername.setText(user.getUsername());
            fieldPassword.setText(user.getPassword());

            radioButtonEmployeeAdministrator.setToggleGroup(toggleGroup);
            radioButtonMainAdministrator.setToggleGroup(toggleGroup);
            radioButtonServiceAdministrator.setToggleGroup(toggleGroup);
            radioButtonStorageAdministrator.setToggleGroup(toggleGroup);
            radioButtonVehicleAdministrator.setToggleGroup(toggleGroup);
            radioButtonWorker.setToggleGroup(toggleGroup);

            radioButtonEmployeeAdministrator.setSelected(false);
            radioButtonVehicleAdministrator.setSelected(false);
            radioButtonMainAdministrator.setSelected(false);
            radioButtonStorageAdministrator.setSelected(false);
            radioButtonServiceAdministrator.setSelected(false);
            radioButtonWorker.setSelected(false);

            switch(user.getLevelOfResponsibility()){
                case ADMINISTRATOR: radioButtonMainAdministrator.setSelected(true); break;
                case STORAGE:   radioButtonStorageAdministrator.setSelected(true); break;
                case SERVICE:   radioButtonServiceAdministrator.setSelected(true);  break;
                case VEHICLE:   radioButtonVehicleAdministrator.setSelected(true);  break;
                case EMPLOYEE:  radioButtonEmployeeAdministrator.setSelected(true); break;
                default: radioButtonWorker.setSelected(true);
            }
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        Boolean noProblem = true;
        if(dao.isThereUserWithUsernameAndPassword(fieldUsername.getText(), fieldPassword.getText()) && doAChach){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong data!!!");
            alert.setContentText("Wrong data for new user.");
            noProblem = false;
            alert.showAndWait();
        }
        if(!noProblem) {
            throw new WrongDataException("Wrong data!!!");
            //return;
        }
        user = new User();

        user.setFirstName(fieldFirstName.getText());
        user.setLastName(fieldLastName.getText());
        user.setTelephoneNumber(fieldTelephoneNumber.getText());
        user.setAddress(fieldAddres.getText());
        user.setUsername(fieldUsername.getText());
        user.setPassword(fieldPassword.getText());

        if (radioButtonEmployeeAdministrator.isSelected())
            user.setLevelOfResponsibility(LevelOfResponsibility.EMPLOYEE);
        else if (radioButtonMainAdministrator.isSelected())
            user.setLevelOfResponsibility(LevelOfResponsibility.ADMINISTRATOR);
        else if (radioButtonServiceAdministrator.isSelected())
            user.setLevelOfResponsibility(LevelOfResponsibility.SERVICE);
        else if (radioButtonStorageAdministrator.isSelected())
            user.setLevelOfResponsibility(LevelOfResponsibility.STORAGE);
        else if(radioButtonVehicleAdministrator.isSelected())
            user.setLevelOfResponsibility(LevelOfResponsibility.VEHICLE);
        else user.setLevelOfResponsibility(LevelOfResponsibility.WORKER);

        if(fieldUsername.getText().equals("") || fieldFirstName.getText().equals("") || fieldLastName.getText().equals("") || fieldPassword.getText().equals("") || fieldTelephoneNumber.getText().equals("") ||fieldAddres.getText().equals(""))
            user = null;

        Stage stage = (Stage) buttonsaveAndBack.getScene().getWindow();
        stage.close();
    }

}
