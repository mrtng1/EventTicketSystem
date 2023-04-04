package ets.gui.controller.create;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
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

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateCustomerWindowController implements Initializable {
    
    @FXML
    private TextField nameField, emailField;
    private CustomerModel customerModel;
    private EventModel eventModel;
    private Event event;

    public void setModel(CustomerModel customerModel, EventModel eventModel) {
        this.customerModel = customerModel;
        this.eventModel = eventModel;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void createBtn(ActionEvent actionEvent) throws SQLException {
        String name = nameField.getText();
        String email = emailField.getText();

        Customer customer = new Customer(name, email);
        customerModel.createCustomer(customer);
        eventModel.joinEvent(event, customer);

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void cancelBtn(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
