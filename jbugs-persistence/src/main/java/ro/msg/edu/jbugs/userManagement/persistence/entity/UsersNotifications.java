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

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public UsersNotifications() {}

    public UsersNotifications(User user, Notification notification){
        this.user = user;
        this.notification = notification;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersNotifications that = (UsersNotifications) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(notification, that.notification) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, user, notification, date);
    }
}