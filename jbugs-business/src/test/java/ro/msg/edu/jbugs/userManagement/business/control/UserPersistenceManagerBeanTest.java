package ro.msg.edu.jbugs.userManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.CheckedBusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.DetailedExceptionCode;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.utils.Encryptor;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.validator.UserValidator;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerBeanTest {

    @InjectMocks
    private UserManagementController userManagementController;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private UserValidator userValidator;


    private static Object invoke(Object obj, String methodName,
                                 Object... params) {
        int paramCount = params.length;
        Method method;
        Object requiredObj = null;
        Class<?>[] classArray = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; i++) {
            classArray[i] = params[i].getClass();
        }
        try {
            method = obj.getClass().getDeclaredMethod(methodName, classArray);
            method.setAccessible(true);
            requiredObj = method.invoke(obj, params);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            fail("");
        }

        return requiredObj;
    }

    @Test
    public void generateFullUsername_expectedMarini() {


        String username = (String) invoke(userManagementController, "generateFullUsername", "Ion", "Marin");
        assertEquals("marini", username);
    }

    @Test
    public void generateFullUsername_expectedIonion() {
        String username = (String) invoke(userManagementController, "generateFullUsername", "Ion", "Ion");
        assertEquals("ionion", username);
    }

    @Test
    public void generateFullUsername_expectedPetric() {
        String username = (String) invoke(userManagementController, "generateFullUsername", "Calin", "Petrindean");
        assertEquals("petric", username);
    }

    @Test
    public void generateFullUsername_expectedba0000() {
        String username = (String) invoke(userManagementController, "generateFullUsername", "a", "b");
        assertEquals("ba0000", username);
    }

    @Test
    public void generateUsernameSuffix_expectedEmpty() {
        when(userPersistenceManager.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<>());

        String suffix = (String) invoke(userManagementController, "generateUsernameSuffix", "dorel0");
        assertEquals("", suffix);
    }

    @Test
    public void generateUsernameSuffix_expected4() {

        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("dorel0");
                            add("dorel01");
                            add("dorel02");
                            add("dorel03");
                        }}
                );
        String suffix = (String) invoke(userManagementController, "generateUsernameSuffix", "dorel0");
        assertEquals("4", suffix);

    }

    @Test
    public void generateUsernameSuffix_expected7() {


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("dorel0");
                            add("dorel06");
                        }}
                );
        String suffix = (String) invoke(userManagementController, "generateUsernameSuffix", "dorel0");
        assertEquals("7", suffix);

    }

    @Test
    public void generateUsernameSuffix_expected1() {


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("marini");
                        }}
                );
        String suffix = (String) invoke(userManagementController, "generateUsernameSuffix", "marini");
        assertEquals("1", suffix);
    }


    @Test
    public void testLogin_wrongUsername() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        try {
            userManagementController.login("a", "s");
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USER_VALIDATION_EXCEPTION, e.getExceptionCode());
        } catch (CheckedBusinessException e) {
            assertEquals(ExceptionCode.USER_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_wrongPassword() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("ioani");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("1234"));

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            UserDTO userDTO = userManagementController.login("ioani", "not1234");
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USER_VALIDATION_EXCEPTION, e.getExceptionCode());
        } catch (CheckedBusinessException e) {
            assertEquals(ExceptionCode.USER_VALIDATION_EXCEPTION, e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_Success() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));
        when(user.getIsActive()).thenReturn(true);

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            UserDTO userDTO = userManagementController.login("salut", "secret");
            assertEquals(userDTO.getUsername(), user.getUsername());
        } catch (BusinessException | CheckedBusinessException e) {
            fail("Shouldn't reach this point.");
        }
    }

    public void testLogin_notActive() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));
        when(user.getIsActive()).thenReturn(false);

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            UserDTO userDTO = userManagementController.login("salut", "secret");
            fail("FAILED");
        } catch (BusinessException e) {
            assertEquals(e.getExceptionCode(), ExceptionCode.USER_VALIDATION_EXCEPTION);
            assertEquals(e.getDetailedExceptionCode(), DetailedExceptionCode.USER_DISABLED);
        } catch (CheckedBusinessException e) {
            fail("FAILED");
        }
    }

    @Test
    public void testCreateUser_Success() {

        Role role = new Role();
        role.setId(1L);
        role.setType1("DEV");
        role.setPermissions(new ArrayList<>());
        List<Role> dbRoles = new ArrayList<>();
        dbRoles.add(role);
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());
        when(userPersistenceManager.getRoleByType(any(String.class)))
                .thenReturn(role);
        when(userPersistenceManager.getAllRoles())
                .thenReturn(dbRoles);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0720512346");
        userDTO.setPassword("IloveSteaua");
        userDTO.setRoles(new ArrayList<>(Arrays.asList(new RoleDTO() {{
            setId(1L);
            setType("DEV");
            setPermissions(new ArrayList<>());
        }})));

        User result = UserDTOHelper.toEntity(userDTO, new User());
        result.setUsername("borcec");

        //Mockito.doThrow(new RuntimeException()).when(userValidator).validateCreate(userDTO);
        Mockito.doNothing().when(userValidator).validateCreate(userDTO);
        when(userPersistenceManager.createUser(any(User.class))).thenReturn(result);

        try {
            UserDTO createdUser = userManagementController.createUser(userDTO);
            assertEquals(userDTO.getFirstName(), createdUser.getFirstName());
            assertEquals(userDTO.getLastName(), createdUser.getLastName());
            assertEquals(userDTO.getEmail(), createdUser.getEmail());
            assertEquals("borcec", createdUser.getUsername());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void testCreateUser_DuplicateEmail() {
        Role role = new Role();
        role.setId(1L);
        role.setType1("DEV");
        role.setPermissions(new ArrayList<>());
        List<Role> dbRoles = new ArrayList<>();
        dbRoles.add(role);
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(new User()));
        when(userPersistenceManager.getRoleByType(any(String.class)))
                .thenReturn(role);
        when(userPersistenceManager.getAllRoles())
                .thenReturn(dbRoles);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0720512346");
        userDTO.setPassword("IloveSteaua");
        userDTO.setRoles(new ArrayList<>(Arrays.asList(new RoleDTO() {{
            setId(1L);
            setType("DEV");
            setPermissions(new ArrayList<>());
        }})));

        User result = UserDTOHelper.toEntity(userDTO, new User());
        result.setUsername("borcec");

        //Mockito.doThrow(new RuntimeException()).when(userValidator).validateCreate(userDTO);
        Mockito.doNothing().when(userValidator).validateCreate(userDTO);
        when(userPersistenceManager.createUser(any(User.class))).thenReturn(result);

        try {
            UserDTO createdUser = userManagementController.createUser(userDTO);
            fail("FAIL");
        } catch (BusinessException e) {
            assertEquals(e.getExceptionCode(), ExceptionCode.USER_VALIDATION_EXCEPTION);
            assertEquals(e.getDetailedExceptionCode(), DetailedExceptionCode.USER_DUPLICATE_EMAIL);
        }
    }




    /*


    @Test
    public void testIsValidPhoneNumber_Success() {
        assertEquals(userManagementController.isValidPhoneNumber("123-456-7890"), true);
        assertEquals(userManagementController.isValidPhoneNumber("(123) 456-7890"), true);
        assertEquals(userManagementController.isValidPhoneNumber("123 456 7890"), true);
        assertEquals(userManagementController.isValidPhoneNumber("+91 (123) 456-7890"), true);
        assertEquals(userManagementController.isValidPhoneNumber("123.456.7890"), true);


        assertEquals(userManagementController.isValidPhoneNumber("0720512346"), true);
        assertEquals(userManagementController.isValidPhoneNumber("+40213.564.864"), true);
        assertEquals(userManagementController.isValidPhoneNumber("+40213 564 864"), true);

    }

    @Test
    public void testIsValidPhoneNumber_Fail() {
        assertEquals(userManagementController.isValidPhoneNumber("0213/564/864"), false);
        assertEquals(userManagementController.isValidPhoneNumber("0413564864"), false);
        assertEquals(userManagementController.isValidPhoneNumber("0790512346"), false);
        assertEquals(userManagementController.isValidPhoneNumber(""), false);
        assertEquals(userManagementController.isValidPhoneNumber("abc"), false);

    }
    */

    @Test
    public void getAllUsers_expectedNull() {
        when(userPersistenceManager.getAllUsers()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<UserDTO>(), userManagementController.getAllUsers());
    }

    @Test
    public void getAllUsers_expectedList() {
        User u1 = new User();
        User u2 = new User();
        u1.setId((long) 6);
        u1.setFirstName("dorel");
        u1.setLastName("dorel");
        u1.setEmail("doreldorel@msggroup.com");
        u1.setPhoneNumber("1234567890");
        u1.setUsername("Dorelut");
        u1.setIsActive(true);
        u1.setRoles(new ArrayList<>());

        u2.setId((long) 7);
        u2.setFirstName("dorel");
        u2.setLastName("dorel");
        u2.setEmail("doreldorel@msggroup.com");
        u2.setPhoneNumber("1234567890");
        u2.setUsername("Dorelut");
        u2.setIsActive(true);
        u2.setRoles(new ArrayList<>());

        List<User> users = new ArrayList<>(Arrays.asList(u1, u2));
        when(userPersistenceManager.getAllUsers()).thenReturn(users);

        List<User> actuals = userManagementController.getAllUsers()
                .stream()
                .map(u -> UserDTOHelper.toEntity(u, new User()))
                .collect(Collectors.toList());
        assertEquals(users, actuals);
    }

    @Test
    public void testUpdateUser_Success() {
        Role role = new Role();
        role.setId(1L);
        role.setType1("DEV");
        role.setPermissions(new ArrayList<>());
        List<Role> dbRoles = new ArrayList<>();
        dbRoles.add(role);
        when(userPersistenceManager.getRoleByType(any(String.class)))
                .thenReturn(role);
        when(userPersistenceManager.getAllRoles())
                .thenReturn(dbRoles);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0720512346");
        List<RoleDTO> roleDTOList = new ArrayList<>();
        roleDTOList.add(RoleDTOHelper.fromEntity(role));
        userDTO.setRoles(roleDTOList);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setFirstName("Marian");
        userDTO1.setLastName("Belean");
        userDTO1.setEmail("steaua@msggroup.com");
        userDTO1.setPhoneNumber("0720512347");
        userDTO1.setRoles(new ArrayList<>());

        when(userPersistenceManager.getUserById(any(Long.class)))
                .thenReturn(Optional.of(UserDTOHelper.toEntity(userDTO, new User())));

        try {
            UserDTO updatedUser = userManagementController.updateUser(userDTO1);
            assertEquals(userDTO1.getFirstName(), updatedUser.getFirstName());
            assertEquals(userDTO1.getLastName(), updatedUser.getLastName());
            assertEquals(userDTO1.getEmail(), updatedUser.getEmail());
            assertEquals(userDTO1.getPhoneNumber(), updatedUser.getPhoneNumber());
            assertEquals(userDTO1.getRoles(), updatedUser.getRoles());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }
}