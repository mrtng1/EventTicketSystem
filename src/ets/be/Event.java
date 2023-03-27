package ets.be;

// imports
import java.time.LocalDate;

/**
 *
 * @author tomdra01, mrtng1
 */
public class Event {

    private int id;
    private String name;
    private String location;
    private LocalDate date;
    private String message;
    private byte[] image;

    public Event(String name, String location, LocalDate date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public Event(int id, String name, String location, LocalDate date){
        this(name, location, date);
        this.id = id;
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
                ", id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
