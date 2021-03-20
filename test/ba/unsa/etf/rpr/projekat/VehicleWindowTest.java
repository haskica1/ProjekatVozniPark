package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class VehicleWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    VehicleWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicle_window.fxml"));
        ctrl = new VehicleWindowController(dao.getAllVehicles(dao.getMotorFleetById(1)),dao.getUserByID(2),dao.getMotorFleetById(1));
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Log in");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testShowingWindow() {
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testSearching(FxRobot robot){
        TableView tableView = robot.lookup("#vehicleTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldVehicleName");
            robot.write("Motor vehicle");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonVehicleList");

            robot.clickOn("#fieldVehicleName");
            robot.write("Motor vehicle 2");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddVehicleWindow(FxRobot robot) {

        robot.clickOn("#buttonAddVehicle");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdateVehicleWindow(FxRobot robot) {

        robot.clickOn("#buttonUpdateVehicle");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testBackWindow(FxRobot robot) {

        robot.clickOn("#buttonBack");

        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testShowingEquipmentWindow(FxRobot robot) {

        robot.clickOn("#buttonListOfAllEquipment");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testAddVehicleWindow(FxRobot robot) {

        robot.clickOn("#buttonAddVehicle");

        robot.lookup("#fieldVehicleName").tryQuery().isPresent();

        robot.clickOn("#fieldVehicleName");
        robot.write("Random vehicle");

        robot.clickOn("#fieldVehicleLicencePlate");
        robot.write("000-100");

        robot.clickOn("#fieldVehicleColor");
        robot.write("Color100");


        robot.clickOn("#radioButtonMotorVehicle");
        robot.clickOn("#buttonsaveAndBack");

        assertEquals(4,dao.getAllVehicles(dao.getMotorFleetById(1)).size());

    }

    @Test
    public void testUpdateVehicleWindow(FxRobot robot) {

        robot.clickOn("Random vehicle");
        robot.clickOn("#buttonUpdateVehicle");

        robot.lookup("#fieldVehicleName").tryQuery().isPresent();

        robot.clickOn("#fieldVehicleName");
        robot.write("2");

        robot.clickOn("#buttonsaveAndBack");

        assertEquals(4,dao.getAllVehicles(dao.getMotorFleetById(1)).size());

    }

}
