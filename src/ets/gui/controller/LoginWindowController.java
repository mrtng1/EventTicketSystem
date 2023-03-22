package ets.gui.controller;

import ets.gui.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class LoginWindowController implements Initializable {

    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private Button loginButton;

    public void openMainWindow(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/admin_window.fxml"));
            Parent adminWindow = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setResizable(false);
            Scene scene = new Scene(adminWindow);
            stage.setScene(scene);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
