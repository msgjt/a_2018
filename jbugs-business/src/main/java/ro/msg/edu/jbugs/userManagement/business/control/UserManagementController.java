package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.utils.Encryptor;
import ro.msg.edu.jbugs.shared.persistence.util.CustomLogger;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.validator.UserValidator;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

    @Stateless
    public class UserManagementController implements UserManagement {

        private final static int MAX_LAST_NAME_LENGTH = 5;
        private final static int MIN_USERNAME_LENGTH = 6;
        @SuppressWarnings("all")
        private static Map<String, Integer> failedCounter = new HashMap<>();
        @SuppressWarnings("all")
        private static Map<String, String> loggedUsers = new HashMap<>();

        @EJB
        private UserValidator userValidator;

        @EJB
        private UserPersistenceManager userPersistenceManager;


        /**
         * Getter method for all the users.
         *
         * @return the list of all the userDTOs present (enabled or disabled).
         */
        @Override
        public List<UserDTO> getAllUsers() {
            CustomLogger.logEnter(this.getClass(), "getAllUsers", "");

            List<UserDTO> result = userPersistenceManager.getAllUsers()
                    .stream()
                    .map(UserDTOHelper::fromEntity)
                    .collect(Collectors.toList());

            CustomLogger.logExit(this.getClass(), "getAllUsers", result.toString());
            return result;
        }

        /**
         * Creates a new user from a userDTO. Will call a validation method for the parameter and will set the other
         * fields accordingly (isActive = true, username = generated, password = encrypted, roles = default if none)
         *
         * @param userDTO contains the required user information in its fields, will be validated
         * @return : the persisted userDTO
         */
        @Override
        public UserDTO createUser(UserDTO userDTO) {
            CustomLogger.logEnter(this.getClass(), "createUser", String.valueOf(userDTO));

            userValidator.validateCreate(userDTO); // THROWS VALIDATION BUSINESS EXCEPTIONS
            validateRoles(userDTO);
            userDTO = normalizeUserDTO(userDTO);

            if (userPersistenceManager.getUserByEmail(userDTO.getEmail()).isPresent()) {
                CustomLogger.logException(this.getClass(), "validateUserForCreation",
                        ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_DUPLICATE_EMAIL);
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.USER_DUPLICATE_EMAIL);
            }

            User user = UserDTOHelper.toEntity(userDTO,getOldUserFields(userDTO));
            user.setIsActive(true);
            user.setUsername(generateFullUsername(userDTO.getFirstName(), userDTO.getLastName()));
            user.setPassword(Encryptor.encrypt(userDTO.getPassword()));

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                Role defaultRole = userPersistenceManager.getRoleByType("DEV");
                user.setRoles(new ArrayList<>(Collections.singleton(defaultRole)));
            }

            User createdUser = userPersistenceManager.createUser(user);
            UserDTO result = UserDTOHelper.fromEntity(createdUser);
            createWelcomeNotification(createdUser);

            CustomLogger.logExit(this.getClass(), "createUser", result.toString());
            return result;
        }

        /**
         * Updates a user from a userDTO. Will call a validation method for the parameter and will set the other
         * fields accordingly (by calling the copy method from the user class)
         *
         * @param userDTO contains the required user information in its fields, will be validated
         * @return : the persisted userDTO
         */
        @Override
        public UserDTO updateUser(UserDTO userDTO) {
            CustomLogger.logEnter(this.getClass(), "updateUser", String.valueOf(userDTO));
            validateRoles(userDTO);
            userDTO = normalizeUserDTO(userDTO);

            User oldUser = userPersistenceManager.getUserById(userDTO.getId())
                    .orElseThrow(() -> new BusinessException(
                            ExceptionCode.USER_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.USER_NOT_FOUND)
                    );
            oldUser = UserDTOHelper.toEntity(userDTO,oldUser);

            if (oldUser.getRoles() == null || oldUser.getRoles().isEmpty()) {
                Role defaultRole = userPersistenceManager.getRoleByType("DEV");
                oldUser.setRoles(new ArrayList<>(Collections.singleton(defaultRole)));
            }

            if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty() && userDTO.getFirstName()!="null" && userDTO.getFirstName()!=" "  ) {
                oldUser.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null && !userDTO.getLastName().isEmpty()  && userDTO.getLastName()!="null" && userDTO.getFirstName()!=" " ) {
                oldUser.setLastName(userDTO.getLastName());
            }
            if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()  && userDTO.getEmail()!="null" && userDTO.getFirstName()!=" " ) {
                oldUser.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isEmpty()  && userDTO.getPhoneNumber()!="null" && userDTO.getFirstName()!=" " ) {
                oldUser.setPhoneNumber(userDTO.getPhoneNumber());
            }

            userPersistenceManager.updateUser(oldUser);
            UserDTO result = UserDTOHelper.fromEntity(oldUser);

            CustomLogger.logExit(this.getClass(), "updateUser", result.toString());
            return result;
        }

        @Override
        public UserDTO updateUserPassword(Long id, String password) {
            User oldUser = userPersistenceManager.getUserById(id)
                    .orElseThrow(() -> new BusinessException(
                            ExceptionCode.USER_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.USER_NOT_FOUND)
                    );
            oldUser.setPassword(Encryptor.encrypt(password));
            userPersistenceManager.updateUser(oldUser);
            UserDTO result = UserDTOHelper.fromEntity(oldUser);
            return result;
        }

        /**
         * Checks if the roles of a userDTO are present in the DB.
         *
         * @param userDTO the user for which the roles will be checked
         */
        private void validateRoles(UserDTO userDTO) {
            List<Role> recievedRoles = userDTO.getRoles()
                    .stream()
                    .map(RoleDTOHelper::toEntity)
                    .collect(Collectors.toList());
            List<Role> possibleRoles = userPersistenceManager.getAllRoles();
            for (Role r : recievedRoles) {
                if (!possibleRoles.contains(r)) {
                    throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                            DetailedExceptionCode.USER_ROLES_NOT_VALID);
                }
            }
        }

        /**
         * Trims firstName and lastName of the userDTO.
         *
         * @param userDTO not null
         */
        private UserDTO normalizeUserDTO(@NotNull UserDTO userDTO) {
            CustomLogger.logEnter(this.getClass(), "normalizeUserDTO", userDTO.toString());

            userDTO.setFirstName(userDTO.getFirstName().trim());
            userDTO.setLastName(userDTO.getLastName().trim());

            CustomLogger.logExit(this.getClass(), "normalizeUserDTO", userDTO.toString());
            return userDTO;
        }

        /**
         * Will generate the username for the given firstName and lastName. Will call methods
         * to generate the suffix and the prefix of the username;
         *
         * @param firstName not null
         * @param lastName  not null
         * @return the full username
         */
        private String generateFullUsername(@NotNull String firstName, @NotNull String lastName) {
            CustomLogger.logEnter(this.getClass(), "generateFullUsername", firstName, lastName);

            String prefix = generateUsernamePrefix(firstName, lastName);
            String suffix = generateUsernameSuffix(prefix);
            String result = prefix + suffix;

            CustomLogger.logExit(this.getClass(), "generateFullUsername", result);
            return result;
        }

        /**
         * Creates a suffix for the username, if the username already exists. The suffix consists
         * of a number.
         *
         * @param username the username for which the suffix will be created, not null
         * @return the suffix generated for the username
         */
        private String generateUsernameSuffix(@NotNull String username) {
            CustomLogger.logEnter(this.getClass(), "generateUsernameSuffix", username);

            Optional<Integer> max = userPersistenceManager.getUsernamesLike(username)
                    .stream()
                    .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                    .map(x -> x.equals("") ? 0 : Integer.parseInt(x))
                    .max(Comparator.naturalOrder())
                    .map(x -> x + 1);

            String result = max.map(Object::toString).orElse("");

            CustomLogger.logExit(this.getClass(), "generateUsernameSuffix", result);
            return result;
        }

        /**
         * Generates a username, taking the first 5 letters of the last name and the first
         * letter of the first name.
         * If the user's last name is not long enough it will try
         * to add the first name's letters to the username until it has 6 characters.
         * If the username already exists it will append a number to the username.
         *
         * @param firstName not null
         * @param lastName  not null
         * @return generated username
         */
        private String generateUsernamePrefix(@NotNull final String firstName, @NotNull final String lastName) {
            CustomLogger.logEnter(this.getClass(), "generateUsernamePrefix", firstName, lastName);

            StringBuilder username = new StringBuilder();

            if (lastName.length() >= MAX_LAST_NAME_LENGTH) {
                username.append(lastName, 0, MAX_LAST_NAME_LENGTH).append(firstName.charAt(0));

            } else if (lastName.length() + firstName.length() >= MIN_USERNAME_LENGTH) {
                username.append(lastName).append(firstName, 0, MIN_USERNAME_LENGTH - lastName.length());
            } else {
                username.append(lastName).append(firstName);
                int usernameLength = username.length();
                for (int i = 0; i < MIN_USERNAME_LENGTH - usernameLength; i++)
                    username.append("0");
            }

            String result = username.toString().toLowerCase();

            CustomLogger.logExit(this.getClass(), "generateUsernamePrefix", result);
            return result;
        }

        /**
         * Activates a user (sets isActive to true)
         *
         * @param id will be validated for null values, or not present
         * @return the persisted userDTO
         */
        @Override
        public UserDTO activateUser(Long id) {
            userValidator.validateId(id);
            return setActiveStatus(id, true);
        }

        /**
         * Deactivates a user (sets isActive to false)
         *
         * @param id will be validated for null values, or not present
         * @return the persisted userDTO
         */
        @Override
        public UserDTO deactivateUser(Long id) {
            userValidator.validateId(id);
            return setActiveStatus(id, false);
        }

        /**
         * Sets the given active status to a user. Will check if the user corresponding to the id is present.
         *
         * @param id    the id of the user
         * @param value the desired active status
         * @return the persisted userDTO
         */
        private UserDTO setActiveStatus(long id, boolean value) {
            CustomLogger.logEnter(this.getClass(), "setActiveStatus", String.valueOf(id), String.valueOf(value));

            UserDTO result = UserDTOHelper.fromEntity(
                    userPersistenceManager.getUserById(id)
                            .map(user -> {
                                user.setIsActive(value);
                                return userPersistenceManager.updateUser(user);
                            }).orElseThrow(() -> {
                        CustomLogger.logException(this.getClass(), "setActiveStatus",
                                ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_NOT_FOUND);
                        return new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION, DetailedExceptionCode.USER_NOT_FOUND);
                    }));

            CustomLogger.logExit(this.getClass(), "setActiveStatus", result.toString());
            return result;
        }

        /**
         * @param id the id of the desired user, will be validated for null values
         * @return the required userDTO
         */
        @Override
        public UserDTO getUserById(Long id) {
            userValidator.validateId(id);

            return UserDTOHelper.fromEntity(userPersistenceManager.getUserById(id).orElseThrow(
                    () -> new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION, DetailedExceptionCode.USER_NOT_FOUND)
            ));
        }

        /**
         * @param username the username of the desired user, will be validated for null values
         * @return the required userDTO
         */
        @Override
        public UserDTO getUserByUsername(String username) {
            userValidator.validateUsername(username);

            return UserDTOHelper.fromEntity(userPersistenceManager.getUserByUsername(username).orElseThrow(
                    () -> new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION, DetailedExceptionCode.USER_NOT_FOUND)
            ));
        }

        /**
         * Checks if a user is already in the failed counter map.
         *
         * @param username not null
         * @return true if the user is in the failed counter, false otherwise
         */
        // check if a specific user already exist in the failedCounter map
        private boolean isInFailedCounter(@NotNull String username) {
            CustomLogger.logEnter(this.getClass(), "isInFailedCounter", username);

            boolean result = failedCounter.containsKey(username);

            CustomLogger.logExit(this.getClass(), "isInFailedCounter", String.valueOf(result));
            return result;
        }

        /**
         * Adds the value "token" to the key "username" in the logged users map.
         *
         * @param username not null
         * @param token    the session token, not null
         */
        public void addInLoggedUsers(@NotNull String username, @NotNull String token) {
            loggedUsers.put(username, token);
        }

        /**
         * Checks if the user with the given username and token is present in the loggedUsers map.
         *
         * @param username not null, the search key in the loggedUsera map
         * @param token    not null, the value that must be present at the search key
         * @return true if the username @username is present in the loggedUsers map and contains the value @token
         */
        public boolean checkLoggedUser(@NotNull String username, @NotNull String token) {
            return loggedUsers.containsKey(username) && loggedUsers.get(username).equals(token);
        }

    public boolean checkLoggedUserByUsername(@NotNull String username){
        return loggedUsers.containsKey(username);
    }

    /**
     * Remove the user with the given username from the loggedIn map.
     * @param username the key to be removed
     */
    public void removeUserInLogged(String username){
        loggedUsers.remove(username);
    }

        /**
         * Logout method for a username. Will remove the user from the loggedUsers map.
         *
         * @param username the username of the user that must log out
         * @return false
         */
        public boolean logout(String username) {
            loggedUsers.remove(username);
            return loggedUsers.containsKey(username);
        }


        private User getOldUserFields(UserDTO newUserDTO){
            User user =
                    newUserDTO.getId() != null ?
                            (
                                    userPersistenceManager.getUserById(newUserDTO.getId()).orElseGet(User::new)
                            )

                            :

                            (
                                    newUserDTO.getUsername() != null ?
                                            (
                                                    userPersistenceManager.getUserByUsername(newUserDTO.getUsername()).orElseGet(User::new)
                                            )

                                            :

                                            (
                                                    new User()
                                            )
                            );
            return user;
        }



        /* TODO - IMPORTANT!!!!! REFACTOR ALL BELOW THIS LINE */
        /* TODO - IMPORTANT!!!!! REFACTOR ALL BELOW THIS LINE */
        /* TODO - IMPORTANT!!!!! REFACTOR ALL BELOW THIS LINE */
        /* TODO - IMPORTANT!!!!! REFACTOR ALL BELOW THIS LINE */

        /**
         * Takes the username and password of a user and if they are correct, it returns the
         * corresponding DTOHelper. Otherwise it will throw an exception.
         *
         * @param username .
         * @param password .
         * @return a user DTOHelper if it succeeds.
         */
        @Override
        public UserDTO login(String username, String password) throws CheckedBusinessException {
            CustomLogger.logEnter(this.getClass(), "login", username, password);

            Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
            //check if the username exist in the database otherwise the login method throw an Exception Code
            if (!userOptional.isPresent()) {
                CustomLogger.logException(this.getClass(), "login",
                        ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_NOT_FOUND);
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.USER_NOT_FOUND);
            }

            if (!userOptional.get().getIsActive()) {
                CustomLogger.logException(this.getClass(), "login", DetailedExceptionCode.USER_DISABLED.toString());
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.USER_DISABLED);
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
                        //TODO this will rollback after the runtime exception is thrown
                        deactivateUser(userOptional.get().getId());
                        CustomLogger.logException(this.getClass(), "login", DetailedExceptionCode.USER_LOGIN_FAILED_FIVE_TIMES.toString());
                        throw new CheckedBusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                                DetailedExceptionCode.USER_LOGIN_FAILED_FIVE_TIMES);
                    }
                }
                CustomLogger.logException(this.getClass(), "login",
                        ExceptionCode.USER_VALIDATION_EXCEPTION + " " + DetailedExceptionCode.USER_NOT_FOUND);
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION,
                        DetailedExceptionCode.USER_NOT_FOUND);
            }
            //in case the user login with success the username is reoved from the map
            if (isInFailedCounter(userOptional.get().getUsername())) {
                System.out.println("Username:  " + userOptional.get().getUsername() + "tried wrong password:   " + failedCounter.get(userOptional.get().getUsername()));
                failedCounter.remove(userOptional.get().getUsername());
            }
            UserDTO result = UserDTOHelper.fromEntity(userOptional.get());

            CustomLogger.logExit(this.getClass(), "login", result.toString());
            return result;
        }


        //get all permissions assigned to an user
        public Set<String> getAllUserPermission(String username) {
            Optional<User> user = userPersistenceManager.getUserByUsername(username);
            Set<Permission> allPermisssion = new HashSet<>();
            List<Permission> allPermisionsForAnUser = new ArrayList<>();
            if (user.isPresent()) {
                List<Role> roles = user.get().getRoles();
                for (Role role : roles) {
                    List<Permission> allPermisionsInRole = new ArrayList<>();
                    allPermisionsInRole = role.getPermissions();
                    for (Permission p : allPermisionsInRole) {
                        allPermisssion.add(p);
                    }
                }
            }


            Set<String> permisionString= new HashSet<>();
            allPermisionsForAnUser.addAll(allPermisssion);
            for(Permission p : allPermisionsForAnUser)
            {permisionString.add(p.getType2());}
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

    public void createWelcomeNotification(User user){
        Notification notification= new Notification();
        notification.setStatus("not_read");
        notification.setMessage("Welcome");
        notification.setType("WELCOME_NEW_USER");
        notification.setURL("Welcome new user");
        userPersistenceManager.createNotification(notification);
        List<Notification> notifications= user.getNotifications();
        notifications.add(notification);
        user.setNotifications(notifications);
        userPersistenceManager.updateUser(user);
    }
}
