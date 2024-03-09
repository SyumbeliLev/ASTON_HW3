package com.example.bank.controller;

import com.example.bank.model.Notification;
import com.example.bank.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestParam String message) {
        Notification newNotification = notificationService.createNotification(message);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/timestamp")
    public List<Notification> getNotificationsByTimestamp(@RequestParam LocalDateTime startTime,
                                                          @RequestParam LocalDateTime endTime) {
        return notificationService.getNotificationsByTimestamp(startTime, endTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllNotifications() {
        notificationService.deleteAllNotifications();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}