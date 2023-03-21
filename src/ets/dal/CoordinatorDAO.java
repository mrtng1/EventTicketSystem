package ets.dal;

//imports
import ets.be.Coordinator;

// java imports
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorDAO {

    private ConnectionManager connectionManager;

    public CoordinatorDAO() {
        connectionManager = new ConnectionManager();
    }

    public List<Coordinator> getAllCoordinators() throws SQLException {
        List<Coordinator> allCoordinators = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Coordinator;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    Coordinator coordinator = new Coordinator(id, username, password);
                    allCoordinators.add(coordinator);
                }
            }
        } return allCoordinators;
    }

    public Coordinator createCoordinator(String username, String password) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            String psql = "INSERT INTO Coordinator (username, password) OUTPUT INSERTED.id VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(psql);
            statement.setString(1, username);
            statement.setString(2, password);

            int id;
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                return new Coordinator(id, username, password);
            } else {
                throw new SQLException("Failed to retrieve the generated ID.");
            }
        }
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement("DELETE FROM Coordinator WHERE id = ?;");
            statement.setInt(1, coordinator.getId());
            statement.executeUpdate();
        }
    }
}
