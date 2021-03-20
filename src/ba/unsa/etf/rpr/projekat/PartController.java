package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PartController {

    public TextField textFieldPartName;
    public RadioButton radioButtonPartUseful;
    public RadioButton radioButtonPartNotUseful;
    public ToggleGroup toggleGroup = new ToggleGroup();
    public Button buttonSaveAndBack;
    public ChoiceBox<Part> partChoiceBox;
    public RadioButton radioButtonSelectPart;
    public RadioButton radioButtonOtherPart;
    public ToggleGroup toggleGroupSelectOption = new ToggleGroup();

    private Part part = null;
    private MotorFleetDAO dao;
    private Storage storage;
    private MotorFleet motorFleet;

    public Part getPart() {
        return part;
    }

    public PartController() {
    }

    public PartController(Storage storage, MotorFleet motorFleet) {
        this.storage = storage;
        this.motorFleet = motorFleet;
    }

    public PartController(Part part, MotorFleet motorFleet) {
        this.part = part;
        this.motorFleet = motorFleet;
    }

    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        partChoiceBox.setItems(FXCollections.observableArrayList(dao.getAllParts(motorFleet)));

        radioButtonSelectPart.setToggleGroup(toggleGroupSelectOption);
        radioButtonOtherPart.setToggleGroup(toggleGroupSelectOption);
        radioButtonSelectPart.setSelected(false);
        radioButtonOtherPart.setSelected(false);
        textFieldPartName.setDisable(true);
        radioButtonPartUseful.setDisable(true);
        radioButtonPartNotUseful.setDisable(true);
        partChoiceBox.setDisable(true);

        radioButtonPartNotUseful.setToggleGroup(toggleGroup);
        radioButtonPartUseful.setToggleGroup(toggleGroup);

        if(part != null){
            textFieldPartName.setText(part.getNameOfPart());
            if(part.isUsefulPart()) radioButtonPartUseful.setSelected(true);
            else radioButtonPartNotUseful.setSelected(true);
        }

        if(storage == null){
            radioButtonSelectPart.setSelected(false);
            radioButtonOtherPart.setSelected(true);
            textFieldPartName.setDisable(false);
            radioButtonPartUseful.setDisable(false);
            radioButtonPartNotUseful.setDisable(false);
            partChoiceBox.setDisable(true);
            radioButtonSelectPart.setDisable(true);
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        if(radioButtonSelectPart.isSelected()){
            part = partChoiceBox.getSelectionModel().getSelectedItem();
        }else {
            //if (part == null)
                part = new Part();
            part.setNameOfPart(textFieldPartName.getText());
            if (radioButtonPartUseful.isSelected()) part.setUsefulPart(true);
            else if (radioButtonPartNotUseful.isSelected()) part.setUsefulPart(false);

            if (textFieldPartName.getText().equals("")) {
                part = null;
                throw new WrongDataException("Wrong Data!!!");
            }
        }

        Stage stage = (Stage) buttonSaveAndBack.getScene().getWindow();
        stage.close();
    }

    public void actionClickRadioButton(){
        if(radioButtonSelectPart.isSelected()){
            partChoiceBox.setDisable(false);
            textFieldPartName.setDisable(true);
            radioButtonPartUseful.setDisable(true);
            radioButtonPartNotUseful.setDisable(true);
        }else{
            partChoiceBox.setDisable(true);
            textFieldPartName.setDisable(false);
            radioButtonPartUseful.setDisable(false);
            radioButtonPartNotUseful.setDisable(false);
        }
    }

}
