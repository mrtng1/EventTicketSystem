package ets.gui.controller;

// imports
import ets.be.Event;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import ets.gui.model.TicketModel;
import ets.gui.util.BlurEffectUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// java imports
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventCardController implements Initializable {

    // instance variables with @FXML
    @FXML
    private ImageView eventImage;
    @FXML
    private Label participants, timeNumber, eventTitle;
    @FXML
    private Button deleteButton;

    // instance variables
    private final ScrollPane scrollPane;
    private final Event event;
    private final EventModel eventModel;
    private final CustomerModel customerModel;
    private AdminWindowController adminWindowController;
    private Consumer<Event> onDeleteEventCallback;

    public EventCardController(Event event, ScrollPane scrollPane, CustomerModel customerModel, EventModel eventModel, AdminWindowController adminWindowController) {
        this.event = event;
        this.scrollPane = scrollPane;
        this.customerModel = customerModel;
        this.eventModel = eventModel;
        this.adminWindowController = adminWindowController;

        try {customerModel.fetchAllCustomers(event);} catch (SQLException e) {throw new RuntimeException(e);}
    }

    public EventCardController(Event event, ScrollPane scrollPane, CustomerModel customerModel, EventModel eventModel) {
        this.event = event;
        this.scrollPane = scrollPane;
        this.customerModel = customerModel;
        this.eventModel = eventModel;

        try {customerModel.fetchAllCustomers(event);} catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void setOnDeleteEventCallback(Consumer<Event> onDeleteEventCallback) {
        this.onDeleteEventCallback = onDeleteEventCallback;
    }

    /**
     * Calculate Time Remaining method - calculates remaining time until event
     */
    private Duration calculateTimeRemaining(LocalDate eventDate) {
        double eventTimeDouble = event.getTime();
        int hours = (int) eventTimeDouble;
        int minutes = (int) ((eventTimeDouble - hours) * 60);
        LocalTime eventLocalTime = LocalTime.of(hours, minutes);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, eventLocalTime);
        return Duration.between(now, eventDateTime);
    }

    /**
     * View Event method - view more details about the event
     */
    public void viewEvent() {
        BlurEffectUtil.applyBlurEffect(scrollPane, 10);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/event_info_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            EventInfoWindowController eventInfoWindowController = fxmlLoader.getController();
            eventInfoWindowController.setModel(new EventModel(), new CustomerModel(), new TicketModel(), scrollPane);
            eventInfoWindowController.setEvent(event);
            eventInfoWindowController.setOnDeleteEventCallback(deletedEvent -> {
                if (onDeleteEventCallback != null) {
                    onDeleteEventCallback.accept(deletedEvent);
                }
            });

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle(event.getName());
            Scene scene = new Scene(createEventParent);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("ets/gui/view/styles/event_info_style.css");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete Event method - delete passed event
     */
    public void deleteEvent(){
        try {
            eventModel.deleteEvent(event);
            adminWindowController.refreshEventCards();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventTitle.setText(event.getName());

        // setting the image
        ByteArrayInputStream inputStream = new ByteArrayInputStream(event.getImageData());
        Image image = new Image(inputStream);
        eventImage.setImage(image);
        eventImage.setFitWidth(120);
        eventImage.setFitHeight(120);
        eventImage.setPreserveRatio(false);

        Duration duration = calculateTimeRemaining(event.getDate());
        if (duration.toHours() < 0) {
            timeNumber.setText("passed");
            deleteButton.setVisible(true);
        } else if (duration.toDays() >= 1) {
            timeNumber.setText(duration.toDays() + " d");
        } else {
            timeNumber.setText(duration.toHours() + " h");
        }
        participants.setText(String.valueOf(customerModel.getCustomers().size()));
    }
}
