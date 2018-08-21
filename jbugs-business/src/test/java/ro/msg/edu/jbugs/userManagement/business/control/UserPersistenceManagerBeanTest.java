package ro.msg.edu.jbugs.userManagement.business.control;

import org.junit.Before;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.utils.Encryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerBeanTest {



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
    public void createSuffix_expectedEmpty(){

        when(userPersistenceManager.getUsernamesLike(any(String.class))).thenReturn(new ArrayList<>());
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals( "",suffix);

    }

    @Test
    public void createSuffix_expected4(){


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>(){{
                            add("dorel0");
                            add("dorel01");
                            add("dorel02");
                            add("dorel03");
                        }}
                );
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals( "4",suffix);

    }

    @Test
    public void createSuffix_expected7(){


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>(){{
                            add("dorel0");
                            add("dorel06");
                        }}
                );
        String suffix = userManagementController.createSuffix("dorel0");
        assertEquals("7",suffix);

    }

    @Test
    public void createSuffix_expected1(){


        when(userPersistenceManager.getUsernamesLike(any(String.class)))
                .thenReturn(
                        new ArrayList<String>(){{
                            add("marini");
                        }}
                );
        String suffix = userManagementController.createSuffix("marini");
        assertEquals( "1",suffix);
    }

    @Test
    public void testLogin_wrongUsername() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        try {
            userManagementController.login("a", "s");
            fail("Shouldn't reach this point");
        } catch (BusinessException e){
            assertEquals(ExceptionCode.USERNAME_NOT_VALID,e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_Success() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        try{
            UserDTO userDTO = userManagementController.login("salut","secret");
            assertEquals(userDTO.getUsername(),user.getUsername());
        } catch(BusinessException e){
            fail("Shouldn't reach this point");
        }
    }

    @Test
    public void testCreateUser_Success(){
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
        try{
        UserDTO createdUser = userManagementController.createUser(userDTO);
        assertEquals(userDTO.getFirstName(),createdUser.getFirstName());
        assertEquals(userDTO.getLastName(),createdUser.getLastName());
        assertEquals(userDTO.getEmail(),createdUser.getEmail());
        assertEquals("borcec",createdUser.getUsername());
        } catch (BusinessException e){
            fail("Should not reach this point");
        }
    }

    @Test
    public void testIsValidPhoneNumber_Success(){
        assertEquals(userManagementController.isValidPhoneNumber("123-456-7890"),true);
        assertEquals(userManagementController.isValidPhoneNumber("(123) 456-7890"),true);
        assertEquals(userManagementController.isValidPhoneNumber("123 456 7890"),true);
        assertEquals(userManagementController.isValidPhoneNumber("+91 (123) 456-7890"),true);
        assertEquals(userManagementController.isValidPhoneNumber("123.456.7890"),true);


        assertEquals(userManagementController.isValidPhoneNumber("0720512346"),true);
        assertEquals(userManagementController.isValidPhoneNumber("+40213.564.864"),true);
        assertEquals(userManagementController.isValidPhoneNumber("+40213 564 864"),true);

    }

    @Test
    public void testIsValidPhoneNumber_Fail(){
        assertEquals(userManagementController.isValidPhoneNumber("0213/564/864"),false);
        assertEquals(userManagementController.isValidPhoneNumber("0413564864"),false);
        assertEquals(userManagementController.isValidPhoneNumber("0790512346"),false);
        assertEquals(userManagementController.isValidPhoneNumber(""),false);
        assertEquals(userManagementController.isValidPhoneNumber("abc"),false);


    }

}