package ro.msg.edu.jbugs.bugManagement.persistence.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugsManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BugPersistenceManagerTest {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @InjectMocks
    private BugPersistenceManager bugPersistenceManager;

    @Mock
    private EntityManager em;

    @Test
    public void getAllBugs() {
        List<Bug> result = new ArrayList<Bug>() {{
            add(new Bug() {{
                id = 1L;
            }});
            add(new Bug() {{
                id = 2L;
            }});
            add(new Bug() {{
                id = 3L;
            }});
        }};


        TypedQuery query = mock(TypedQuery.class);
        when(em.createNamedQuery(Bug.GET_ALL_BUGS, Bug.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(result);
    }

    @Test
    public void createBug() {

        Bug bug = new Bug();
        bug.setId(1l);
        doNothing().when(em).persist(bug);
        doNothing().when(em).flush();

        assertEquals(bugPersistenceManager.createBug(bug), bug);
    }

    @Test
    public void updateBug() {
/*        Bug bug1=new Bug();
        Bug bug = new Bug();
        bug.setId(1l);
        bug.setTitle("title");
        bug.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo");
        bug.setVersion("5.1");
        bug.setTargetDate(LocalDate.now());
        bug.setStatus("Open");
        bug.setSeverity(Severity.MEDIUM);
        bug1.setId(1l);
        bug1.setStatus("InProgress");
        bug1.setId(1l);
        bug1.setTitle("title");
        bug1.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo");
        bug1.setVersion("5.1");
        bug1.setTargetDate(LocalDate.now());
        bug1.setStatus("Open");
        bug1.setSeverity(Severity.MEDIUM);
        bug.setFixedVersion("");
        bug1.setFixedVersion("");

        doNothing().when(em).persist(bug);
        doNothing().when(em).flush();

      bugPersistenceManager.updateBug(bug1);
  */
    }


    @Test
    public void getAllBugsForUser() {
        User user = new User();
        List<Bug> bugs = new ArrayList<>();
        Query query = mock(Query.class);
        when(em.createQuery("select n from Bug n where n.assignedTo = :user ")).thenReturn(query);
        when(query.getResultList()).thenReturn(bugs);
    }

    @Test
    public void getBugByTitle() {

        List<Bug> allb = new ArrayList<Bug>();
        Bug bug1 = new Bug();
        bug1.setTitle("titlee");
        bug1.setId(1l);
        Bug bug12 = new Bug();
        bug12.setTitle("alttitle");
        bug12.setId(2l);
        Bug bug3 = new Bug();
        bug3.setTitle("title");
        bug3.setId(3l);
        allb.add(bug1);
        allb.add(bug12);
        allb.add(bug3);
        List<Bug> result = new ArrayList<Bug>();
        result.add(bug1);
        result.add(bug3);

        TypedQuery query = mock(TypedQuery.class);
        String string = "title";
        when(em.createNamedQuery(Bug.GET_BUG_BY_TITLE, Bug.class)).thenReturn(query);
        when(query.setParameter("title", string)).thenReturn(query);
        when(query.getResultList()).thenReturn(result);

    }

}