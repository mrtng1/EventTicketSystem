package ets.be;

// imports
import java.util.UUID;

/**
 *
 * @author tomdra01, mrtng1
 */
public class Ticket {

    private UUID uuid;
    private Event event;
    private Customer customer;
    private String ticketType;

    public Ticket(UUID uuid, Event event, Customer customer, String ticketType) {
        this.uuid = uuid;
        this.event = event;
        this.customer = customer;
        this.ticketType = ticketType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "uuid=" + uuid +
                ", event=" + event +
                ", customer=" + customer +
                ", ticketType='" + ticketType + '\'' +
                '}';
    }
}
