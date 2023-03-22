package ets.gui.controller;

// imports
import ets.be.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventCardViewController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label eventTitleLabel;
    private Event event;

    public EventCardViewController() {
    }

    public void setEvent(Event event) {
        this.event = event;
        if (eventTitleLabel != null) {
            eventTitleLabel.setText(event.getName());
        }
    }

    @FXML
    private void closeBtn(javafx.event.ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (event != null) {
            eventTitleLabel.setText(event.getName());
        }
    }
}
