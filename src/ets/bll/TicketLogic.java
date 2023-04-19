package ets.bll;

import ets.be.Customer;
import ets.be.Event;
import ets.be.Ticket;
import ets.dal.TicketDAO;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class TicketLogic {

    TicketDAO ticketDAO = new TicketDAO();

    public List<Ticket> getAllTickets(Customer customer, Event event) throws SQLException {
        return ticketDAO.getAllTickets(customer, event);
    }

    public Ticket createTicket(Ticket ticket) throws SQLException {
        return ticketDAO.createTicket(ticket);
    }
}
