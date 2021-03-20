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
public class ServiceManagmentWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    ServiceManagementWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/service_management_window.fxml"));
        ctrl = new ServiceManagementWindowController(dao.getAllVehicles(dao.getMotorFleetById(1)),dao.getUserByID(2),dao.getMotorFleetById(1));
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
    public void testShowingListOfServiceWindow(FxRobot robot) {

        robot.clickOn("Motor vehicle");
        robot.clickOn("#buttonListoOfAllServices");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }
}
