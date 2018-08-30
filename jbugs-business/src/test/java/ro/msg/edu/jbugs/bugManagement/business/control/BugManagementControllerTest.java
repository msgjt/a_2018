package ro.msg.edu.jbugs.bugManagement.business.control;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugManagement.business.validator.BugValidator;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagement;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BugManagementControllerTest {

    @InjectMocks
    private BugManagementController bugManagementController;

    @Mock
    private BugPersistenceManager bugPersistenceManager;

    @Mock
    private BugValidator bugValidator;

    @Mock
    private User user;

    @Mock
    private UserManagement userManagement;

    @Test
    public void testCreateBug_Success() {

        BugDTO bugDTO = new BugDTO();
        bugDTO.setTitle("title");
        bugDTO.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo");
        bugDTO.setVersion("5.1");
        bugDTO.setTargetDate("1997-09-27");
        bugDTO.setStatus("Open");
        bugDTO.setSeverity("MEDIUM");
        bugDTO.setCreatedBy(UserDTOHelper.fromEntity(user));
        bugDTO.setAssignedTo(UserDTOHelper.fromEntity(user));

        Bug dummyBug = new Bug();
        dummyBug.setCreatedBy(new User());
        dummyBug.setAssignedTo(new User());

        when(bugPersistenceManager.createBug(BugDTOHelper.toEntity(bugDTO,dummyBug)))
                .thenReturn(BugDTOHelper.toEntity(bugDTO, dummyBug));
        when(userManagement.getOldUserFields(any(UserDTO.class)))
                .thenReturn(user);

        try {
            BugDTO createBug = bugManagementController.createBug(bugDTO);
            assertEquals(bugDTO.getTitle(), createBug.getTitle());
            assertEquals(bugDTO.getSeverity(), createBug.getSeverity());
            assertEquals("Open", createBug.getStatus());

        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getAllBugs_expectedEmptyList() {
        when(bugPersistenceManager.getAllBugs()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<BugDTO>(), bugManagementController.getAllBugs());
    }

    @Test
    public void getAllBugs_expectedList() {
        Bug b1 = new Bug();
        b1.setCreatedBy(new User());
        b1.setAssignedTo(new User());
        Bug b2 = new Bug();
        b2.setCreatedBy(new User());
        b2.setAssignedTo(new User());

        b1.setAssignedTo(user);
        b1.setCreatedBy(user);
        b1.setSeverity(Severity.MEDIUM);
        b1.setFixedVersion("2.3");
        b1.setStatus("in progress");
        b1.setVersion("3.4");
        b1.setDescription("description");
        b1.setTitle("bug1");
        b1.setTargetDate(LocalDate.parse("2018-02-13"));
        b2.setAssignedTo(user);
        b2.setCreatedBy(user);
        b2.setSeverity(Severity.MEDIUM);
        b2.setFixedVersion("2.3");
        b2.setStatus("in progress");
        b2.setVersion("3.4");
        b2.setDescription("description");
        b2.setTitle("bug1");
        b2.setTargetDate(LocalDate.parse("2018-02-13"));

        List<Bug> bugs = new ArrayList<>(Arrays.asList(b1, b2));
        when(bugPersistenceManager.getAllBugs()).thenReturn(bugs);

        List<Bug> actuals = bugManagementController.getAllBugs()
                .stream()
                .map(BugDTOHelper::toEntityOneParam)
                .collect(Collectors.toList());
        assertEquals(actuals, bugs);
    }
/*
    @Test
    public void getBugByIdTest() {
        Bug bug = new Bug();
        bug.setId((long) 8);


        when(bugPersistenceManager.getBugById((long) 8)).thenReturn(Optional.of(bug));
        assertEquals(bug, BugDTOHelper.toEntity(bugManagementController.getBugById((long) 8)));
    }

    @Test
    public void getBugByTitleTest() {
        Bug bug = new Bug();
        bug.setTitle("test");


        when(bugPersistenceManager.getBugByTitle("test")).thenReturn(Optional.of(bug));
        assertEquals(bug, BugDTOHelper.toEntity(bugManagementController.getBugByTitle("test")));
    }

    @Test
    public void isValidVersionTest_Success(){
        boolean boolTest;
        final Pattern VALID_VERSION_REGEX =
                Pattern.compile("^\\w+(\\.\\w*)*$", Pattern.CASE_INSENSITIVE);

        boolTest = VALID_VERSION_REGEX.matcher("1").find();
        assertTrue(boolTest);
        boolTest = VALID_VERSION_REGEX.matcher("1.1").find();
        assertTrue(boolTest);
        boolTest = VALID_VERSION_REGEX.matcher("v1.1").find();
        assertTrue(boolTest);
        boolTest = VALID_VERSION_REGEX.matcher("v5.a").find();
        assertTrue(boolTest);
        boolTest = VALID_VERSION_REGEX.matcher("v2.v8.22").find();
        assertTrue(boolTest);
    }

    /*
    @Test
    public void isValidForCreationTest_fail() {
        Bug bug = new Bug();
        assertEquals(false, bugManagementController.isValidForCreation(BugDTOHelper.fromEntity(bug)));
    }
*/
    /*
    @Test
    public void isValidForCreationTest_success() {
        Bug bug = new Bug();
        bug.setTitle("bug");
        bug.setAssignedTo(user);
        bug.setCreatedBy(user);
        bug.setDescription("des");
    //    bug.setTargetDate("12-06-2044");
        bug.setFixedVersion("2.0");
        bug.setVersion("2.1");
        bug.setString(String.LVL2);
    //    bug.setStatus("progr");

        assertEquals(true, bugManagementController.isValidForCreation(BugDTOHelper.fromEntity(bug)));
    }
    */
}
