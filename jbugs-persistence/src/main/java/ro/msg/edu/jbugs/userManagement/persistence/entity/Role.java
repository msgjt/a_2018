package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
@NamedQueries(
        {
                @NamedQuery(name = Role.GET_ALL_ROLES, query = "SELECT r FROM Role r")
        }
)
public class Role extends BaseEntity<Long> {

    public static final String GET_ALL_ROLES = "get_all_roles";
    @Transient
    private final static int MAX_STRING_LENGTH = 127;
    @Column(name = "type", length = MAX_STRING_LENGTH, updatable = false)
    private String type1;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Permission> permissions = new ArrayList<>();


    public String getType1() {
        return type1;
    }

    public void setType1(String type) {
        this.type1 = type;
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
        return Objects.equals(type1, role.type1) &&
                Objects.equals(id, role.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type1);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type1 + '\'' +
                '}';
    }

}
