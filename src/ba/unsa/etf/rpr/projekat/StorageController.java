package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StorageController {

    public TextField fieldStorageName;
    public ChoiceBox<User> choiceBoxStorageManager = new ChoiceBox<>();
    public Button buttonSaveAndBack;

    private Storage storage = null;
    private ObservableList<User> userObservableList;
    private MotorFleetDAO dao;

    public Storage getStorage() {
        return storage;
    }

    public StorageController(ArrayList<User> users) {
        userObservableList = FXCollections.observableArrayList(users);
    }

    public StorageController(ArrayList<User> users, Storage storage) {
        userObservableList = FXCollections.observableArrayList(users);
        this.storage = storage;
    }
    @FXML
    public void initialize(){
        dao = MotorFleetDAO.getInstance();
        choiceBoxStorageManager.setItems(userObservableList);
        if(storage != null){
            fieldStorageName.setText(storage.getNameOfStorage());
            int indexOfUser = 0;
            for(int i = 0; i < userObservableList.size();i++){
                if(userObservableList.get(i).equals(storage.getManager())){
                    indexOfUser = i;
                    break;
                }
            }
            choiceBoxStorageManager.getSelectionModel().select(indexOfUser);
        }
    }

    public void actionSaveAndBack() throws WrongDataException {
        Boolean noProblem = true;
        if(dao.isThereStorageWithSameName(fieldStorageName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong name of storage!!!");
            alert.setContentText("There is storage with the same name.");
            noProblem = false;
            alert.showAndWait();
        }

        if(!noProblem){
            throw new WrongDataException("Wrong data!!!");
            //return;
        }

        storage = new Storage();

        storage.setNameOfStorage(fieldStorageName.getText());
        User user;
        user = choiceBoxStorageManager.getValue();
        if(user != null)
            storage.setManager(user);
        else
            storage.setManager(choiceBoxStorageManager.getItems().get(0));
        if(fieldStorageName.getText().equals(""))   storage = null;

        Stage stage = (Stage) buttonSaveAndBack.getScene().getWindow();
        stage.close();
    }

}
