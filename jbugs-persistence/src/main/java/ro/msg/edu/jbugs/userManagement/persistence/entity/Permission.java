package ro.msg.edu.jbugs.userManagement.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity<Long> {

    @Transient
    private final static int MAX_STRING_LENGTH = 127;

    @Column(name = "type", nullable = false, length = MAX_STRING_LENGTH, unique = true, updatable = false)
    private String type2;

    @Column(name = "description", updatable = false)
    private String description;

    public String getType2() {
        return type2;
    }

    public void setType2(String type) {
        this.type2 = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Permission that = (Permission) o;
        return Objects.equals(type2, that.type2) &&
                Objects.equals(description, that.description) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type2, description);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", type='" + type2 + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
