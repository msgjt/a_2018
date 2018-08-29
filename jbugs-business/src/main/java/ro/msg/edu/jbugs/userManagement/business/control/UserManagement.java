package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface UserManagement {

    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO deactivateUser(Long id);
    UserDTO activateUser(Long id);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO login(String username, String password) throws CheckedBusinessException;
    void addInLoggedUsers(@NotNull String username,@NotNull String token);
    boolean checkLoggedUser(@NotNull String username,@NotNull String token);
    void removeUserInLogged(String username);
    Set<String> getAllUserPermission(String username);
    List<Permission> getAllUserPermissionAsList(String username);
    boolean logout(String username);
    boolean checkLoggedUserByUsername(@NotNull String username);

}
