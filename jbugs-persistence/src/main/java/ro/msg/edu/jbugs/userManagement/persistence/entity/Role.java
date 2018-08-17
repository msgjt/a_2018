package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
@NamedQueries(
        {
              @NamedQuery(name = Role.GET_ALL_ROLES,query = "SELECT r FROM Role r")
        }
)
public class Role extends BaseEntity<Long> {

    @Transient
    private final static int MAX_STRING_LENGTH = 20;
    public static final String GET_ALL_ROLES = "get_all_roles";

    @Column(name = "type", length = MAX_STRING_LENGTH)
    private String type;

    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "rid"),
//            inverseJoinColumns = { @JoinColumn(name = "pid")})
    private List<Permission> permissions;

//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "rid"),
//            inverseJoinColumns = { @JoinColumn(name = "uid")})
//    private List<User> users;

    public Role() {
    }





    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(type, role.type) &&
                Objects.equals(id, role.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }


}
