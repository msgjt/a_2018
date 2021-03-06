package ro.msg.edu.jbugs.userManagement.business.dto;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Boolean isActive;
    private String phoneNumber;
    private List<RoleDTO> roles;
    private List<NotificationDTO> notifications;
    private List<BugDTO> assignedBugs;


    public UserDTO() {
        roles = new ArrayList<>();
        notifications = new ArrayList<>();
        assignedBugs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }


    public List<BugDTO> getAssignedBugs() {
        return assignedBugs;
    }

    public void setAssignedBugs(List<BugDTO> assignedBugs) {
        this.assignedBugs = assignedBugs;
    }


    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }


    @Override
    public String toString() {
        String message="USER:"+'\n'+" FIRST NAME='" + firstName + '\'' +
                ", LAST NAME='" + lastName + '\'' +
                ", USERNAME='" + username + '\'' +
                ", EMAIL='" + email + '\'' +
                ", ACTIVE STATUS=" + isActive +
                ", PHONE NUMBER='" + phoneNumber + '\'' +
                ", ROLES=" + roles ;

      return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(isActive, userDTO.isActive) &&
                Objects.equals(phoneNumber, userDTO.phoneNumber) &&
                Objects.equals(roles, userDTO.roles) &&
                Objects.equals(notifications, userDTO.notifications) &&
                Objects.equals(assignedBugs, userDTO.assignedBugs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, username, password, email, isActive, phoneNumber, roles, notifications, assignedBugs);
    }

}
