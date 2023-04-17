package ets.gui.controller;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.bll.util.QRCodeGenerator;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// java imports
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 *
 * @author tomdra01, mrtng1
 */
public class TicketWindowController implements Initializable {

    @FXML
    private Label ticketEvent, ticketLocation, ticketDateAndTime, ticketParticipantName;
    @FXML
    private ImageView imgQRCode;
    EventModel eventModel;
    CustomerModel customerModel;
    Event event;
    Customer customer;

    public void setModel(EventModel eventModel, CustomerModel customerModel) {
        this.eventModel = eventModel;
        this.customerModel = customerModel;
    }

    public void setDetails(Event event, Customer customer) {
        this.event = event;
        this.customer = customer;
        ticketInitialize();
    }

    public void ticketInitialize(){
        ticketEvent.setText(event.getName());
        ticketLocation.setText(event.getLocation());
        ticketDateAndTime.setText(event.getDate() +", " +event.getTime());
        ticketParticipantName.setText(customer.getName());

        try {
            Image qrCode = SwingFXUtils.toFXImage(QRCodeGenerator.generateQRCodeImage(UUID.randomUUID().toString()), null);
            imgQRCode.setImage(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
