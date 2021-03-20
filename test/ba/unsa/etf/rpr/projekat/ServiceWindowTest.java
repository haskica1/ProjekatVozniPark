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
public class ServiceWindowTest {
    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    ServiceWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/service_window.fxml"));
        ctrl = new ServiceWindowController(dao.getVehiclesById(1),dao.getUserByID(2),dao.getMotorFleetById(1));
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

        TableView tableView = robot.lookup("#tableViewServices").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldServiceName");
            robot.write("Service 1");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonListOfAllService");

            robot.clickOn("#fieldServiceName");
            robot.write("Service 11");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddSerivceWindow(FxRobot robot) {

        robot.clickOn("#buttonAddService");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdateServiceWindow(FxRobot robot) {

        robot.clickOn("Service 1");
        robot.clickOn("#buttonUpdateService");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testBackWindow(FxRobot robot) {

        robot.clickOn("#buttonBack");

        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testAddServiceWindow(FxRobot robot) {

        robot.clickOn("#buttonAddService");

        robot.lookup("#fieldNameOfService").tryQuery().isPresent();

        robot.clickOn("#fieldNameOfService");
        robot.write("Random");

        robot.clickOn("#fieldServicePerson");
        robot.write("Random");

        robot.clickOn("#areaServiceInformation");
        robot.write("Random");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllServiceOfVehicle(dao.getVehiclesById(1)).size());

    }

    @Test
    public void testUpdateServiceWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonUpdateService");

        robot.lookup("#fieldStorageName").tryQuery().isPresent();

        robot.clickOn("#fieldNameOfService");
        robot.write(" 2");

        robot.clickOn("#buttonSaveAndBack");

        assertEquals(2,dao.getAllServiceOfVehicle(dao.getVehiclesById(1)).size());

    }
}
