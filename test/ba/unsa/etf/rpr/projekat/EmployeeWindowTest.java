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
public class EmployeeWindowTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    UserWindowController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user_window.fxml"));
        ctrl = new UserWindowController(dao.getAllUsers(dao.getMotorFleetById(1)),dao.getUserByID(2),dao.getMotorFleetById(1));
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

        TableView tableView = robot.lookup("#userTableView").queryAs(TableView.class);

        if(tableView.getItems().size() > 0){

            robot.clickOn("#fieldFirstName");
            robot.write("User 2");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() != 0);

            robot.clickOn("#buttonListOfAllUsers");

            robot.clickOn("#fieldFirstName");
            robot.write("User 22");
            robot.clickOn("#buttonSearch");
            assertTrue(tableView.getItems().size() == 0);
        }
    }

    @Test
    public void testShowingAddEmployeeWindow(FxRobot robot) {

        robot.clickOn("#buttonAdd");

        assertTrue(theStage.isShowing());
        assertTrue(!theStage.isFocused());
    }

    @Test
    public void testShowingUpdateEmployeeWindow(FxRobot robot) {

        robot.clickOn("#buttonUpdate");

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

        robot.clickOn("#buttonAdd");

        robot.lookup("#fieldTelephoneNumber").tryQuery().isPresent();

        robot.clickOn("#fieldFirstName");
        robot.write("Random");

        robot.clickOn("#fieldLastName");
        robot.write("Random");

        robot.clickOn("#fieldTelephoneNumber");
        robot.write("Random");

        robot.clickOn("#fieldAddres");
        robot.write("Random");

        robot.clickOn("#fieldUsername");
        robot.write("Random");

        robot.clickOn("#fieldPassword");
        robot.write("Random");

        robot.clickOn("#radioButtonWorker");

        robot.clickOn("#buttonsaveAndBack");

        assertEquals(3,dao.getAllUsers(dao.getMotorFleetById(1)).size());

    }

    @Test
    public void testUpdateEmployeeWindow(FxRobot robot) {

        robot.clickOn("Random");
        robot.clickOn("#buttonUpdate");

        robot.lookup("#fieldTelephoneNumber").tryQuery().isPresent();

        robot.clickOn("#fieldFirstName");
        robot.write("Random2");

        robot.clickOn("#buttonsaveAndBack");

        assertEquals(3,dao.getAllUsers(dao.getMotorFleetById(1)).size());

    }
}
