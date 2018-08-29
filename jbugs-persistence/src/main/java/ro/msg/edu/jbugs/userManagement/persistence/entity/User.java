package ro.msg.edu.jbugs.userManagement.persistence.entity;


import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
                @NamedQuery(name = User.GET_USER_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username=:username"),
                @NamedQuery(name= User.GET_USER_BY_EMAIL, query = "SELECT u from User u where u.email = :email "),
                @NamedQuery(name= User.GET_USER_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
                @NamedQuery(name = User.GET_NOTIFICATIONS_BY_USER_AND_STATUS, query = "SELECT n FROM User u, Notification n WHERE n MEMBER OF u.notifications AND n.status=:status AND u.id =:userId")
        }
)
public class User extends BaseEntity<Long> {

    @Transient
    private final static int MAX_STRING_LENGTH = 127;
    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String GET_USER_BY_USERNAME = "getUserByUsername";
    public static final String GET_USER_BY_EMAIL = "getUserByEmail";
    public static final String GET_USER_BY_ID = "getUserById";
    public static final String GET_NOTIFICATIONS_BY_USER_AND_STATUS = "GET_NOTIFICATIONS_BY_USER_AND_STATUS";

    @Column(name = "firstName", length = MAX_STRING_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = MAX_STRING_LENGTH, nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", length = MAX_STRING_LENGTH, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String email;

    @Column(name = "username", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = MAX_STRING_LENGTH, nullable = false)
    private String password;

    @Column(name = "isActive", length = MAX_STRING_LENGTH, nullable = false)
    private Boolean isActive;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignedTo", orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_id")
    private List<Bug> assignedBugs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private List<Notification> notifications = new ArrayList<>();

    @Transactional
    public User copyFieldsFrom(User user) {

        id = id != null ? id : user.id;
        firstName = firstName != null ? firstName : user.firstName;
        lastName = lastName != null ? lastName : user.lastName;
        username = username != null ? username : user.username;
        password = password != null ? password : user.password;
        phoneNumber = phoneNumber != null ? phoneNumber : user.phoneNumber;
        email = email != null ? email : user.email;
        isActive = isActive != null ? isActive : user.isActive;
        roles = roles != null ? roles : user.roles;
        notifications = notifications != null ? notifications : user.notifications;

        return this;
    }

    public User copy(User user) {
        this.id = user.getId() != null ? user.getId() : this.id;
        this.firstName = user.getFirstName() != null ? user.getFirstName() : this.firstName;
        this.lastName = user.getLastName() != null ? user.getLastName() : this.lastName;
        this.username = user.getUsername() != null ? user.getUsername() : this.username;
        this.password = user.getPassword() != null ? user.getPassword() : this.password;
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : this.phoneNumber;
        this.email = user.getEmail() != null ? user.getEmail() : this.email;
        this.isActive = user.getIsActive() != null ? user.getIsActive() : this.isActive;
        this.roles = user.getRoles() != null ? user.getRoles() : this.roles;
        this.assignedBugs = user.getAssignedBugs() != null ? user.getAssignedBugs() : this.assignedBugs;
        this.notifications = user.getNotifications() != null ? user.getNotifications() : this.notifications;
        return this;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean status) {
        this.isActive = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, phoneNumber, email, username, password, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status='" + isActive + '\'' +
                '}';
    }


    public List<Bug> getAssignedBugs() {
        return assignedBugs;
    }

    public void setAssignedBugs(List<Bug> assignedBugs) {
        this.assignedBugs = assignedBugs;
    }
}
