package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.utils.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserManagementController implements UserManagement {
    //TODO rename;
    private final static int MAX_LAST_NAME_LENGTH = 5;
    private final static int MIN_USERNAME_LENGTH = 6;
    private static final Logger logger = LogManager.getLogger(UserManagementController.class);

    @EJB
    private UserPersistenceManager userPersistenceManager;

    /**
     * Creates a user entity using a user DTO.
     * @param userDTO user information
     * @return : the user DTO of the created entity
     * @throws BusinessException
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) throws BusinessException {

        logger.log(Level.INFO, "In createUser method");

        normalizeUserDTO(userDTO);
        validateUserForCreation(userDTO);
        User user = UserDTOHelper.toEntity(userDTO);
        user.setUsername(generateFullUsername(userDTO.getFirstName(),userDTO.getLastName()));
        user.setIsActive(true);
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistenceManager.createUser(user);

        return UserDTOHelper.fromEntity(user);
    }


    /**
     * Validates the DTO. To use before sending it further.
     * @param userDTO
     * @throws BusinessException
     */
    private void validateUserForCreation(UserDTO userDTO) throws BusinessException {
        if (!isValidForCreation(userDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
        //validate if email already exists
        if (userPersistenceManager.getUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }


    }

    /**
     * Trims stuff (first and last name)
     *
     * @param userDTO
     */
    private void normalizeUserDTO(UserDTO userDTO) {
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
    }

    /**
     * Creates a suffix for the username, if the username already exists. The suffix consists
     * of a number.
     * TODO : Change this. Probably won't be needed.
     * @param username
     * @return
     */
    protected String createSuffix(String username) {

        Optional<Integer> max = userPersistenceManager.getUsernamesLike(username)
                .stream()
                .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                .map(x -> x.equals("") ? 0 : Integer.parseInt(x))
                .max(Comparator.naturalOrder())
                .map(x -> x + 1);
        return max.map(Object::toString).orElse("");
    }

    private boolean isValidForCreation(UserDTO user) {
        return user.getEmail() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getPassword() != null
                && isValidEmail(user.getEmail());
    }

    private boolean isValidEmail(String email) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


    /**
     * Generates a username, taking the first 5 letters of the last name and the first
     * letter of the first name.
     * If the user's last name is not long enough it will try
     * to add the first name's letters to the username until it has 6 characters.
     * If the username already exists it will append a number to the username.
     *
     * TODO : Change the algorithm.
     *
     * @param firstName
     * @param lastName
     * @return generated username
     */
    protected String generateUsername(@NotNull final String firstName, @NotNull final String lastName) {
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


        return username.toString().toLowerCase();

    }

    /**
     * Deactivates a user, removing them the ability to login, but keeping their bugs, comments, etc.
     * @param username
     */
    @Override
    public void deactivateUser(String username) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if(userOptional.isPresent()) {
            User user = userPersistenceManager.getUserByUsername(username).get();
            user.setIsActive(false);
            userPersistenceManager.updateUser(user);
        }
        else{
            throw (new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        }

    }

    /**
     * Activates a user, granting them the ability to login. asdf
     * @param username
     */
    @Override
    public void activateUser(String username) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setIsActive(true);
            userPersistenceManager.updateUser(user);
        } else{
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }

    /**
     * Get a list of all Users that are registered.
     * @return
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userPersistenceManager.getAllUsers()
                .stream()
                .map(UserDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Takes the username and password of a user and if they are correct, it returns the
     * corresponding DTO. Otherwise it will throw an exception.
     * @param username
     * @param password
     * @return a user DTO if it succeeds.
     * @throws BusinessException
     */
    @Override
    public UserDTO login(String username, String password) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
        if (!Encryptor.encrypt(password).equals(userOptional.get().getPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        }

        return UserDTOHelper.fromEntity(userOptional.get());
    }

    private String generateFullUsername(String firstName, String lastName){
        String prefix = generateUsername(firstName,lastName);
        String suffix = createSuffix(prefix);
        return prefix+suffix;
    }

    private boolean isValidPhoneNumber(String phonenumber){
        //TODO Nu merge
        final Pattern VALID_PHONE_ADDRESS_REGEX =
                Pattern.compile("(^\\+49)|(^01[5-7][1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(phonenumber);
        return matcher.find();
    }
}