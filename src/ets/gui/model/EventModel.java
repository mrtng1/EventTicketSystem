package ets.gui.model;

// imports
import ets.be.Event;
import ets.bll.EventLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventModel {

    EventLogic eventLogic = new EventLogic();

    private ObservableList<Event> events = FXCollections.observableArrayList();

    public Event createEvent(String name, String location, LocalDate date) throws SQLException {
        Event event = eventLogic.createEvent(name, location, date);
        events.add(event);
        return event;
    }

    public void deletePerson(Event event) throws SQLException {
        eventLogic.deleteEvent(event);
    }
}