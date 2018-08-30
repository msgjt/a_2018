package ro.msg.edu.jbugs.userManagement.business.control;


import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PermissionManagementControllerTest {
/*
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
    }*/
}