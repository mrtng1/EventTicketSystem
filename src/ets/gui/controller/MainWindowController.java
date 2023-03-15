package ets.gui.controller;

// javafx imports
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

// java imports
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

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

    }
}
