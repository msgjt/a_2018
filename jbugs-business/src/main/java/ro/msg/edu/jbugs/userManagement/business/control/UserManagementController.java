package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.business.utils.Encryptor;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.utils.CustomLogger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import javax.validation.constraints.NotNull;
import java.security.AllPermission;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode.*;

@Singleton
@Stateless
public class UserManagementController implements UserManagement {
    //TODO rename;
    private final static int MAX_LAST_NAME_LENGTH = 5;
    private final static int MIN_USERNAME_LENGTH = 6;
    private static final Logger logger = LogManager.getLogger(UserManagementController.class);
    private static Map<String,Integer> failedCounter= new HashMap<String,Integer>();
    private static Map<String,String> loggedUsers= new HashMap<String,String>();

    @EJB
    private UserPersistenceManager userPersistenceManager;

    /**
     * Creates a user entity using a user DTO.
     *
     * @param userDTO user information
     * @return : the user DTO of the created entity
     * @throws BusinessException
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"createUser",userDTO.toString());

        normalizeUserDTO(userDTO);
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()){
            userDTO.setRoles(new ArrayList<>());
            Role devRole = userPersistenceManager.getRoleByType("DEV");
            userDTO.getRoles().add(RoleDTOHelper.fromEntity(devRole));
        }
        validateUserForCreation(userDTO);
        User user = UserDTOHelper.toEntity(userDTO);
        user.setUsername(generateFullUsername(userDTO.getFirstName(), userDTO.getLastName()));
        user.setIsActive(true);
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistenceManager.createUser(user);
        UserDTO result = UserDTOHelper.fromEntity(user);

        CustomLogger.logExit(this.getClass(),"createUser",result.toString());
        return result;
    }


    /**
     * Validates the DTO. To use before sending it further.
     *
     * @param userDTO
     * @throws BusinessException
     */
    private void validateUserForCreation(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"validateUserForCreation",userDTO.toString());

        if (!isValidForCreation(userDTO)) {
            CustomLogger.logException(this.getClass(),"validateUserForCreation",USER_VALIDATION_EXCEPTION.toString());
            throw new BusinessException(USER_VALIDATION_EXCEPTION);
        }
        //validate if email already exists
        if (userPersistenceManager.getUserByEmail(userDTO.getEmail()).isPresent()) {
            CustomLogger.logException(this.getClass(),"validateUserForCreation",EMAIL_EXISTS_ALREADY.toString());
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }

