package com.example.bank.service;

import com.example.bank.model.Notification;
import com.example.bank.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    public Notification createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsByTimestamp(LocalDateTime startTime, LocalDateTime endTime) {
        return notificationRepository.findByTimestampBetween(startTime, endTime);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}
