package ets.bll;

// imports
import ets.be.Coordinator;
import ets.be.Event;
import ets.dal.EventDAO;
import ets.dal.IDataAccess;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventLogic {

    EventDAO eventDAO = new EventDAO();

    public List<Event> getAllEvents() throws SQLException{
        return eventDAO.getAllEvents();
    }

    public Event createEvent(Event event) throws SQLException {
        return eventDAO.createEvent(event);
    }

    public void assignEventCoordinator(Event event, Coordinator coordinator) throws SQLException{
        eventDAO.assignEventCoordinator(event, coordinator);
    }

    public void deleteEvent(Event event) throws SQLException{
        eventDAO.deleteEvent(event);
    }
}