        CustomLogger.logExit(this.getClass(),"validateUserForCreation","");

    }

    /**
     * Trims stuff (first and last name)
     *
     * @param userDTO
     */
    private void normalizeUserDTO(UserDTO userDTO) {
        CustomLogger.logEnter(this.getClass(),"normalizeUserDTO",userDTO.toString());

        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());

        CustomLogger.logExit(this.getClass(),"normalizeUserDTO",userDTO.toString());
    }

    /**
     * Creates a suffix for the username, if the username already exists. The suffix consists
     * of a number.
     * TODO : Change this. Probably won't be needed.
     *
     * @param username
     * @return
     */
    protected String createSuffix(String username) {
        CustomLogger.logEnter(this.getClass(),"createSuffix",username);

        Optional<Integer> max = userPersistenceManager.getUsernamesLike(username)
                .stream()
                .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                .map(x -> x.equals("") ? 0 : Integer.parseInt(x))
                .max(Comparator.naturalOrder())
                .map(x -> x + 1);

        String result = max.map(Object::toString).orElse("");

        CustomLogger.logExit(this.getClass(),"createSuffix",result);
        return result;
    }

    private boolean isValidForCreation(UserDTO user) {
        CustomLogger.logEnter(this.getClass(),"isValidForCreation",user.toString());

        boolean result = user.getEmail() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getPassword() != null
                && user.getPhoneNumber() != null
                && isValidEmail(user.getEmail())
                && isValidPhoneNumber(user.getPhoneNumber())
                && checkRoles(user);


        CustomLogger.logExit(this.getClass(),"isValidForCreation",String.valueOf(result));
        return result;
    }

    private boolean isValidEmail(String email) {
        CustomLogger.logEnter(this.getClass(),"isValidEmail",email);

        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        boolean result = matcher.find();

        CustomLogger.logExit(this.getClass(),"isValidEmail",String.valueOf(result));
        return result;
    }


    /**
     * Generates a username, taking the first 5 letters of the last name and the first
     * letter of the first name.
     * If the user's last name is not long enough it will try
     * to add the first name's letters to the username until it has 6 characters.
     * If the username already exists it will append a number to the username.
     * <p>
     * TODO : Change the algorithm.
     *
     * @param firstName
     * @param lastName
     * @return generated username
     */
    protected String generateUsername(@NotNull final String firstName, @NotNull final String lastName) {
        CustomLogger.logEnter(this.getClass(),"generateUsername",firstName,lastName);

        StringBuilder username = new StringBuilder();


        if (lastName.length() >= MAX_LAST_NAME_LENGTH) {
            username.append(lastName.substring(0, MAX_LAST_NAME_LENGTH) + firstName.charAt(0));

        } else if (lastName.length() + firstName.length() >= MIN_USERNAME_LENGTH) {
            username.append(lastName + firstName.substring(0, MIN_USERNAME_LENGTH - lastName.length()));
        } else {
            username.append(lastName + firstName);
            int usernameLength = username.length();
            for (int i = 0; i < MIN_USERNAME_LENGTH - usernameLength; i++) {
                username.append("0");
            }
        }

        String result = username.toString().toLowerCase();

        CustomLogger.logExit(this.getClass(),"generateUsername",result);
        return result;

    }


    @Override
    public UserDTO deactivateUser(Long id) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserById(id);
        UserDTO result;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(false);
            userPersistenceManager.updateUser(user);
            result = UserDTOHelper.fromEntity(user);

        } else {
            CustomLogger.logException(this.getClass(), "deactivateUser", USERNAME_NOT_VALID.toString());
            throw (new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        }

        CustomLogger.logExit(this.getClass(), "deactivateUser", "");
        return result;

    }

    @Override
    public UserDTO activateUser(Long id) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserById(id);
        UserDTO result;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            userPersistenceManager.updateUser(user);
            result = UserDTOHelper.fromEntity(user);
        } else {
            CustomLogger.logException(this.getClass(), "activateUser", USERNAME_NOT_VALID.toString());
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }

        CustomLogger.logExit(this.getClass(), "activateUser", "");
        return result;
    }

    /**
     * Get a list of all Users that are registered.
     *
     * @return
     */
    @Override
    public List<UserDTO> getAllUsers() {
        CustomLogger.logEnter(this.getClass(),"getAllUsers","");

        List<UserDTO> result = userPersistenceManager.getAllUsers()
                .stream()
                .map(UserDTOHelper::fromEntity)
                .collect(Collectors.toList());

        CustomLogger.logExit(this.getClass(),"getAllUsers",result.toString());
        return result;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserDTOHelper.fromEntity(userPersistenceManager.getUserById(id).get());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return UserDTOHelper.fromEntity(userPersistenceManager.getUserByUsername(username).get());
    }

    /**
     * Takes the username and password of a user and if they are correct, it returns the
     * corresponding DTO. Otherwise it will throw an exception.
     *
     * @param username
     * @param password
     * @return a user DTO if it succeeds.
     * @throws BusinessException
     */

    // Check the user credentials received fro a Http Post
    @Override
    public UserDTO login(String username, String password) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"login",username,password);

        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        //check if the username exist in the database otherwise the login method throw an Exception Code
        if (!userOptional.isPresent()) {
            CustomLogger.logException(this.getClass(),"login",USERNAME_NOT_VALID.toString());
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
        //check if the password match with the one found in the database
        if (!Encryptor.encrypt(password).equals(userOptional.get().getPassword())) {
            // if the password don#t match with the one in te database check if the user tried before to login without success
            if (!isInFailedCounter(userOptional.get().getUsername())) {
                // if thee user add the password wrong for the first time is added in a map where is stored a counter assigned to his/her username
                failedCounter.put(userOptional.get().getUsername(), 0);
            } else {
                //if the user tried before to login with a false password, the counter assigden to his/her username is increased with 1
                failedCounter.put(userOptional.get().getUsername(), failedCounter.get(userOptional.get().getUsername()) + 1);
                //if the counder is greather then 4 (that means the user tried to login with wrong credentials up to 5 times) the user is deactivated
                if (failedCounter.get(userOptional.get().getUsername()) >= 4) {
                    deactivateUser(userOptional.get().getId());
                }
            }
            CustomLogger.logException(this.getClass(),"login",PASSWORD_NOT_VALID.toString());
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        }
        //in case the user login with success the username is reoved from the map
        if (isInFailedCounter(userOptional.get().getUsername())) {
            System.out.println("Username:  " + userOptional.get().getUsername() + "tried wrong password:   " + failedCounter.get(userOptional.get().getUsername()));
            failedCounter.remove(userOptional.get().getUsername());
        }
        UserDTO result = UserDTOHelper.fromEntity(userOptional.get());

        CustomLogger.logExit(this.getClass(),"login",result.toString());
        return result;
    }

    /**
     * Updates a user with new attributes received from a Http Put.
     * It returns the corresponding DTO. Otherwise it will throw an exception.
     *
     * @param userDTO
     * @return a user DTO if it succeeds.
     * @throws BusinessException
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) throws BusinessException {
        CustomLogger.logEnter(this.getClass(), "updateUser", userDTO.toString());

        if (!isValidForUpdate(userDTO)) {
            throw new BusinessException(USER_VALIDATION_EXCEPTION);
        }

        Optional<User> oldUser = userPersistenceManager.getUserById(userDTO.getId());
        User user = new User();
        if(oldUser.isPresent()) {
            user = oldUser.get();
        }
        else{
            throw new BusinessException(ExceptionCode.USER_DOES_NOT_EXIST);
        }
        user.setEmail(userDTO.getEmail().trim());
        user.setFirstName(userDTO.getFirstName().trim());
        user.setLastName(userDTO.getLastName().trim());
        user.setPhoneNumber(userDTO.getPhoneNumber().trim());
        user.setRoles(userDTO.getRoles()
                .stream().map(RoleDTOHelper::toEntity).collect(Collectors.toList()));
        userPersistenceManager.updateUser(user);
        UserDTO result = UserDTOHelper.fromEntity(user);

        CustomLogger.logExit(this.getClass(), "updateUser", result.toString());
        return result;
    }

    private String generateFullUsername(String firstName, String lastName) {
        CustomLogger.logEnter(this.getClass(),"generateFullUsername",firstName,lastName);

        String prefix = generateUsername(firstName, lastName);
        String suffix = createSuffix(prefix);
        String result = prefix + suffix;

        CustomLogger.logExit(this.getClass(),"generateFullUsername",result);
        return result;
    }

    /**
     * Checks if the given number is a valid german or romanian phone number
     * @param phoneNumber
     * @return true if valid, false if not
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        final Pattern VALID_PHONE_ADDRESS_REGEX_GERMANY =
                Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher_ger = VALID_PHONE_ADDRESS_REGEX_GERMANY.matcher(phoneNumber);
        final Pattern VALID_PHONE_ADDRESS_REGEX_ROMANIA =
                Pattern.compile("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher_ro = VALID_PHONE_ADDRESS_REGEX_ROMANIA.matcher(phoneNumber);

        return matcher_ger.find() || matcher_ro.find();
    }

    // check if a specific user already exist in the failedCounter map
    private boolean isInFailedCounter(String username) {
        CustomLogger.logEnter(this.getClass(),"isInFailedCounter",username);

        boolean result = failedCounter.containsKey(username);

        CustomLogger.logExit(this.getClass(),"isInFailedCounter",String.valueOf(result));
        return result;
    }

    //add the username and the token in a map to have the list with the logged users.
    public void addInLoggedUsers(String username, String token){
        loggedUsers.put(username,token);
    }

    // check if an user is logged in
    public boolean checkLoggedUser(String username, String token){
       if(loggedUsers.containsKey(username)){
           if(loggedUsers.get(username).equals(token))
               return true;
           else return false;
       }
       return  false;
    }

    public void removeUserInLogged(String username){
        if (loggedUsers.containsKey(username)){
            loggedUsers.remove(username);
        }
    }

    //get all permissions assigned to an user
    public Set<String> getAllUserPermission(String username){
    Optional<User> user= userPersistenceManager.getUserByUsername(username);
    Set<Permission> allPermisssion = new HashSet<>();
    List<Permission> allPermisionsForAnUser= new ArrayList<>();
    if(user.isPresent()){
        List<Role> roles= user.get().getRoles();
        for(Role role : roles){
            List<Permission> allPermisionsInRole= new ArrayList<>();
            allPermisionsInRole= role.getPermissions();
            for (Permission p: allPermisionsInRole){
                allPermisssion.add(p);
            }
        }
    }
    Set<String> permisionString= new HashSet<>();
    allPermisionsForAnUser.addAll(allPermisssion);
    for(Permission p : allPermisionsForAnUser)
    {permisionString.add(p.getType());}
    return permisionString;
    }

    //get all permissions assigned to an user as list
    public List<Permission> getAllUserPermissionAsList(String username){
        Optional<User> user= userPersistenceManager.getUserByUsername(username);
        Set<Permission> allPermission = new HashSet<>();
        List<Permission> allPermisionsForAnUser= new ArrayList<>();
        if(user.isPresent()){
            List<Role> roles= user.get().getRoles();
            for(Role role : roles){
                List<Permission> allPermisionsInRole= new ArrayList<>();
                allPermisionsInRole= role.getPermissions();
                for (Permission p: allPermisionsInRole){
                    allPermission.add(p);
                }
            }
        }
        List<Permission> permisionsList= new ArrayList<>();
        permisionsList.addAll(allPermission);
        return permisionsList;
    }

    @Override
    public boolean checkRoles(UserDTO userDTO) {
        List<Role> recievedRoles = userDTO.getRoles()
                .stream()
                .map(RoleDTOHelper::toEntity)
                .collect(Collectors.toList());
        List<Role> possibleRoles = userPersistenceManager.getAllRoles();
        for (Role r : recievedRoles){
            if (!possibleRoles.contains(r)){
                return false;
            }
        }
        return true;
    }

    public boolean logout(String username){
        loggedUsers.remove(username);
        if(!loggedUsers.containsKey(username)){
            System.out.println("User"+ username+" is logged out "+ !loggedUsers.containsKey(username));
            return true;}
        return  false;
    }

    public boolean isValidForUpdate(UserDTO userDTO){
        return userDTO.getFirstName() != null
                && userDTO.getLastName() != null
                && userDTO.getPhoneNumber() != null
                && userDTO.getEmail() != null
                && userDTO.getRoles() != null
                && isValidEmail(userDTO.getEmail())
                && isValidPhoneNumber(userDTO.getPhoneNumber())
                && checkRoles(userDTO);
    }
}
