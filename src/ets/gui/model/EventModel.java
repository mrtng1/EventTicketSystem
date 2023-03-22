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

    public Event createEvent(Event event) throws SQLException {
        Event e = eventLogic.createEvent(event);
        events.add(e);
        return e;
    }

    public void deleteEvent(Event event) throws SQLException {
        eventLogic.deleteEvent(event);
    }
}