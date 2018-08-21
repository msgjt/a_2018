package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.business.utils.Encryptor;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.utils.CustomLogger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode.*;

@Stateless
public class UserManagementController implements UserManagement {
    //TODO rename;
    private final static int MAX_LAST_NAME_LENGTH = 5;
    private final static int MIN_USERNAME_LENGTH = 6;
    private static Map<String, Integer> failedCounter = new HashMap<String, Integer>();

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
                && isValidEmail(user.getEmail());

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

    /**
     * Deactivates a user, removing them the ability to login, but keeping their bugs, comments, etc.
     *
     * @param username
     */
    @Override
    public void deactivateUser(String username) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"deactivateUser",username);

        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userPersistenceManager.getUserByUsername(username).get();
            user.setIsActive(false);
            userPersistenceManager.updateUser(user);
        } else {
            CustomLogger.logException(this.getClass(),"deactivateUser",USERNAME_NOT_VALID.toString());
            throw (new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        }

        CustomLogger.logExit(this.getClass(),"deactivateUser","");
    }

    /**
     * Activates a user, granting them the ability to login. asdf
     *
     * @param username
     */
    @Override
    public void activateUser(String username) throws BusinessException {
        CustomLogger.logEnter(this.getClass(),"activateUser",username);

        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            userPersistenceManager.updateUser(user);
        } else {
            CustomLogger.logException(this.getClass(),"activateUser",USERNAME_NOT_VALID.toString());
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }

        CustomLogger.logExit(this.getClass(),"activateUser","");
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
                    deactivateUser(userOptional.get().getUsername());
                }
            }
            CustomLogger.logException(this.getClass(),"login",PASSWORD_NOT_VALID.toString());
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        }
        //in case the user login with success the username is reoved from the map
        if (isInFailedCounter(userOptional.get().getUsername())) {
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
        CustomLogger.logEnter(this.getClass(),"updateUser",userDTO.toString());

        Optional<User> oldUser = userPersistenceManager.getUserById(userDTO.getId());
        if (!isValidForCreation(userDTO)) {
            CustomLogger.logException(this.getClass(),"updateUser",USER_VALIDATION_EXCEPTION.toString());
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
        User user=oldUser.get();

        user.setEmail(userDTO.getEmail().trim());
        user.setFirstName(userDTO.getFirstName().trim());
        user.setLastName(userDTO.getLastName().trim());
        user.setPassword(userDTO.getPassword().trim());
        user.setPhoneNumber(userDTO.getPhoneNumber().trim());

        UserDTO result = UserDTOHelper.fromEntity(user);

        CustomLogger.logExit(this.getClass(),"updateUser",result.toString());
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

    private boolean isValidPhoneNumber(String phonenumber) {
        CustomLogger.logEnter(this.getClass(),"isValidPhoneNumber",phonenumber);

        //TODO Nu merge
        final Pattern VALID_PHONE_ADDRESS_REGEX =
                Pattern.compile("(^\\+49)|(^01[5-7][1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(phonenumber);
        boolean result = matcher.find();
        CustomLogger.logExit(this.getClass(),"isValidPhoneNumber",String.valueOf(result));
        return result;
    }

    // check if a specific user already exist in the failedCounter map
    private boolean isInFailedCounter(String username) {
        CustomLogger.logEnter(this.getClass(),"isInFailedCounter",username);

        boolean result = failedCounter.containsKey(username);

        CustomLogger.logExit(this.getClass(),"isInFailedCounter",String.valueOf(result));
        return result;
    }


}