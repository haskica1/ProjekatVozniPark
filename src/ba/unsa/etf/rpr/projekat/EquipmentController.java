package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EquipmentController {

    public TextField fieldEquipmentName;
    public TextArea areaEquipmentInformation;
    public Button buttonSaveAndBack;
    public RadioButton radioButtonSelectEqiupment;
    public RadioButton radioButtonOtherEquipment;
    public ToggleGroup toggleGroup = new ToggleGroup();
    public ChoiceBox<Equipment> equipmentChoiceBox;

    private MotorFleetDAO dao;
    private Equipment equipment = null;
    private User user;
    private MotorFleet motorFleet;

    public EquipmentController(User user, MotorFleet motorFleet) {this.user = user;
    this.motorFleet = motorFleet;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public EquipmentController(Equipment equipment, MotorFleet motorFleet){
        this.equipment = equipment;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        equipmentChoiceBox.setItems(FXCollections.observableArrayList(dao.getAllEquipment(motorFleet)));
        radioButtonOtherEquipment.setToggleGroup(toggleGroup);
        radioButtonSelectEqiupment.setToggleGroup(toggleGroup);
        radioButtonSelectEqiupment.setSelected(false);
        radioButtonOtherEquipment.setSelected(false);
        equipmentChoiceBox.setDisable(true);
        fieldEquipmentName.setDisable(true);
        areaEquipmentInformation.setDisable(true);

        if(equipment != null) {
            fieldEquipmentName.setText(equipment.getNameOfEquipment());
            areaEquipmentInformation.setText(equipment.getInformation());
        }

        if(user == null){
            radioButtonOtherEquipment.setDisable(false);
            radioButtonSelectEqiupment.setDisable(true);
            equipmentChoiceBox.setDisable(true);
            fieldEquipmentName.setDisable(false);
            areaEquipmentInformation.setDisable(false);
            radioButtonOtherEquipment.setSelected(true);
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        if(radioButtonSelectEqiupment.isSelected()){
            equipment = equipmentChoiceBox.getSelectionModel().getSelectedItem();
        }else {
            equipment = new Equipment(fieldEquipmentName.getText(), areaEquipmentInformation.getText());

            if (fieldEquipmentName.getText().equals("")) {
                equipment = null;
                throw new WrongDataException("Wrong Data!!!");
            }
        }

        Stage stage = (Stage) buttonSaveAndBack.getScene().getWindow();
        stage.close();
    }

    public void actionClickRadioButton() {
        if(radioButtonSelectEqiupment.isSelected()){
            equipmentChoiceBox.setDisable(false);
            fieldEquipmentName.setDisable(true);
            areaEquipmentInformation.setDisable(true);
        }else {
            fieldEquipmentName.setDisable(false);
            areaEquipmentInformation.setDisable(false);
            equipmentChoiceBox.setDisable(true);
        }
    }
}
