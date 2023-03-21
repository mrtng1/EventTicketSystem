package ets.gui.controller;

// imports
import ets.gui.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void setModel(EventModel model){
        this.eventModel = model;
    }
    @FXML
    private void createBtn(ActionEvent event){
        System.out.println("creating...");

        String name = nameField.getText();
        String location = locationField.getText();
        LocalDate date = dateField.getValue();

        try {
            eventModel.createEvent(name, location, date);
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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
