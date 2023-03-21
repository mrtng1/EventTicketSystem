package ets.dal;

import ets.be.Event;

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

    public Event createEvent(String name, String location, LocalDate date) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            String psql = "INSERT INTO Event (name, location, date) OUTPUT INSERTED.id VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(psql);
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setDate(3, Date.valueOf(date));

            int id;
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                return new Event(id, name, location, date);
            } else {
                throw new SQLException("Failed to retrieve the generated ID.");
            }
        }
    }

    public void deleteEvent(Event event) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement pstMovie = con.prepareStatement("DELETE FROM Event WHERE id = ?;");
            pstMovie.setInt(1, event.getId());
            pstMovie.executeUpdate();
        }
    }



}
