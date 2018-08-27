package ro.msg.edu.jbugs.userManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTOHelper;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.shared.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleManagementControllerTest {

    @InjectMocks
    private RoleManagementController roleManagementController;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Test
    public void getAllRoles_expectedList() {
        Role r1 = new Role();
        Role r2 = new Role();
        r1.setType("ADM");
        r1.setPermissions(new ArrayList<>());
        r2.setType("DEV");
        r2.setPermissions(new ArrayList<>());
        List<Role> roles = new ArrayList<>(Arrays.asList(r1,r2));
        when(userPersistenceManager.getAllRoles()).thenReturn(roles);

            List<Role> actuals = roleManagementController.getAllRoles()
                    .stream()
                    .map(r -> {
                        try {
                            return RoleDTOHelper.toEntity(r);
                        } catch (BusinessException e) {
                            fail("Should not get here");
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            assertEquals(actuals, roles);

    }

    @Test
    public void getAllRoles_expectedEmptyList() {
        when(userPersistenceManager.getAllRoles()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<RoleDTO>(),roleManagementController.getAllRoles());
    }


    @Test
    public void updateRole(){
        Role toBeUpdated = new Role();
        toBeUpdated.setId(1L);
        toBeUpdated.setType("tobe");

        Role expected = new Role();
        expected.setId(1L);
        expected.setType("expected");
        when(userPersistenceManager.updateRole(toBeUpdated)).thenReturn(expected);

        try {
            assertEquals(roleManagementController.updateRole(
                    RoleDTOHelper.fromEntity(toBeUpdated)),
                    RoleDTOHelper.fromEntity(expected)
            );
        } catch (BusinessException e) {
            fail("BussinessException thrown, shouldn't get here.");
        }
    }

    @Test
    public void updateRole_expectedBusinessException() {
        Role toBeUpdated = null;
        when(userPersistenceManager.updateRole(toBeUpdated)).thenReturn(null);
        try{
            roleManagementController.updateRole(RoleDTOHelper.fromEntity(toBeUpdated));
            fail("TEST_FAILED");
        } catch (BusinessException ex){
            assertEquals(ex.getExceptionCode(),ExceptionCode.ROLE_VALIDATION_EXCEPTION);
        }
    }




}