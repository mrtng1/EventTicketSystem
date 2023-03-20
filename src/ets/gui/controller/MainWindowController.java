package ets.gui.controller;

// imports
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// java imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class MainWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane eventPane;

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
    private void createEventBtn(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/createEvent.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage createEventStage = new Stage();
            createEventStage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            createEventStage.initStyle(StageStyle.TRANSPARENT);
            createEventStage.setTitle("Create Event");
            createEventStage.setScene(new Scene(createEventParent));

            // Show the new stage
            createEventStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createCoordinatorBtn(ActionEvent event){
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/createCoordinator.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage createEventStage = new Stage();
            createEventStage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            createEventStage.initStyle(StageStyle.TRANSPARENT);
            createEventStage.setTitle("Create Coordinator");
            createEventStage.setScene(new Scene(createEventParent));

            // Show the new stage
            createEventStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateGridPane() throws IOException {
        int numRows = 5;
        int numColumns = 3;
        String borderColor = "#000000"; // You can change this to any color you like (e.g., #FF0000 for red)
        double borderWidth = 1.0; // Adjust the border width as needed

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                Pane pane = new Pane();

                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/eventCard.fxml"));
                Pane contentPane = fxmlLoader.load();

                // Add the loaded content to the pane
                pane.getChildren().add(contentPane);

                // Apply the border outline
                pane.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: " + borderWidth + ";");

                eventPane.add(pane, col, row);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}