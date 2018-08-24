package ro.msg.edu.jbugs.userManagement.business.control;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.userManagement.business.dto.PermissionDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.dto.RoleDTO;
import ro.msg.edu.jbugs.userManagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PermissionManagementControllerTest {

    @InjectMocks
    private PermissionManagementController permissionManagement;

    @Mock
    private PermissionPersistenceManager permissionPersistenceManager;

    @Test
    public void getAllPermissions_expectedList() {
        Permission p1 = new Permission();
        p1.setId(1L);
        p1.setType("CAN");
        p1.setDescription("can do smth");
        List<Permission> expected = new ArrayList<>(Arrays.asList(p1));
        when(permissionPersistenceManager.getAllPermissions()).thenReturn(expected);

        List<Permission> actual = permissionManagement.getAllPermissions()
                .stream()
                .map(PermissionDTOHelper::toEntity)
                .collect(Collectors.toList());

        assertEquals(actual,expected);
    }

    @Test
    public void getAllPermissions_expectedEmptyList() {
        when(permissionPersistenceManager.getAllPermissions()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(),permissionManagement.getAllPermissions());
    }
}