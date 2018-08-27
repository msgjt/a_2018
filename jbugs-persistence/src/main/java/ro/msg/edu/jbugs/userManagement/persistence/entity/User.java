package ro.msg.edu.jbugs.userManagement.persistence.entity;


import javax.persistence.*;
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
                @NamedQuery(name= User.GET_USER_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id")

        }
)
public class User extends BaseEntity<Long> {

    @Transient
    private final static int MAX_STRING_LENGTH = 127;
    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String GET_USER_BY_USERNAME = "getUserByUsername";
    public static final String GET_USER_BY_EMAIL = "getUserByEmail";
    public static final String GET_USER_BY_ID = "getUserById";

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

    @ManyToMany
    private List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    public User copy(User user){
        this.id = user.getId() != null ? user.getId() : this.id;
        this.firstName = user.getFirstName() != null ? user.getFirstName() : this.firstName;
        this.lastName = user.getLastName() != null ? user.getLastName() : this.lastName;
        this.username = user.getUsername() != null ? user.getUsername() : this.username;
        this.password = user.getPassword() != null ? user.getPassword() : this.password;
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : this.phoneNumber;
        this.email = user.getEmail() != null ? user.getEmail() : this.email;
        this.isActive = user.getIsActive() != null ? user.getIsActive() : this.isActive;
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

}
