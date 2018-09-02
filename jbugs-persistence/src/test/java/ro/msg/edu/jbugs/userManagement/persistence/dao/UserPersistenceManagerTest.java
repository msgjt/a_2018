package ro.msg.edu.jbugs.userManagement.persistence.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import ro.msg.edu.jbugs.bugsManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerTest {

    @InjectMocks
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private EntityManager em;

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


    @Test
    public void createUser() {
        User user = new User();
        user.setId(1l);
        doNothing().when(em).persist(user);
        doNothing().when(em).flush();

        assertEquals(userPersistenceManager.createUser(user), user);
    }

    @Test
    public void updateUser() {

    }


    @Test
    public void getUserByUsername() {

        User user = new User();
        user.setId(1l);
        user.setUsername("ss");
        TypedQuery query = mock(TypedQuery.class);
        when(em.createNamedQuery(User.GET_USER_BY_USERNAME, User.class)).thenReturn(query);
        when(query.setParameter("username", user.getUsername())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);
    }

    @Test
    public void getUserById() {
        User user = new User();
        user.setId(1l);

        TypedQuery query = mock(TypedQuery.class);
        when(em.createNamedQuery(User.GET_USER_BY_ID, User.class)).thenReturn(query);
        when(query.setParameter("id", user.getId())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(user);
    }

    @Test
    public void createRole() {
        Role role = new Role();
        role.setId(1l);
        doNothing().when(em).persist(role);
        doNothing().when(em).flush();

        assertEquals(userPersistenceManager.createRole(role), role);
    }

    @Test
    public void removeRole() {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1l);
        roles.add(role);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                roles.remove(role);
                return null;
            }
        }).when(em).remove(role);
        doNothing().when(em).flush();

        userPersistenceManager.removeRole(role);

        assertEquals(0, roles.size());
    }

    @Test
    public void updateRole() {
        Role role= new Role();
        role.setId(1l);
        Long id=1l;
        Query query = mock(Query.class);
        when(em.createQuery("SELECT r FROM Role r WHERE r.id=" + id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(role);
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