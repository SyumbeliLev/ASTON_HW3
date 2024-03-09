package com.example.bank.service;

import com.example.bank.model.Notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
     Notification createNotification(String message);

     List<Notification> getAllNotifications();

     Optional<Notification> getNotificationById(Long id);

     List<Notification> getNotificationsByTimestamp(LocalDateTime startTime, LocalDateTime endTime);
     void deleteNotification(Long id);

     void deleteAllNotifications();
}
