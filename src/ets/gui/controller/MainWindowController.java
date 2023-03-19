package ets.gui.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane eventPane;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        double startValue = scrollPane.getVvalue();
        double endValue = startValue + 1;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(scrollPane.vvalueProperty(), endValue, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

}