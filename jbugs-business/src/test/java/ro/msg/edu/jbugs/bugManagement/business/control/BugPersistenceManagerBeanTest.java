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
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    private BugValidator bugValidator;

    @Mock
    private User user;

    @Test
    public void testCreateBug_Success() {

        Date date = new Date();
        try {
            String target = "27-09-1997";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            date =  df.parse(target);
        } catch (ParseException e) {
            fail("Should not reach this point!");
        }

        BugDTO bug = new BugDTO();
        bug.setTitle("title");
        bug.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo");
        bug.setVersion("5.1");
        bug.setTargetDate(date);
        bug.setStatus("Open");
        bug.setFixedVersion("fVers");
        bug.setSeverity(Severity.MEDIUM);
       // bug.setCreatedBy(user);
      //  bug.setAssignedTo(user);

        when(bugPersistenceManager.createBug(BugDTOHelper.toEntity(bug)))
                .thenReturn(BugDTOHelper.toEntity(bug));

        try {
            BugDTO createBug = bugManagementController.createBug(bug);
            assertEquals(bug.getTitle(), createBug.getTitle());
            assertEquals(bug.getSeverity(), createBug.getSeverity());
            assertEquals("Open", createBug.getStatus());

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

        Date date = new Date();
        try {
            String target = "27-09-2018";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            date =  df.parse(target);
        } catch (ParseException e) {
            fail("Should not reach this point!");
        }

        b1.setAssignedTo(user);
        b1.setCreatedBy(user);
        b1.setSeverity(Severity.MEDIUM);
        b1.setFixedVersion("2.3");
        b1.setStatus("in progress");
        b1.setVersion("3.4");
        b1.setDescription("description");
        b1.setTitle("bug1");
        b1.setTargetDate(date);

        b2.setAssignedTo(user);
        b2.setCreatedBy(user);
        b2.setSeverity(Severity.MEDIUM);
        b2.setFixedVersion("2.3");
        b2.setStatus("in progress");
        b2.setVersion("3.4");
        b2.setDescription("description");
        b2.setTitle("bug1");
        b2.setTargetDate(date);

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
        bug.setSeverity(Severity.LVL2);
    //    bug.setStatus("progr");

        assertEquals(true, bugManagementController.isValidForCreation(BugDTOHelper.fromEntity(bug)));
    }
    */
}
