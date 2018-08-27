package ro.msg.edu.jbugs.userManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.shared.business.utils.Encryptor;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

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
/*

    @InjectMocks
    private UserManagementController userManagementController;

    @Mock
    private UserPersistenceManager userPersistenceManager;


    @Test
    public void generateUsername_expectedMarini() {
        String username = userManagementController.generateUsername("Ion", "Marin");
        assertEquals("marini", username);
    }

    @Test
    public void generateUsername_expectedIonion() {
        String username = userManagementController.generateUsername("Ion", "Ion");
        assertEquals("ionion", username);
    }

    @Test
    public void generateUsername_expectedPetric() {
        String username = userManagementController.generateUsername("Calin", "Petrindean");
        assertEquals("petric", username);
    }

    @Test
    public void generateUsername_expectedba0000() {
        String username = userManagementController.generateUsername("a", "b");
        assertEquals("ba0000", username);
    }

    @Test
    public void createSuffix_expectedEmpty() {

        when(userPersistenceManager.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<>());
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals("", suffix);

    }

    @Test
    public void createSuffix_expected4() {


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("dorel0");
                            add("dorel01");
                            add("dorel02");
                            add("dorel03");
                        }}
                );
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals("4", suffix);

    }

    @Test
    public void createSuffix_expected7() {


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("dorel0");
                            add("dorel06");
                        }}
                );
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals("7", suffix);

    }

    @Test
    public void createSuffix_expected1() {


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>() {{
                            add("marini");
                        }}
                );
        String suffix = userManagementController.createSuffix("marini");
        assertEquals("1", suffix);
    }

    /*

    @Test
    public void testLogin_wrongUsername() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        try {
            userManagementController.login("a", "s");
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        } catch (CheckedBusinessException e) {
            e.printStackTrace();
        }
    }
*/

/*
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
            assertEquals(ExceptionCode.PASSWORD_NOT_VALID, e.getExceptionCode());
        } catch (CheckedBusinessException e) {
            e.printStackTrace();
        }
    }
*//*
    @Test
    public void testLogin_Success() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        try {
            UserDTO userDTO = userManagementController.login("salut", "secret");
            assertEquals(userDTO.getUsername(), user.getUsername());
        } catch (BusinessException e) {
            fail("Shouldn't reach this point");
        } catch (CheckedBusinessException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateUser_Success() {
        Role role = new Role();
        role.setId(1L);
        role.setType("DEV");
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

    @Test
    public void getAllUsers_expectedNull() {
        when(userPersistenceManager.getAllUsers()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<UserDTO>(), userManagementController.getAllUsers());
    }

    @Test
    public void getAllUsers_expectedList() {
        User u1 = new User();
        User u2 = new User();
        u1.setId((long)6);
        u1.setFirstName("dorel");
        u1.setLastName("dorel");
        u1.setEmail("doreldorel@msggroup.com");
        u1.setPhoneNumber("1234567890");
        u1.setUsername("Dorelut");
        u1.setIsActive(true);
        u1.setRoles(new ArrayList<>());

        u2.setId((long)7);
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
                .map(UserDTOHelper::toEntity)
                .collect(Collectors.toList());
        assertEquals(actuals, users);
    }

    @Test
    public void testUpdateUser_Success(){
        Role role = new Role();
        role.setId(1L);
        role.setType("DEV");
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
                .thenReturn(Optional.of(UserDTOHelper.toEntity(userDTO)));

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
*/
}