package ets.gui.model;

// imports
import ets.be.Coordinator;
import ets.be.Event;
import ets.bll.EventLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventModel {

    EventLogic eventLogic = new EventLogic();

    private ObservableList<Event> events = FXCollections.observableArrayList();
    private ObservableList<Coordinator> coordinators = FXCollections.observableArrayList();

    public Event createEvent(Event event) throws SQLException {
        Event e = eventLogic.createEvent(event);
        events.add(e);
        return e;
    }

    public void assignEventCoordinator(Event event, Coordinator coordinator) throws SQLException{
        eventLogic.assignEventCoordinator(event, coordinator);
        coordinators.add(coordinator);
    }

    public void deleteEvent(Event event) throws SQLException {
        eventLogic.deleteEvent(event);
    }
}