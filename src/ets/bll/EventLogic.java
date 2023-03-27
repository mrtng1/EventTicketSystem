package ets.bll;

// imports
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

    public Event createEvent(Event event) throws SQLException {
        return eventDAO.createEvent(event);
    }

    public List<Event> getAllEvents() throws SQLException{
        return eventDAO.getAllEvents();
    }

    public void deleteEvent(Event event) throws SQLException{
        eventDAO.deleteEvent(event);
    }
}
