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
        Duration duration = calculateTimeRemaining(event.getDate());
        if (duration.toHours() < 0) {
            timeNumber.setText("passed");
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
}
