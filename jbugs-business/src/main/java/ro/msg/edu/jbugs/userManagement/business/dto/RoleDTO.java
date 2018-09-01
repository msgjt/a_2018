package ro.msg.edu.jbugs.userManagement.business.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDTO {

    private Long id;
    private String type;
    private List<PermissionDTO> permissions;

    public RoleDTO() {
        permissions = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String message="";
        if(type.equals("ADM"))
            message=message.concat(" ADMIN ");
        if(type.equals("DEV"))
            message=message.concat(" DEVELOPER ");
        if(type.equals("TM"))
            message=message.concat(" TEST MANAGER ");
        if(type.equals("TESTER"))
            message=message.concat(" TESTER ");
        if(type.equals("PM"))
            message=message.concat(" PERMISSION MANAGER ");
        return message + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(id, roleDTO.id) &&
                Objects.equals(type, roleDTO.type) &&
                Objects.equals(permissions, roleDTO.permissions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, permissions);
    }
}
