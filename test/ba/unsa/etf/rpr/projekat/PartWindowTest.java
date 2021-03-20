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
public class PartWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    PartWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/part_window.fxml"));
        ctrl = new PartWindowController(dao.getAllParts(dao.getMotorFleetById(1)),dao.getUserByID(2),dao.getMotorFleetById(1));
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

        TableView tableView = robot.lookup("#partTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldPartId");
            robot.write("1");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonListOfAllParts");

            robot.clickOn("#fieldPartId");
            robot.write("11");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddPartWindow(FxRobot robot) {

        robot.clickOn("#buttonAddPart");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdatePartWindow(FxRobot robot) {

        robot.clickOn("1");
        robot.clickOn("#buttonUpdatePart");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testBackWindow(FxRobot robot) {

        robot.clickOn("#buttonBack");

        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testAddPartWindow(FxRobot robot) {

        robot.clickOn("#buttonAddPart");

        robot.lookup("#textFieldPartName").tryQuery().isPresent();

        robot.clickOn("#radioButtonOtherPart");

        robot.clickOn("#textFieldPartName");
        robot.write("Random");

        robot.clickOn("#radioButtonPartUseful");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(3,dao.getAllParts(dao.getMotorFleetById(1)).size());

    }

    @Test
    public void testUpdatePartWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonUpdatePart");

        robot.lookup("#textFieldPartName").tryQuery().isPresent();

        robot.clickOn("#textFieldPartName");
        robot.write(" 2");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(3,dao.getAllParts(dao.getMotorFleetById(1)).size());

    }
}
