package com.example.bank.service;

import com.example.bank.dto.NotificationDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    NotificationDto createNotification(String message);

    List<NotificationDto> getAllNotifications();

    Optional<NotificationDto> getNotificationById(Long id);

    List<NotificationDto> getNotificationsByTimestamp(LocalDateTime startTime, LocalDateTime endTime);

    void deleteNotification(Long id);

    void deleteAllNotifications();
}
