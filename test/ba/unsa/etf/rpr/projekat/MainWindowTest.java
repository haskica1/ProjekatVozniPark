package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class MainWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    MainWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_window.fxml"));
        ctrl = new MainWindowController(dao.getUserByID(2),dao.getMotorFleetById(1));
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
    public void testVehicle(FxRobot robot) {
        robot.clickOn("#buttonVehicle");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testEmployee(FxRobot robot) {
        robot.clickOn("#buttonEmployee");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testStorage(FxRobot robot) {
        robot.clickOn("#buttonStorage");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testService(FxRobot robot) {
        robot.clickOn("#buttonService");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testEquipment(FxRobot robot) {
        robot.clickOn("#buttonEquipment");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testPart(FxRobot robot) {
        robot.clickOn("#buttonListOfAllParts");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testVehicleReport(FxRobot robot) {
        robot.clickOn("#buttonVehiclesReport");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testEmployeeReport(FxRobot robot) {
        robot.clickOn("#buttonEmployeeReport");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testStorageReport(FxRobot robot) {
        robot.clickOn("#buttonStorageReport");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testEquipmentReport(FxRobot robot) {
        robot.clickOn("#buttonEquipmentReport");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testPartReport(FxRobot robot) {
        robot.clickOn("#buttonPartReport");
        assertTrue(theStage.isShowing());
    }

    @Test
    public void testServiceReport(FxRobot robot) {
        robot.clickOn("#buttonServiceReport");
        assertTrue(theStage.isShowing());
    }
}
