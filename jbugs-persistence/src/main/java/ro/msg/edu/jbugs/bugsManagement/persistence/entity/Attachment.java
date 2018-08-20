package ro.msg.edu.jbugs.bugsManagement.persistence.entity;

import ro.msg.edu.jbugs.userManagement.persistence.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "attachments")
public class Attachment extends BaseEntity<Long> {

    @Column(name = "content", length = 400, nullable = false)
    private String attContent;
    @JoinColumn(name = "bugId", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Bug bug;

    public Attachment() {
    }

    public String getAttContent() {
        return attContent;
    }

    public void setAttContent(String attContent) {
        this.attContent = attContent;
    }

    public Bug getBug() {
        return bug;
    }

    public void setBug(Bug bug) {
        this.bug = bug;
    }


    @Override
    public String toString() {
        return "Attachment{" +
                ", id=" + id +
                "attContent='" + attContent + '\'' +
                ", bug=" + bug +
                '}';
    }
}
