package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersNotificationsId implements Serializable {

    @Column(name="user_id")
    private Long userId;

    @Column(name="notification_id")
    private Long notificationId;

    public UsersNotificationsId() {

    }

    public UsersNotificationsId(Long userId, Long notificationId){
        this.userId = userId;
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersNotificationsId that = (UsersNotificationsId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, notificationId);
    }
}
