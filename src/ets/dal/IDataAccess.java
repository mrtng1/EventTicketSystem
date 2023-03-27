package ets.dal;

//imports
import ets.be.Admin;
import ets.be.Coordinator;
import ets.be.Event;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public interface IDataAccess {

    List<Admin> getAllAdmins() throws SQLException;

    List<Coordinator> getAllCoordinators() throws SQLException;

    Coordinator createCoordinator(Coordinator coordinator) throws SQLException;

    void deleteCoordinator(Coordinator coordinator) throws SQLException;

    List<Event> getAllEvents() throws SQLException;

    Event createEvent(Event event) throws SQLException;

    void deleteEvent(Event event) throws SQLException;
}
