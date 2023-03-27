package ets.bll;

// imports
import ets.be.Event;
import ets.dal.IDataAccess;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventLogic {

    IDataAccess dataAccess;

    public Event createEvent(Event event) throws SQLException {
        return dataAccess.createEvent(event);
    }

    public List<Event> getAllEvents() throws SQLException{
        return dataAccess.getAllEvents();
    }

    public void deleteEvent(Event event) throws SQLException{
        dataAccess.deleteEvent(event);
    }
}
