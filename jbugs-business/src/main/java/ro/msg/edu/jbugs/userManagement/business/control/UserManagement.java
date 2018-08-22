package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.List;
import java.util.Set;

public interface UserManagement {

    /**
     * Method is used for persisting an user from an userDTO.
     * It generates the username and does the validations..
     *
     * @param userDTO user information
     * @return the newly created entity as a userDTO
     */
    UserDTO createUser(UserDTO userDTO) throws BusinessException;

    /**
     * Deactivates a user, restricting the access of said user to the app.
     *
     * @param username
     */
    void deactivateUser(String username) throws BusinessException;

    /**
     * Activates a deactivated user.
     *
     * @param username
     */
    void activateUser(String username) throws BusinessException;


    /**
     * @return a list of DTOs containing information about users.
     */
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);


    /**
     * Tries to log in a user.
     *
     * @param username
     * @param password
     * @return UserDTO of said user
     * @throws BusinessException in case the user is not found or the password is wrong.
     */
    UserDTO login(String username, String password) throws BusinessException;

    void addInLoggedUsers(String username, String token);
    boolean checkLoggedUser(String username, String token);
    void removeUserInLogged(String username);
    Set<String> getAllUserPermission(String username);
    UserDTO updateUser(UserDTO userDTO) throws BusinessException;

    boolean checkRoles(UserDTO userDTO);

}
