package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ServiceController {

    public TextField fieldNameOfService;
    public DatePicker datePickerServiceDate;
    public TextField fieldServicePerson;
    public TextArea areaServiceInformation;
    public Button buttonSaveAndBack;

    private Service service = null;


    public ServiceController(){
        service = new Service();
    }

    public ServiceController(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    @FXML
    public void initialize() {
        if(service.getNameOfService() != null){
            fieldNameOfService.setText(service.getNameOfService());
            datePickerServiceDate.getEditor().setText(service.getServiceDateAsString());
            datePickerServiceDate.setValue(service.getDateOfService());
            fieldServicePerson.setText(service.getServicePerson());
            areaServiceInformation.setText(service.getInformation());
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        service = new Service();

        service.setNameOfService(fieldNameOfService.getText());
        if(datePickerServiceDate.getValue() != null)
            service.setDateOfService(datePickerServiceDate.getValue());
        else service.setDateOfService(LocalDate.now());
        service.setServicePerson(fieldServicePerson.getText());
        service.setInformation(areaServiceInformation.getText());

        if(fieldNameOfService.getText().equals("") || fieldServicePerson.getText().equals("")) {
            service = null;
            throw new WrongDataException("Wrong data!!!");
        }

        Stage stage = (Stage) buttonSaveAndBack.getScene().getWindow();
        stage.close();
    }

}
