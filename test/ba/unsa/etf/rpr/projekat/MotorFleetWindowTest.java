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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class MotorFleetWindowTest {

    Stage theStage;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/motor_fleet_window.fxml"));
        MotorFleetWindowController ctrl = new MotorFleetWindowController();
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
    public void testSearch(FxRobot robot) {
        TableView tableView = robot.lookup("#motorFleetTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldNameOfMotorFleet");
            robot.write("First motor fleet");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonlistOfAllMotorFleets");

            robot.clickOn("#fieldNameOfMotorFleet");
            robot.write("First motor fleet 2");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingWindowAddMotorFleet(FxRobot robot) {
        robot.clickOn("#buttonAddMotorFleet");
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingWindowDeleteMotorFleet(FxRobot robot) {
        robot.clickOn("#buttonDeleteMotorFleet");
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingWindowUpdateMotorFleet(FxRobot robot) {
        robot.clickOn("#buttonDeleteMotorFleet");
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testNextButton(FxRobot robot) {
        robot.clickOn("First motor fleet");
        robot.clickOn("#buttonNext");
        assertTrue(!theStage.isFocused());
    }
}
