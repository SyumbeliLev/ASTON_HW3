import com.example.bank.dto.NotificationDto;
import com.example.bank.model.Notification;
import com.example.bank.repository.NotificationRepository;
import com.example.bank.service.NotificationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    public void testCreateNotification() {
        Notification notification = new Notification();
        notification.setMessage("Test message");
        notification.setTimestamp(LocalDateTime.now());
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        NotificationDto createdNotification = notificationService.createNotification("Test message");

        assertNotNull(createdNotification);
        assertEquals("Test message", createdNotification.getMessage());
    }

    @Test
    public void testGetAllNotifications() {
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification());
        when(notificationRepository.findAll()).thenReturn(notificationList);

        List<NotificationDto> retrievedNotifications = notificationService.getAllNotifications();

        assertNotNull(retrievedNotifications);
        assertEquals(1, retrievedNotifications.size());
    }

    @Test
    public void testGetNotificationById() {
        Long id = 1L;
        Notification notification = new Notification();
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));

        Optional<NotificationDto> retrievedNotification = notificationService.getNotificationById(id);

        assertNotNull(retrievedNotification);
        assertEquals(notification.getMessage(), retrievedNotification.get().getMessage());
    }

    @Test
    public void testGetNotificationsByTimestamp() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification());
        when(notificationRepository.findByTimestampBetween(startTime, endTime)).thenReturn(notificationList);

        List<NotificationDto> retrievedNotifications = notificationService.getNotificationsByTimestamp(startTime, endTime);

        assertNotNull(retrievedNotifications);
        assertEquals(1, retrievedNotifications.size());
    }

    @Test
    public void testDeleteNotification() {
        notificationService.deleteNotification(1L);

        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllNotifications() {
        notificationService.deleteAllNotifications();

        verify(notificationRepository, times(1)).deleteAll();
    }
}