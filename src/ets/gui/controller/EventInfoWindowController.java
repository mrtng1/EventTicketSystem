package ets.gui.controller;

// imports
import ets.be.Event;
import ets.gui.model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventInfoWindowController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Label eventTitleLabel, locationLabel, dateLabel;
    private Event event;
    EventModel eventModel = new EventModel();



    public EventInfoWindowController() {
    }

    public void setEvent(Event event) {
        this.event = event;
        if (eventTitleLabel != null) {
            eventTitleLabel.setText(event.getName());
        }
        if (locationLabel != null){
            locationLabel.setText("Location: "+event.getLocation());
        }

        if(dateLabel != null) {
            dateLabel.setText("Date: "+String.valueOf(event.getDate()));
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

    @FXML
    private void deleteEventBtn(javafx.event.ActionEvent actionEvent){
        try {
            eventModel.deleteEvent(this.event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
}
