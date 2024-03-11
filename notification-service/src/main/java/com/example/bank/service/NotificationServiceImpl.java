package com.example.bank.service;

import com.example.bank.dto.NotificationDto;
import com.example.bank.mapper.NotificationMapper;
import com.example.bank.model.Notification;
import com.example.bank.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationDto createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());

        return NotificationMapper.toDto(notificationRepository.save(notification));
    }

    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll().stream().map(NotificationMapper::toDto).toList();
    }

    public Optional<NotificationDto> getNotificationById(Long id) {
        return notificationRepository.findById(id).map(NotificationMapper::toDto);
    }

    public List<NotificationDto> getNotificationsByTimestamp(LocalDateTime startTime, LocalDateTime endTime) {
        return notificationRepository.findByTimestampBetween(startTime, endTime).stream().map(NotificationMapper::toDto).toList();
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}
