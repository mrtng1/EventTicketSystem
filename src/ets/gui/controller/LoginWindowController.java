package ets.gui.controller;

// imports
import ets.be.Coordinator;
import ets.gui.model.AdminModel;
import ets.gui.model.CoordinatorModel;
import ets.gui.util.MessagePopup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class LoginWindowController implements Initializable {

    // instance variables with @FXML
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private TextField passwordField, nameField;

    // instance variables
    private AdminModel adminModel;
    private CoordinatorModel coordinatorModel;

    /**
     * Login method - allows user to log in either as an admin or as a coordinator
     */
    public void logIn() {
        String inputUsername = nameField.getText();
        String inputPassword = passwordField.getText();

        if (adminModel.isValidAdmin(inputUsername, inputPassword)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/admin_window.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Window");
                stage.show();

                Stage currentStage = (Stage) loginAnchorPane.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (coordinatorModel.isValidCoordinator(inputUsername, inputPassword)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/coordinator_window.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.setTitle("Coordinator Window");
                stage.show();

                // Get the Coordinator object for the logged-in user
                Coordinator loggedInCoordinator = coordinatorModel.getCoordinatorByUsername(inputUsername);

                // Pass the Coordinator object to the CoordinatorWindowController
                CoordinatorWindowController controller = fxmlLoader.getController();
                controller.setCoordinator(loggedInCoordinator);

                Stage currentStage = (Stage) loginAnchorPane.getScene().getWindow();
                currentStage.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            MessagePopup.showAlert("Invalid Credentials", "Username or password is incorrect", Alert.AlertType.ERROR);
        }
    }

    /**
     * Initialize method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminModel = new AdminModel();
        coordinatorModel = new CoordinatorModel();
    }
}
