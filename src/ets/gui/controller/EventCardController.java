package ets.gui.controller;

import ets.be.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EventCardController implements Initializable {
    @FXML
    private Label participantsNumber, timeNumber, eventTitle;
    private Event event;
    public EventCardController(Event event) {
        this.event = event;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventTitle.setText(event.getName());
        //eventLocation.setText(event.getLocation());
        long daysRemaining = calculateDaysRemaining(event.getDate());
        if (daysRemaining > -1) {
            timeNumber.setText(daysRemaining + " d");
        } else {
            timeNumber.setText("passed");
        }
    }

    private long calculateDaysRemaining(LocalDate eventDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, LocalTime.MIDNIGHT);
        Duration duration = Duration.between(now, eventDateTime);
        return duration.toDays();
    }
}
