package ets.gui.model;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.be.Ticket;
import ets.bll.logic.TicketLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;

public class TicketModel {

    TicketLogic ticketLogic = new TicketLogic();

    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    public ObservableList<Ticket> getTickets() {
        return tickets;
    }

    public void fetchAllTickets(Customer customer, Event event) throws SQLException {
        tickets.clear();
        tickets.addAll(ticketLogic.getAllTickets(customer, event));
    }

    public Ticket createTicket(Ticket ticket) throws SQLException {
        Ticket t = ticketLogic.createTicket(ticket);
        tickets.add(t);
        return t;
    }
}
