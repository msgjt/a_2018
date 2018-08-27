package ro.msg.edu.jbugs.bugManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BugPersistenceManagerBeanTest {

    @InjectMocks
    private BugManagementController bugManagementController;

    @Mock
    private BugPersistenceManager bugPersistenceManager;

    @Mock
    private User user;

    @Test
    public void testCreateBug_Success() {


        BugDTO bug = new BugDTO();
        bug.setTitle("title");
        bug.setDescription("descript");
        bug.setVersion("vers");
        bug.setTargetDate("2020-05-11");
        bug.setStatus("1");
        bug.setFixedVersion("fVers");
        bug.setSeverity(Severity.LVL2);
        bug.setCreatedBy(user);
        bug.setAssignedTo(user);

        try {
            BugDTO createBug = bugManagementController.createBug(bug);
            assertEquals(bug.getTitle(), createBug.getTitle());
            assertEquals(bug.getSeverity(), createBug.getSeverity());
            assertEquals(bug.getStatus(), createBug.getStatus());

        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getAllBugs_expectedNull() {
        when(bugPersistenceManager.getAllBugs()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<BugDTO>(), bugManagementController.getAllBugs());
    }

    @Test
    public void getAllBugs_expectedList() {
        Bug b1 = new Bug();
        Bug b2 = new Bug();


        b1.setAssignedTo(user);
        b1.setCreatedBy(user);
        b1.setSeverity(Severity.LVL2);
        b1.setFixedVersion("2.3");
        b1.setStatus("in progress");
        b1.setVersion("3.4");
        b1.setDescription("description");
        b1.setTitle("bug1");
        b1.setTargetDate("12-02-2019");

        b2.setAssignedTo(user);
        b2.setCreatedBy(user);
        b2.setSeverity(Severity.LVL2);
        b2.setFixedVersion("2.3");
        b2.setStatus("in progress");
        b2.setVersion("3.4");
        b2.setDescription("description");
        b2.setTitle("bug1");
        b2.setTargetDate("12-02-2019");

        List<Bug> bugs = new ArrayList<>(Arrays.asList(b1, b2));
        when(bugPersistenceManager.getAllBugs()).thenReturn(bugs);

        List<Bug> actuals = bugManagementController.getAllBugs()
                .stream()
                .map(BugDTOHelper::toEntity)
                .collect(Collectors.toList());
        assertEquals(actuals, bugs);
    }

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
        bug.setTargetDate("12-06-2044");
        bug.setFixedVersion("2.0");
        bug.setVersion("2.1");
        bug.setSeverity(Severity.LVL2);
        bug.setStatus("progr");

        assertEquals(true, bugManagementController.isValidForCreation(BugDTOHelper.fromEntity(bug)));
    }
    */
}
