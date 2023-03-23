package ets.dal;

import ets.be.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private ConnectionManager connectionManager;

    public AdminDAO() {
        connectionManager = new ConnectionManager();
    }

    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> allAdmins = new ArrayList<>();
        try (Connection con = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Admin;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    Admin admin = new Admin(id, username, password);
                    allAdmins.add(admin);
                }
            }
        }
        return allAdmins;
    }
}