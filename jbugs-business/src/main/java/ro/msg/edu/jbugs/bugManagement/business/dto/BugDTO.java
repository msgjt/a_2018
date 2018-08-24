package ro.msg.edu.jbugs.bugManagement.business.dto;

import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

public class BugDTO {

    private Long id;
    private String title;
    private String description;
    private String version;
    private String targetDate;
    private String status;
    private String fixedVersion;
    private Severity severity;
    private User createdBy;
    private User assignedTo;

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

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
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

    public void setSeverity(Severity severity) {
        this.severity = severity;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BugDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", status='" + status + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity=" + severity +
                ", createdBy=" + createdBy +
                ", assignedTo=" + assignedTo +
                '}';
    }
}
