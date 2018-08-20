package ro.msg.edu.jbugs.bugManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.Optional;

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
    public void testCreateUser_Success(){


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

        try{
            BugDTO createBug = bugManagementController.createBug(bug);
            assertEquals(bug.getTitle(),createBug.getTitle());
            assertEquals(bug.getSeverity(),createBug.getSeverity());
            assertEquals(bug.getStatus(),createBug.getStatus());

        } catch (BusinessException e){
            fail("Should not reach this point");
        }
    }
}
