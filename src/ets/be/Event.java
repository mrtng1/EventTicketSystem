package ets.be;

// imports
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author tomdra01, mrtng1
 */
public class Event {

    private UUID uuid;
    private int id;
    private String name;
    private String location;
    private LocalDate date;

    public Event(UUID uuid, int id, String name, String location, LocalDate date) {
        this.uuid = uuid;
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "uuid=" + uuid +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
