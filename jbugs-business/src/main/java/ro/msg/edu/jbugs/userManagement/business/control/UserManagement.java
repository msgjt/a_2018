package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

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


    /**
     * Checks if the user with the given username and token is present in the loggedUsers map.
     *
     * @param username not null, the search key in the loggedUsera map
     * @param token    not null, the value that must be present at the search key
     * @return true if the username @username is present in the loggedUsers map and contains the value @token
     */
    boolean checkLoggedUser(@NotNull String username, @NotNull String token);

    /**
     * Checks if the user with the given username  is present in the loggedUsers map.
     *
     * @param username not null, the search key in the loggedUsera map
     * @return true if the username @username is present in the loggedUsers map and contains the value @token
     */
    boolean checkLoggedUserByUsername(@NotNull String username);


    /**
     * Removes the user with the given username from the loggedIn map.
     *
     * @param username the key to be removed
     */
    void removeUserInLogged(String username);


    /**
     * Updates the password for a user;
     * @param id not null
     * @param password not null
     * @return the updated userDTO
     */
    UserDTO updateUserPassword(Long id, String password);


    /**
     * Logout method for a username. Will remove the user from the loggedUsers map.
     *
     * @param username the username of the user that must log out
     * @return false
     */
    boolean logout(String username);


    /**
     * Takes the username and password of a user and if they are correct, it returns the
     * corresponding DTOHelper. Otherwise it will throw an exception.
     *
     * @param username .
     * @param password .
     * @return a user DTOHelper if it succeeds.
     */
    UserDTO login(String username, String password) throws CheckedBusinessException;

    Set<String> getAllUserPermission(String username);

    List<Permission> getAllUserPermissionAsList(String username);

    User getOldUserFields(UserDTO newUserDTO);






}
