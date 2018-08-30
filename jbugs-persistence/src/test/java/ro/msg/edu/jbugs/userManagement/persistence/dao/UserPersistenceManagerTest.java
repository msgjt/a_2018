package ro.msg.edu.jbugs.userManagement.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerTest {

    @InjectMocks
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private EntityManager em;


    @Before
    public void before() {
        List<User> users = userPersistenceManager.getAllUsers();
        //NamedQuery query = mock(NamedQuery.class);
        Query query = mock(Query.class);

    }

    @Test
    public void getAllUsers() {
        List<User> result = new ArrayList<User>() {{
            add(new User() {{
                id = 1L;
            }});
            add(new User() {{
                id = 2L;
            }});
            add(new User() {{
                id = 3L;
            }});
        }};


        TypedQuery query = mock(TypedQuery.class);
        when(em.createNamedQuery(User.GET_ALL_USERS, User.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(result);
    }

    @After
    public void after() {

    }

    @Test
    public void createUser() {

    }

    @Test
    public void updateUser() {

    }


    @Test
    public void getUserByUsername() {
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void createRole() {
    }

    @Test
    public void removeRole() {
    }

    @Test
    public void updateRole() {
    }

    @Test
    public void getRoleForId() {
    }

    @Test
    public void getAllRoles() {
    }

    @Test
    public void getUserByEmail() {
    }

    @Test
    public void getUsernamesLike() {
    }

    @Test
    public void getRoleByType() {
    }
}