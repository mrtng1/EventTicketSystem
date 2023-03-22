package ets.gui.controller;

// imports
import ets.gui.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateEventController implements Initializable {
    private EventModel eventModel;
    @FXML
    private TextField nameField, locationField;
    @FXML
    private DatePicker dateField;
    private Runnable refreshCallback;

    public void setModel(EventModel model){
        this.eventModel = model;
    }
    public void setRefreshCallback(Runnable refreshCallback) {this.refreshCallback = refreshCallback;}
    @FXML
    private void createBtn(ActionEvent event) {
        System.out.println("creating...");

        String name = nameField.getText();
        String location = locationField.getText();
        LocalDate date = dateField.getValue();

        if (name.isEmpty() || location.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
        } else {
            try {
                eventModel.createEvent(name, location, date);
                // Call the refreshCallback
                if (refreshCallback != null) {
                    refreshCallback.run();
                }
            } catch (SQLException e) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelBtn(ActionEvent event){
        System.out.println("canceling...");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
