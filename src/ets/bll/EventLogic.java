package ets.bll;

import ets.be.Event;
import ets.dal.EventDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventLogic {
    EventDAO eventDAO = new EventDAO();

    public Event createEvent(String name, String location, LocalDate date) throws SQLException {
        return eventDAO.createEvent(name, location, date);
    }


    public List<Event> getAllEvents() throws SQLException{
        return eventDAO.getAllEvents();
    }

    public void deleteEvent(Event event) throws SQLException{
        eventDAO.deleteEvent(event);
    }
}
