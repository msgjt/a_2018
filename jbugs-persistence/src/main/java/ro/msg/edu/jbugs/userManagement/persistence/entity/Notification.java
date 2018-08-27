package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.*;
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

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @Column(name = "message")
    private String message;

    @Column(name = "URL")
    private String URL;


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


}
