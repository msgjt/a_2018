package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface UserManagement {


    /**
     * Returns the list of all the users from the persistence layer.
     *
     * @return the list of all the userDTOs present (enabled or disabled).
     */
    List<UserDTO> getAllUsers();


    /**
     * Creates a new user from a userDTO. Will call a validation method for the parameter and will set the other
     * fields accordingly (isActive = true, username = generated, password = encrypted, roles = default if none)
     *
     * @param userDTO contains the required user information in its fields, will be validated
     * @return : the persisted userDTO
     */
    UserDTO createUser(UserDTO userDTO);


    /**
     * Updates a user from a userDTO. Will call a validation method for the parameter and will set the other
     * fields accordingly (by calling the copy method from the user class)
     *
     * @param userDTO contains the required user information in its fields, will be validated
     * @return : the persisted userDTO
     */
    UserDTO updateUser(UserDTO userDTO);


    /**
     * Activates a user (sets isActive to true)
     *
     * @param id will be validated for null values, or not present
     * @return the persisted userDTO
     */
    UserDTO activateUser(Long id);


    /**
     * Deactivates a user (sets isActive to false)
     *
     * @param id will be validated for null values, or not present
     * @return the persisted userDTO
     */
    UserDTO deactivateUser(Long id);


    /**
     * Returns a userDTO from the user with the given id.
     *
     * @param id the id of the desired user, will be validated for null values
     * @return the required userDTO
     */
    UserDTO getUserById(Long id);


    /**
     * @param username the username of the desired user, will be validated for null values
     * @return the required userDTO
     */
    UserDTO getUserByUsername(String username);


    /**
     * Adds the value "token" to the key "username" in the logged users map.
     *
     * @param username not null
     * @param token    the session token, not null
     */
    void addInLoggedUsers(@NotNull String username, @NotNull String token);

    UserDTO login(String username, String password) throws CheckedBusinessException;




    boolean checkLoggedUser(@NotNull String username, @NotNull String token);

    void removeUserInLogged(String username);

    UserDTO updateUserPassword(Long id, String password);

    Set<String> getAllUserPermission(String username);

    List<Permission> getAllUserPermissionAsList(String username);

    boolean logout(String username);

    boolean checkLoggedUserByUsername(@NotNull String username);


}
