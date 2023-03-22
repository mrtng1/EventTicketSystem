package ets.be;

/**
 *
 * @author tomdra01, mrtng1
 */
public class Coordinator {

    private int id;
    private String username;
    private String password;
    private int event_id;

    public Coordinator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Coordinator(int id, String username, String password) {
        this(username, password);
        this.id = id;
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

    @Override
    public String toString() {
        return "Coordinator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", event_id=" + event_id +
                '}';
    }
}
