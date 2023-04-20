package ets.gui.controller.create;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.be.Ticket;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import ets.gui.model.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Consumer;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateCustomerWindowController implements Initializable {
    
    @FXML
    private TextField nameField, emailField;
    private CustomerModel customerModel;
    private EventModel eventModel;
    private TicketModel ticketModel;
    private Event event;

    public void setModel(CustomerModel customerModel, EventModel eventModel, TicketModel ticketModel) {
        this.customerModel = customerModel;
        this.eventModel = eventModel;
        this.ticketModel = ticketModel;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void createParticipant(ActionEvent actionEvent) throws SQLException {
        String name = nameField.getText();
        String email = emailField.getText();

        Customer customer = new Customer(name, email);
        customerModel.createCustomer(customer);
        eventModel.joinEvent(event, customer);
        customerModel.fetchAllCustomers(event);

        Ticket ticket = new Ticket(UUID.randomUUID(), "Event Ticket", event, customer);
        ticketModel.createTicket(ticket);



        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
