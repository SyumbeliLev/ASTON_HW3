import com.example.bank.controller.NotificationController;
import com.example.bank.dto.NotificationDto;
import com.example.bank.service.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationDto notificationDto;

    @Before
    public void setUp() {
        notificationDto = new NotificationDto();
        notificationDto.setMessage("Test notification");
    }

    @Test
    public void testCreateNotification() {
        when(notificationService.createNotification(any(String.class))).thenReturn(notificationDto);

        NotificationDto createdNotification = notificationController.createNotification("Test notification");

        assertNotNull(createdNotification);
        assertEquals(notificationDto, createdNotification);
    }

    @Test
    public void testGetAllNotifications() {
        List<NotificationDto> notificationList = new ArrayList<>();
        notificationList.add(notificationDto);
        when(notificationService.getAllNotifications()).thenReturn(notificationList);

        List<NotificationDto> retrievedNotifications = notificationController.getAllNotifications();

        assertNotNull(retrievedNotifications);
        assertEquals(notificationList, retrievedNotifications);
    }

    @Test
    public void testGetNotificationById() {
        when(notificationService.getNotificationById(1L)).thenReturn(Optional.of(notificationDto));

        ResponseEntity<NotificationDto> response = notificationController.getNotificationById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationDto, response.getBody());
    }

    @Test
    public void testGetNotificationById_NotFound() {
        when(notificationService.getNotificationById(1L)).thenReturn(Optional.empty());

        ResponseEntity<NotificationDto> response = notificationController.getNotificationById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNotificationsByTimestamp() {
        List<NotificationDto> notificationList = new ArrayList<>();
        notificationList.add(notificationDto);
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        when(notificationService.getNotificationsByTimestamp(startTime, endTime)).thenReturn(notificationList);

        List<NotificationDto> retrievedNotifications = notificationController.getNotificationsByTimestamp(startTime, endTime);

        assertNotNull(retrievedNotifications);
        assertEquals(notificationList, retrievedNotifications);
    }

    @Test
    public void testDeleteNotification() {
        ResponseEntity<Void> response = notificationController.deleteNotification(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificationService, times(1)).deleteNotification(1L);
    }

    @Test
    public void testDeleteAllNotifications() {
        ResponseEntity<Void> response = notificationController.deleteAllNotifications();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificationService, times(1)).deleteAllNotifications();
    }
}