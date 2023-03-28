package ets.dal;

// imports
import ets.be.Coordinator;
import ets.be.Event;

// java imports
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class EventDAO {

    private ConnectionManager connectionManager;

    public EventDAO() {
        connectionManager = new ConnectionManager();
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> allEvents = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Event;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    LocalDate date = resultSet.getDate("date").toLocalDate();

                    Event event = new Event(id, name, location, date);
                    allEvents.add(event);
                }
            }
        } return allEvents;
    }

    public Event createEvent(Event event) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO Event (name, date, location) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, event.getName());
            pst.setDate(2, Date.valueOf(event.getDate()));
            pst.setString(3, event.getLocation());
            pst.execute();

            if (pst.getGeneratedKeys().next()) {
                int id = pst.getGeneratedKeys().getInt(1);
                event.setId(id);
                return event;
            }
        }
        throw new RuntimeException("Id not set");
    }

    public void assignEventCoordinator(Event event, Coordinator coordinator) throws SQLException{
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO EventCoordinator(EventId, CoordinatorId) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, event.getId());
            pst.setInt(2, coordinator.getId());
            pst.executeUpdate();
        }
    }

    public void deleteEvent(Event event) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("DELETE FROM Event WHERE id = ?;");
            statement.setInt(1, event.getId());
            statement.executeUpdate();
        }
    }
}
