package ets.gui.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), endValue, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateGridPane();
    }

    private void populateGridPane() {
        int numRows = 5;
        int numColumns = 3;
        String imagePath = "/ets/gui/view/images/questionmark.png";
        double imageWidth = 1;
        double imageHeight = 1;
        double imageViewWidth = 100;
        double imageViewHeight = imageViewWidth * (imageHeight / imageWidth);
        String borderColor = "#000000"; // You can change this to any color you like (e.g., #FF0000 for red)
        double borderWidth = 1.0; // Adjust the border width as needed

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                Pane pane = new Pane();

                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(imageViewWidth);
                imageView.setFitHeight(imageViewHeight);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                // Create the label
                Label label = new Label("Empty event");

                // Create the button and style it
                Button viewBtn = new Button("View");
                viewBtn.setStyle("-fx-background-radius: 20;");

                // Create an HBox for the ImageView and label, adjust spacing
                HBox imageLabelHBox = new HBox(10, imageView, label);
                imageLabelHBox.setAlignment(Pos.TOP_CENTER);
                imageLabelHBox.setSpacing(2);

                // Wrap the button in an HBox and set its alignment
                HBox buttonHBox = new HBox(viewBtn);
                buttonHBox.setAlignment(Pos.CENTER_RIGHT);

                // Create a VBox for the HBox and button, adjust spacing
                VBox contentVBox = new VBox(2, imageLabelHBox, buttonHBox);
                contentVBox.setAlignment(Pos.TOP_CENTER);
                contentVBox.setPrefSize(pane.getPrefWidth(), pane.getPrefHeight());
                contentVBox.setSpacing(-20);

                pane.getChildren().add(contentVBox);
                pane.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: " + borderWidth + ";");
                eventPane.add(pane, col, row);
            }
        }
    }

}