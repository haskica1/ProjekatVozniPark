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
public class StorageWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    StorageWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/storage_window.fxml"));
        ctrl = new StorageWindowController(dao.getAllStorages(dao.getMotorFleetById(1)),dao.getUserByID(2),dao.getMotorFleetById(1));
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

        TableView tableView = robot.lookup("#storageTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldStorageName");
            robot.write("Storage 1");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonListAllStorage");

            robot.clickOn("#fieldStorageName");
            robot.write("Storage 11");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddStorageWindow(FxRobot robot) {

        robot.clickOn("#buttonAddStorage");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdateStorageWindow(FxRobot robot) {

        robot.clickOn("#buttonUpdateStorage");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingPartsStorageWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonListOfParts");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testBackWindow(FxRobot robot) {

        robot.clickOn("#buttonBack");

        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testAddEmployeeWindow(FxRobot robot) {

        robot.clickOn("#buttonAddStorage");

        robot.lookup("#fieldStorageName").tryQuery().isPresent();

        robot.clickOn("#fieldStorageName");
        robot.write("Random");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllStorages(dao.getMotorFleetById(1)).size());

    }

    @Test
    public void testUpdateEmployeeWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonUpdateStorage");

        robot.lookup("#fieldStorageName").tryQuery().isPresent();

        robot.clickOn("#fieldStorageName");
        robot.write("Random 2");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllStorages(dao.getMotorFleetById(1)).size());

    }
}
