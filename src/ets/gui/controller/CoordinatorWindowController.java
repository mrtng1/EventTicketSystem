package ets.gui.controller;

// imports
import ets.gui.model.CoordinatorModel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

// java imports
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane eventPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void viewEventsBtn(ActionEvent event) {
        double startValue = scrollPane.getVvalue();
        double endValue = startValue + 1;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), endValue, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }

    @FXML
    private void createCoordinatorBtn(ActionEvent event){
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_coordinator_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            stage.setResizable(false);
            stage.setTitle("Create Coordinator");
            stage.setScene(new Scene(createEventParent));

            CreateCoordinatorWindowController createCoordinatorWindowController = fxmlLoader.getController();
            createCoordinatorWindowController.setModel(new CoordinatorModel());

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
