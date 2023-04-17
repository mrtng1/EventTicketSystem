package ets.bll;

import ets.be.Ticket;
import ets.dal.TicketDAO;

import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class TicketLogic {

    TicketDAO ticketDAO = new TicketDAO();

    public Ticket createTicket(Ticket ticket) throws SQLException {
        return ticketDAO.createTicket(ticket);
    }
}
