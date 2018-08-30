package ro.msg.edu.jbugs.bugsManagement.persistence.entity;

import ro.msg.edu.jbugs.userManagement.persistence.entity.BaseEntity;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bugs")
@NamedQueries(
        {
                @NamedQuery(name = Bug.GET_ALL_BUGS, query = "SELECT b FROM Bug b"),
                @NamedQuery(name = Bug.GET_BUG_BY_TITLE, query = "SELECT b FROM Bug b WHERE b.title=:title"),
                @NamedQuery(name = Bug.GET_BUG_BY_ID, query = "SELECT DISTINCT b FROM Bug b WHERE b.id=:id")

        }
)
@Converter(autoApply = true)
public class Bug extends BaseEntity<Long> {

    public static final String GET_ALL_BUGS = "get_All_Bugs";
    public static final String GET_BUG_BY_TITLE = "get_Bug_By_Title";
    public static final String GET_BUG_BY_ID = "get_Bug_By_Id";
    @Transient
    private final static int MAX_STRING_LENGTH = 127;
    @Column(name = "title", length = MAX_STRING_LENGTH, nullable = false)
    private String title;
    @Column(name = "description", length = 2000, nullable = false)
    private String description;
    @Column(name = "version", length = MAX_STRING_LENGTH, nullable = false)
    private String version;

    @Column(name = "targetDate", length = MAX_STRING_LENGTH, nullable = false)
    private LocalDate targetDate;

    @Column(name = "status", length = MAX_STRING_LENGTH, nullable = false)
    private String status;

    @Column(name = "fixedVersion", length = MAX_STRING_LENGTH, nullable = false)
    private String fixedVersion;

    @Column(name = "severity", length = MAX_STRING_LENGTH, nullable = false)
    private Severity severity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "bugs_creators",
            joinColumns = @JoinColumn(
                    name = "bug_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id")
    )
    private User createdBy;

    @JoinColumn(name = "assignedTo", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedTo;


    @Column(name = "attachment", length = MAX_STRING_LENGTH, nullable = false)
    private String attachment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bug", orphanRemoval = true)
    private List<History> history = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity string) {
        this.severity = string;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate=" + targetDate +
                ", status='" + status + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity=" + severity +
                ", createdBy=" + createdBy +
                ", assignedTo=" + assignedTo +
                ", attachment='" + attachment + '\'' +
                ", history=" + history +
                ", id=" + id +
                '}';
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }
}
