package ro.msg.edu.jbugs.bugsManagement.persistence.entity;

import ro.msg.edu.jbugs.userManagement.persistence.entity.BaseEntity;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity<Long> {

    @JoinColumn(name = "userId", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @JoinColumn(name = "bugId", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Bug bug;
    @Column(name = "text", length = 400, nullable = false)
    private String text;
    @Column(name = "date", nullable = false)
    private Date date;

    public Comment() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                ", id=" + id +
                "user=" + user +
                ", bug=" + bug +
                ", text='" + text + '\'' +
                ", date=" + date +

                '}';
    }
}
