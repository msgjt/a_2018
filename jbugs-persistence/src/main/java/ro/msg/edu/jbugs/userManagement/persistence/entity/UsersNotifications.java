package ro.msg.edu.jbugs.userManagement.persistence.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "UsersNotifications")
@Table(name = "users_notifications")
public class UsersNotifications implements Serializable {

    @EmbeddedId
    private UsersNotificationsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("notificationId")
    private Notification notification;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "received", nullable = false)
    private Boolean received;

    public UsersNotifications() {}

    public UsersNotifications(User user, Notification notification){
        this.user = user;
        this.notification = notification;
        this.received = false;
        this.date = LocalDate.now();
        this.id = new UsersNotificationsId(user.getId(),notification.getId());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getReceived() { return received; }

    public void setReceived(Boolean received) { this.received = received; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersNotifications that = (UsersNotifications) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(notification, that.notification) &&
                Objects.equals(date, that.date) &&
                Objects.equals(received, that.received);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, notification, date, received);
    }

    @Override
    public String toString() {
        return "UsersNotifications{" +
                "id=" + id +
                ", user=" + user +
                ", notification=" + notification +
                ", date=" + date +
                ", received=" + received +
                '}';
    }
}