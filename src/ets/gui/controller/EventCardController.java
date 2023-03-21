package ets.gui.controller;

import ets.be.Event;
import ets.gui.model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EventCardController implements Initializable {
    @FXML
    private Label participantsNumber, timeNumber, eventTitle;
    @FXML
    private Button deleteButton;
    private Event event;
    private EventModel eventModel;
    private MainWindowController mainWindowController;
    public EventCardController(Event event, EventModel eventModel) {
        this.event = event;
        this.eventModel = eventModel;
    }

    public EventCardController(Event event) {
        this.event = event;
    }

    public EventCardController(Event event, EventModel eventModel, MainWindowController mainWindowController) {
        this.event = event;
        this.eventModel = eventModel;
        this.mainWindowController = mainWindowController;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventTitle.setText(event.getName());
        //eventLocation.setText(event.getLocation());
        Duration duration = calculateTimeRemaining(event.getDate());
        if (duration.toHours() < 0) {
            timeNumber.setText("passed");
            deleteButton.setVisible(true);
        } else if (duration.toDays() >= 1) {
            timeNumber.setText(duration.toDays() + " d");
        } else {
            timeNumber.setText(duration.toHours() + " h");
        }
    }

    private Duration calculateTimeRemaining(LocalDate eventDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, LocalTime.MIDNIGHT);
        return Duration.between(now, eventDateTime);
    }

    @FXML
    public void eventViewBtn(ActionEvent actionEvent) {
        System.out.println("view");
    }

    @FXML
    public void deleteBtn(ActionEvent actionEvent){
        System.out.println("delete");
        try {
            eventModel.deleteEvent(event);
            mainWindowController.refreshEventCards();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
