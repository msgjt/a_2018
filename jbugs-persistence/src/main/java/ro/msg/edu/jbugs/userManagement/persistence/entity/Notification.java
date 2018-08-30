package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "notifications")
@NamedQueries(
        {
                @NamedQuery(name = Notification.GET, query =
                        "SELECT n FROM Notification n"),
                @NamedQuery(name = Notification.GET_WITH_USERS, query =
                        "SELECT u FROM Notification n JOIN FETCH n.usersNotifications usr_not JOIN FETCH usr_not.user u WHERE n.id=:id"),
                @NamedQuery(name = Notification.GET_BY_TYPE, query =
                        "SELECT n FROM Notification n WHERE n.type=:type")
        })
public class Notification extends BaseEntity<Long> {
    public static final String GET = "GET";
    public static final String GET_WITH_USERS = "GET_WITH_USERS";
    public static final String GET_BY_TYPE = "GET_BY_TYPE";

    @Column(name = "type")
    private String type;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "URL")
    private String URL;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersNotifications> usersNotifications = new ArrayList<>();


    public void addUser(User user) {
        UsersNotifications join = new UsersNotifications(user, this);
        this.usersNotifications.add(join);
    }

    public void removeUser(User user) {
        for (Iterator<UsersNotifications> iterator = this.usersNotifications.iterator(); iterator.hasNext(); ) {

            UsersNotifications join = iterator.next();

            if (join.getNotification().equals(this) && join.getUser().equals(user)) {
                iterator.remove();
                join.setUser(null);
                join.setNotification(null);
            }
        }

    }

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


    public void copyFieldsFrom(Notification n) {
        status = n.status != null ? n.status : status;
        type = n.type != null ? n.type : type;
        message = n.message != null ? n.message : message;
        URL = n.URL != null ? n.URL : URL;
    }

    public List<UsersNotifications> getUsersNotifications() {
        return usersNotifications;
    }

    public void setUsersNotifications(List<UsersNotifications> users) {
        this.usersNotifications = users;
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
                Objects.equals(usersNotifications, that.usersNotifications);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type, message, URL, status, usersNotifications);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", URL='" + URL + '\'' +
                ", status='" + status + '\'' +
                ", usersNotifications=" + usersNotifications +
                ", id=" + id +
                '}';
    }
}
