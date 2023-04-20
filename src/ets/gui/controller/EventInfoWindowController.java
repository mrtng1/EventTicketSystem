package ets.gui.controller;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.be.Ticket;
import ets.gui.controller.create.CreateCustomerWindowController;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import ets.gui.model.TicketModel;
import ets.gui.util.BlurEffectUtil;
import ets.gui.util.MessagePopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventInfoWindowController implements Initializable {

    @FXML
    private ListView<Customer> participantsList;
    @FXML
    private TextField searchField;
    @FXML
    private Label eventTitleLabel, locationLabel, dateLabel, noteLabel;
    private ScrollPane scrollPane;
    private EventModel eventModel;
    private CustomerModel customerModel;
    private TicketModel ticketModel;
    private Ticket ticket;
    private Event event;
    private Customer customer;
    private Consumer<Event> onDeleteEventCallback;

    private boolean sortAscending = true;


    public void setModel(EventModel eventModel, CustomerModel customerModel, TicketModel ticketModel, ScrollPane scrollPane) {
        this.eventModel = eventModel;
        this.customerModel = customerModel;
        this.ticketModel = ticketModel;
        this.scrollPane = scrollPane;
    }

    public void setOnDeleteEventCallback(Consumer<Event> onDeleteEventCallback) {
        this.onDeleteEventCallback = onDeleteEventCallback;
    }

    public void updateParticipantsList() {
        try {
            customerModel.fetchAllCustomers(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void sortButtonHandle(ActionEvent actionEvent) {
        ObservableList<Customer> items = participantsList.getItems();
        ObservableList<Customer> sortedItems = FXCollections.observableArrayList(items);

        int n = sortedItems.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Ascending order sorting
                if (sortAscending) {
                    if (sortedItems.get(j).getName().compareToIgnoreCase(sortedItems.get(j + 1).getName()) > 0) {
                        Customer temp = sortedItems.get(j);
                        sortedItems.set(j, sortedItems.get(j + 1));
                        sortedItems.set(j + 1, temp);
                    }
                }
                // Descending order sorting
                else {
                    if (sortedItems.get(j).getName().compareToIgnoreCase(sortedItems.get(j + 1).getName()) < 0) {
                        Customer temp = sortedItems.get(j);
                        sortedItems.set(j, sortedItems.get(j + 1));
                        sortedItems.set(j + 1, temp);
                    }
                }
            }
        }

        // Reverse the sort order for the next click
        sortAscending = !sortAscending;

        participantsList.setItems(sortedItems);
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

        FilteredList<Customer> filteredCustomers = new FilteredList<>(customerModel.getCustomers(), s -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredCustomers.setPredicate(customer -> {
            // If the search field is empty, show all customers
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            return customer.getName().toLowerCase().contains(lowerCaseFilter);
        }));

        // Create a SortedList and bind it to the FilteredList
        SortedList<Customer> sortedCustomers = new SortedList<>(filteredCustomers);
        participantsList.setItems(sortedCustomers);

    }

    @FXML
    private void deleteEvent(ActionEvent actionEvent) {

        Optional<ButtonType> result = MessagePopup.showConfirmationAlert("Delete Event", "Are you sure you want to delete this event?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                eventModel.deleteEvent(this.event);
                if (onDeleteEventCallback != null) {
                    onDeleteEventCallback.accept(event);
                    BlurEffectUtil.removeBlurEffect(scrollPane);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent){
        BlurEffectUtil.removeBlurEffect(scrollPane);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getTicket() {
        customer = participantsList.getSelectionModel().getSelectedItem();
        ticket = new Ticket("Event ticket", event, customer);
        System.out.println(ticket);

        if (participantsList.getSelectionModel().getSelectedItem() == null) {
            MessagePopup.showAlert("Oops!", "You haven't selected a customer", Alert.AlertType.ERROR);
        }

        else {
            try {
                // Load the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/ticket_preview.fxml"));
                Parent createEventParent = fxmlLoader.load();

                TicketWindowController ticketWindowController = fxmlLoader.getController();
                ticketWindowController.setModel(eventModel, customerModel, ticketModel);
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

    public void getCoupon() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_coupon_window.fxml"));
            Parent createCouponParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(createCouponParent);
            stage.setScene(scene);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

            try {
                customerModel.fetchAllCustomers(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Add an onHidden event handler to update the participants list
            stage.setOnHidden(e -> updateParticipantsList());

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
