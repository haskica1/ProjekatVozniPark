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
public class EquipmentWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    EquipmentWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/equipment_window.fxml"));
        ctrl = new EquipmentWindowController(dao.getAllEquipment(dao.getMotorFleetById(1)),dao.getMotorFleetById(1));
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

        TableView tableView = robot.lookup("#equipmentTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldEquipmentName");
            robot.write("Equipment 1");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonListOfAllEqupiment");

            robot.clickOn("#fieldEquipmentName");
            robot.write("Equipment 11");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddEquipmentWindow(FxRobot robot) {

        robot.clickOn("#buttonAddEquipment");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdateEquipmentWindow(FxRobot robot) {

        robot.clickOn("Equipment 1");
        robot.clickOn("#buttonUpdateEquipment");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testBackWindow(FxRobot robot) {

        robot.clickOn("#buttonBack");

        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testAddEquipmentWindow(FxRobot robot) {

        robot.clickOn("#buttonAddEquipment");

        robot.lookup("#fieldEquipmentName").tryQuery().isPresent();

        robot.clickOn("#radioButtonOtherEquipment");

        robot.clickOn("#fieldEquipmentName");
        robot.write("Random");

        robot.clickOn("#areaEquipmentInformation");
        robot.write("Random");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllEquipment(dao.getMotorFleetById(1)).size());

    }

    @Test
    public void testUpdateEquipmentWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonUpdateEquipment");

        robot.lookup("#fieldEquipmentName").tryQuery().isPresent();

        robot.clickOn("#fieldEquipmentName");
        robot.write(" 2");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllEquipment(dao.getMotorFleetById(1)).size());

    }
}
