package ets.gui.model;

import ets.be.Ticket;
import ets.bll.TicketLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class TicketModel {

    TicketLogic ticketLogic = new TicketLogic();

    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    public Ticket createTicket(Ticket ticket) throws SQLException {
        Ticket t = ticketLogic.createTicket(ticket);
        tickets.add(t);
        return t;
    }
}
