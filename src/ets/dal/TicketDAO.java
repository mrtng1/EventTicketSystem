package ets.dal;

import ets.be.Customer;
import ets.be.Event;
import ets.be.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author tomdra01, mrtng1
 */
public class TicketDAO {

    private ConnectionManager connectionManager;

    public TicketDAO() {
        connectionManager = new ConnectionManager();
    }

    public List<Ticket> getAllTickets(Customer customer) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = connectionManager.getConnection()) {
            String sql = "SELECT * FROM Ticket";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    String ticketType = rs.getString("ticketType");
                    int eventid = rs.getInt("eventid");
                    int customerid = rs.getInt("customerid");

                    //Ticket ticket = new Ticket(uuid, ticketType, eventid, customerid);
                    //tickets.add(ticket);
                }
            }
        } return tickets;
    }

    public Ticket createTicket(Ticket ticket) throws SQLException {
        try (Connection con = connectionManager.getConnection()) {
            String sqlCommandInsert = "INSERT INTO Ticket (uuid, ticketType, eventid, customerid) VALUES (?, ?, ?, ?)";
            PreparedStatement pstCreateTicket = con.prepareStatement(sqlCommandInsert);
            pstCreateTicket.setString(1, ticket.getUuid().toString());
            pstCreateTicket.setString(2, ticket.getTicketType());
            pstCreateTicket.setInt(3, ticket.getEvent().getId());
            pstCreateTicket.setInt(4, ticket.getCustomer().getId());
            pstCreateTicket.executeUpdate();
        }
        return ticket;
    }
}
