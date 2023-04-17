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

    public Ticket(String ticketType, Event event, Customer customer) {
        this.uuid = UUID.randomUUID();
        this.ticketType = ticketType;
        this.event = event;
        this.customer = customer;
    }

    public Ticket(UUID uuid, String ticketType, Event event, Customer customer) {
        this.uuid = uuid;
        this.ticketType = ticketType;
        this.event = event;
        this.customer = customer;
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
