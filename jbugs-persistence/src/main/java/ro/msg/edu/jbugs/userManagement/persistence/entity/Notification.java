package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "notifications")
@NamedQueries(
        {
                @NamedQuery(name = Notification.GET_ALL_NOTIFICATIONS,query = "SELECT n FROM Notification n")
        })
public class Notification extends BaseEntity<Long>{
    public static final String GET_ALL_NOTIFICATIONS = "get_all_notifications";

    @Column(name = "type")
    private String type;

    @Column(name = "message")
    private String message;

    @Column(name = "URL")
    private String URL;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersNotifications> users = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


    public List<UsersNotifications> getUsers() {
        return users;
    }

    public void setUsers(List<UsersNotifications> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Notification that = (Notification) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(message, that.message) &&
                Objects.equals(URL, that.URL) &&
                Objects.equals(status, that.status) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type, message, URL, status, users);
    }
}
