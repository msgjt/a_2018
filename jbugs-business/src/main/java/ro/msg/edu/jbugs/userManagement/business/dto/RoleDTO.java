package ro.msg.edu.jbugs.userManagement.business.dto;

import java.util.List;

public class RoleDTO {

    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
