package ro.msg.edu.jbugs.userManagement.business.dto;

import java.util.List;

public class RoleDTO {

    private long id;
    private String type;
    private List<PermissionDTO> permissions;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "type='" + type + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
