package ets.gui.controller;

// imports
import ets.be.Coordinator;
import ets.be.Event;
import ets.gui.model.CoordinatorModel;
import ets.gui.model.EventModel;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateEventWindowController implements Initializable {

    @FXML
    private CheckComboBox selectCoordinators;
    @FXML
    private TextField nameField, locationField;
    @FXML
    private DatePicker dateField;
    private Runnable refreshCallback;
    private EventModel eventModel;
    private CoordinatorModel coordinatorModel;

    public void setCoordinatorModel(CoordinatorModel coordinatorModel) {
        this.coordinatorModel = coordinatorModel;

        selectCoordinators.setTitle("Coordinators");
        selectCoordinators.getItems().addAll(coordinatorModel.getCoordinators());

        coordinatorModel.getCoordinators().addListener((ListChangeListener<? super Coordinator>) obs->{
            selectCoordinators.getItems().clear();
            selectCoordinators.getItems().addAll(coordinatorModel.getCoordinators());
        });
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setRefreshCallback(Runnable refreshCallback) {this.refreshCallback = refreshCallback;}

    @FXML
    private void createBtn(ActionEvent actionEvent) {
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
                Event event = new Event(name, location, date);
                eventModel.createEvent(event);

                // assign coordinators to the event
                List<Coordinator> selectedItems = selectCoordinators.getCheckModel().getCheckedItems();
                for (Coordinator item : selectedItems) { //Loop
                    eventModel.assignEventCoordinator(event, new Coordinator(item.getId(), item.getUsername(), item.getPassword()));
                }

                // Call the refreshCallback
                if (refreshCallback != null) {
                    refreshCallback.run();
                }
            } catch (SQLException e) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace();
            }

            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelBtn(ActionEvent actionEvent){
        System.out.println("canceling...");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
