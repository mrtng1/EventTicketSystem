package ets.gui.controller;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.gui.controller.create.CreateCustomerWindowController;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import ets.gui.model.TicketModel;
import ets.gui.util.MessagePopup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventInfoWindowController implements Initializable {

    @FXML
    private ListView<Customer> participantsList;
    @FXML
    private Label eventTitleLabel, locationLabel, dateLabel, noteLabel;
    private ScrollPane scrollPane;
    private Event event;
    private EventModel eventModel;
    private CustomerModel customerModel;
    private TicketModel ticketModel;
    private Customer customer;

    public void setModel(EventModel eventModel, CustomerModel customerModel, TicketModel ticketModel, ScrollPane scrollPane) {
        this.eventModel = eventModel;
        this.customerModel = customerModel;
        this.ticketModel = ticketModel;
        this.scrollPane = scrollPane;
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
            dateLabel.setText("Date: "+ event.getDate());
        }
        if (noteLabel != null) {
            String note = event.getNote();
            if (note.isEmpty()) {
                noteLabel.setText("Note is empty");
            } else {
                noteLabel.setText(note);
            }
        }

        try {customerModel.fetchAllCustomers(event);} catch (SQLException e) {throw new RuntimeException(e);}
        participantsList.setItems(customerModel.getCustomers());
    }

    @FXML
    private void deleteEvent(ActionEvent actionEvent){
        scrollPane.setEffect(null);
        try {eventModel.deleteEvent(this.event);} catch (SQLException e) {throw new RuntimeException(e);}

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent){
        scrollPane.setEffect(null);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void printTicket() {
        customer = participantsList.getSelectionModel().getSelectedItem();

        if (participantsList.getSelectionModel().getSelectedItem() == null) {
            MessagePopup.showAlert("Oops!", "You haven't selected a customer", Alert.AlertType.ERROR);
        }

        else {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/ticket_inspect_window.fxml"));
                Parent createEventParent = fxmlLoader.load();

                TicketWindowController ticketWindowController = fxmlLoader.getController();
                ticketWindowController.setModel(new EventModel(), new CustomerModel());
                ticketWindowController.setDetails(event, customer);

                // Create a new stage and scene
                Stage stage = new Stage();
                stage.setResizable(false);
                Scene scene = new Scene(createEventParent);
                stage.setScene(scene);

                // Show the new stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addParticipant() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_customer_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            stage.setResizable(false);
            stage.setTitle("Create Customer");
            stage.setScene(new Scene(createEventParent));

            CreateCustomerWindowController customerWindowController = fxmlLoader.getController();
            customerWindowController.setModel(new CustomerModel(), new EventModel(), new TicketModel());
            customerWindowController.setEvent(event);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteParticipant(ActionEvent actionEvent) throws SQLException {
        Customer selectedItem = participantsList.getSelectionModel().getSelectedItem();
        customerModel.deleteCustomers(selectedItem);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (event != null) {
            eventTitleLabel.setText(event.getName());
        }
    }
}
