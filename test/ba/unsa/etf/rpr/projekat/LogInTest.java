package ba.unsa.etf.rpr.projekat;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class LogInTest {

    Stage theStage;
    MotorFleetDAO dao = MotorFleetDAO.getInstance();
    LogInController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
        ctrl = new LogInController(dao.getMotorFleetById(1));
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
    public void testCorrectData(FxRobot robot) {
        robot.clickOn("#fieldUserName");
        robot.write("v123");
        robot.clickOn("#fieldPassword");
        robot.write("v123");
        robot.clickOn("#buttonNext");
        assertTrue(!theStage.isShowing());
    }

    @Test
    public void testWrongData(FxRobot robot) {
        TextField textField = robot.lookup("#fieldUserName").queryAs(TextField.class);
        PasswordField passwordField = robot.lookup("#fieldPassword").queryAs(PasswordField.class);

        robot.clickOn("#fieldUserName");
        robot.write("v1234");
        robot.clickOn("#fieldPassword");
        robot.write("v1234");
        robot.clickOn("#buttonNext");
        assertTrue(theStage.isShowing());

/*
        //fali kod da se iskljuci alter window


        Background bg = textField.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        Background bg2 = passwordField.getBackground();
        boolean colorFound2 = false;
        for (BackgroundFill bf2 : bg2.getFills())
            if (bf2.getFill().toString().contains("ffb6c1"))
                colorFound2 = true;
        assertTrue(colorFound2);
*/
    }
}
