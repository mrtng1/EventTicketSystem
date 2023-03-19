package ets.be;

public class Coordinator {
    private int id;
    private String username;
    private String password;
    private int event_id;

    public Coordinator(int id, String username, String password, int event_id){
        this.id=id;
        this.username=username;
        this.password=password;
        this.event_id=event_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
