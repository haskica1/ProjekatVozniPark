package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class VehicleController {

    public TextField fieldVehicleName;
    public TextField fieldVehicleLicencePlate;
    public TextField fieldVehicleColor;
    public RadioButton radioButtonMotorVehicle;
    public RadioButton radioButtonHeavyVehicle;
    public RadioButton radioButtonTrailerVehicle;
    public ToggleGroup toggleGroup = new ToggleGroup();
    public Button buttonsaveAndBack;

    private Vehicle vehicle = null;
    private MotorFleetDAO dao;
    private Boolean doAChach = true;

    public VehicleController() {
    }

    public VehicleController(Vehicle vehicle){
        this.vehicle = vehicle;
        doAChach = false;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    @FXML
    void initialize(){
        dao = MotorFleetDAO.getInstance();

        radioButtonMotorVehicle.setToggleGroup(toggleGroup);
        radioButtonHeavyVehicle.setToggleGroup(toggleGroup);
        radioButtonTrailerVehicle.setToggleGroup(toggleGroup);

        if(vehicle != null){
            fieldVehicleName.setText(vehicle.getVehicleName());
            fieldVehicleLicencePlate.setText(vehicle.getLicencePlate());
            fieldVehicleColor.setText(vehicle.getColor());

            radioButtonMotorVehicle.setToggleGroup(toggleGroup);
            radioButtonHeavyVehicle.setToggleGroup(toggleGroup);
            radioButtonTrailerVehicle.setToggleGroup(toggleGroup);

            radioButtonHeavyVehicle.setSelected(false);
            radioButtonTrailerVehicle.setSelected(false);
            radioButtonMotorVehicle.setSelected(false);

            switch(vehicle.getVehicleType()){
                case "MOTOR VEHICLE": radioButtonMotorVehicle.setSelected(true); break;
                case "HEAVY VEHICLE":   radioButtonHeavyVehicle.setSelected(true); break;
                case "TRAILER VEHICLE":   radioButtonTrailerVehicle.setSelected(true);  break;
                default: break;
            }
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        Boolean noProblem = true;
        if(dao.isThereVehicleWithLicencePlate(fieldVehicleLicencePlate.getText()) && doAChach){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong licence plate!!!");
            alert.setContentText("There is vehicle with the same licance plate.");
            noProblem = false;
            alert.showAndWait();
        }

        if(!noProblem){
            throw new WrongDataException("Wrong Data!!!");
            //return;
        }
        if (radioButtonMotorVehicle.isSelected()) {
            vehicle = new MotorVehicle();
            vehicle.setVehicleType("MOTOR VEHICLE");
        } else if (radioButtonTrailerVehicle.isSelected()) {
            vehicle = new TrailerVehicle();
            vehicle.setVehicleType("TRAILER VEHICLE");
        } else {
            vehicle = new HeavyVehicle();
            vehicle.setVehicleType("HEAVY VEHICLE");
        }

        vehicle.setVehicleName(fieldVehicleName.getText());
        vehicle.setLicencePlate(fieldVehicleLicencePlate.getText());
        vehicle.setColor(fieldVehicleColor.getText());

        if(fieldVehicleName.getText().equals("") || fieldVehicleColor.getText().equals("") || fieldVehicleLicencePlate.getText().equals(""))
            vehicle = null;
        Stage stage = (Stage) buttonsaveAndBack.getScene().getWindow();
        stage.close();
    }
}
