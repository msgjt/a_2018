package ro.msg.edu.jbugs.notificationManagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.shared.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.control.NotificationManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.userManagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationManagementControllerTest {


    @InjectMocks
    private NotificationManagementController notificationManagementController;

    @Mock
    NotificationPersistenceManager notificationPersistenceManager;
    @Mock
    UserPersistenceManager userPersistenceManager;


    @Test
    public void testCreateNotification_Success() {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setStatus("stat");
        notificationDTO.setURL("ss");

        notificationDTO.setMessage("ddd");
        notificationDTO.setType("ssss");

        Notification notification = new Notification();


        when(notificationPersistenceManager.add(NotificationDTOHelper.toEntity(notificationDTO)))
                .thenReturn(NotificationDTOHelper.toEntity(notificationDTO));


        try {
            NotificationDTO createNotif = notificationManagementController.createNotification(notificationDTO);
            assertEquals(notificationDTO.getMessage(), createNotif.getMessage());
            assertEquals(notificationDTO.getStatus(), notificationDTO.getStatus());

        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getNotificationByIdTest() {
      Notification notification = new Notification();
      notification.setURL("ss");
      notification.setMessage("ss");
      notification.setType("ss");
      notification.setId(1l);
      notification.setStatus("ss");


        when(notificationPersistenceManager.getById((long) 1)).thenReturn(Optional.of(notification));
        assertEquals(notification, notificationManagementController.getNotificationById((long) 1));
    }


    @Test
    public void getAllUnreadNotifications_expectedEmptyList() {

        when(notificationPersistenceManager.getNotificationsForUser(1l)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<NotificationDTO>(), notificationManagementController.getUnreadNotificationsForUser(1l));
    }


    @Test
    public void getAllReadNotifications_expectedEmptyList() {

        when(notificationPersistenceManager.getNotificationsForUser(1l)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<NotificationDTO>(), notificationManagementController.getReadNotificationsForUser(1l));
    }





    @Test
    public void getAllReadNotifications_expectedList() {
      Notification n1=new Notification();
      Notification n2= new Notification();
        n1.setStatus("read");
        n1.setURL("ss");
        n1.setMessage("ddd");
        n1.setType("ssss");

        n2.setStatus("read");
        n2.setURL("ss");
        n2.setMessage("ddd");
        n2.setType("ssss");

        List<Notification> notifications = new ArrayList<>(Arrays.asList(n1, n2));
        when(notificationPersistenceManager.getNotificationsForUser(1l)).thenReturn(notifications);

        List<Notification> actuals = notificationManagementController.getReadNotificationsForUser(1l)
                .stream()
                .map(NotificationDTOHelper::toEntity)
                .collect(Collectors.toList());
        assertEquals(actuals, notifications);
    }

    @Test
    public void getAllUnreadNotifications_expectedList() {
        Notification n1=new Notification();
        Notification n2= new Notification();
        n1.setStatus("not_read");
        n1.setURL("ss");
        n1.setMessage("ddd");
        n1.setType("ssss");

        n2.setStatus("not_read");
        n2.setURL("ss");
        n2.setMessage("ddd");
        n2.setType("ssss");

        List<Notification> notifications = new ArrayList<>(Arrays.asList(n1, n2));
        when(notificationPersistenceManager.getNotificationsForUser(1l)).thenReturn(notifications);

        List<Notification> actuals = notificationManagementController.getUnreadNotificationsForUser(1l)
                .stream()
                .map(NotificationDTOHelper::toEntity)
                .collect(Collectors.toList());
        assertEquals(actuals, notifications);
    }



    @Test
    public void assignUserToNotification() {

        User user=new User();
        user.setId(1l);

        Notification n=new Notification();
        n.setStatus("not_read");
        n.setURL("ss");
        n.setMessage("ddd");
        n.setType("ssss");
        n.setId(2l);

        when(notificationPersistenceManager.getNotificationWithUsers(2l)).thenReturn(Optional.of(n));
        when(userPersistenceManager.getUserById(1l)).thenReturn(Optional.of(user));
        when(notificationPersistenceManager.update(n)).thenReturn(n);
        assertEquals(n,notificationManagementController.assignUserToNotification(2l,1l));
    }


    @Test
    public void  sendNotification(){

        User u1 = new User();
        u1.setId((long) 6);
        u1.setFirstName("dorel");
        u1.setLastName("dorel");
        u1.setEmail("doreldorel@msggroup.com");
        u1.setPhoneNumber("1234567890");
        u1.setUsername("Dorelut");
        u1.setIsActive(true);
        u1.setRoles(new ArrayList<>());

        Notification n=new Notification();
        n.setStatus("not_read");
        n.setURL("ss");
        n.setMessage("ddd");
        n.setType("ssss");
        n.setId(2l);

        when(userPersistenceManager.getUserById(1l)).thenReturn(Optional.of(u1));
        notificationManagementController.sendNotification(n.getType(),n.getMessage(),n.getURL());
    }


}