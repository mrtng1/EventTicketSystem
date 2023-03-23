package ets.gui.controller;

// imports
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

// java imports
import java.awt.*;
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
}
