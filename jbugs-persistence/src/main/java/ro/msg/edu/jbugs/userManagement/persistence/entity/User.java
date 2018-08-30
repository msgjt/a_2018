package ro.msg.edu.jbugs.userManagement.persistence.entity;


import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
                @NamedQuery(name = User.GET_USER_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username=:username"),
                @NamedQuery(name = User.GET_USER_BY_EMAIL, query = "SELECT u from User u where u.email = :email "),
                @NamedQuery(name = User.GET_USER_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
                @NamedQuery(name = User.GET_NOTIFICATIONS_FOR_USER, query =
                        "SELECT n FROM User u JOIN FETCH u.usersNotifications ntfs JOIN FETCH ntfs.notification n WHERE u.id=:id")
        }

)
public class User extends BaseEntity<Long> {

    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String GET_USER_BY_USERNAME = "getUserByUsername";
    public static final String GET_USER_BY_EMAIL = "getUserByEmail";
    public static final String GET_USER_BY_ID = "getUserById";
    public static final String GET_NOTIFICATIONS_FOR_USER = "GET_NOTIFICATIONS_FOR_USER";
    @Transient
    private final static int MAX_STRING_LENGTH = 127;
    @Column(name = "firstName", length = MAX_STRING_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = MAX_STRING_LENGTH, nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", length = MAX_STRING_LENGTH, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String email;

    @Column(name = "username", length = MAX_STRING_LENGTH, nullable = false, unique = true, updatable = false)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersNotifications> usersNotifications = new ArrayList<>();

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

    public List<UsersNotifications> getUsersNotifications() {
        return usersNotifications;
    }

    public void setUsersNotifications(List<UsersNotifications> notifications) {
        this.usersNotifications = notifications;
    }

    public void copyFieldsFrom(User u) {
        firstName = u.firstName != null ? u.firstName : firstName;
        lastName = u.lastName != null ? u.lastName : lastName;
        username = u.username != null ? u.username : username;
        password = u.password != null ? u.password : password;
        roles = u.roles != null ? u.roles : roles;
        email = u.email != null ? u.email : email;
        usersNotifications = u.usersNotifications != null ? u.usersNotifications : usersNotifications;
        phoneNumber = u.phoneNumber != null ? u.phoneNumber : phoneNumber;
        isActive = u.isActive != null ? u.isActive : isActive;
        assignedBugs = u.assignedBugs != null ? u.assignedBugs : assignedBugs;
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
                Objects.equals(isActive, user.isActive) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(assignedBugs, user.assignedBugs) &&
                Objects.equals(usersNotifications, user.usersNotifications);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, phoneNumber, email, username, password, isActive, roles, assignedBugs, usersNotifications);
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


    public void addNotification(Notification notification) {
        UsersNotifications join = new UsersNotifications(this, notification);
        this.usersNotifications.add(join);
    }

    public void removeNotification(Notification notification) {
        for (Iterator<UsersNotifications> iterator = this.usersNotifications.iterator(); iterator.hasNext(); ) {

            UsersNotifications join = iterator.next();

            if (join.getUser().equals(this) && join.getNotification().equals(notification)) {
                iterator.remove();
                join.setNotification(null);
                join.setUser(null);
            }
        }

    }


}
