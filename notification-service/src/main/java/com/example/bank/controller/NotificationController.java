package com.example.bank.controller;

import com.example.bank.dto.NotificationDto;
import com.example.bank.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@Api(value = "Notification Management", description = "Operations pertaining to notifications in Notification Management System")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new notification")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto createNotification(@RequestParam String message) {
        log.info("Received request to create notification with message: {}", message);
        NotificationDto createdNotification = notificationService.createNotification(message);
        log.info("Notification created successfully: {}", createdNotification);
        return createdNotification;
    }

    @GetMapping
    @ApiOperation(value = "Get a list of all notifications")
    public List<NotificationDto> getAllNotifications() {
        log.info("Received request to get all notifications");
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        log.info("Returned {} notifications", notifications.size());
        return notifications;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a notification by Id")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        log.info("Received request to get notification by Id: {}", id);
        Optional<NotificationDto> notification = notificationService.getNotificationById(id);
        return notification.map(value -> {
            log.info("Notification found: {}", value);
            return new ResponseEntity<>(value, HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Notification with Id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/timestamp")
    @ApiOperation(value = "Get notifications by timestamp")
    public List<NotificationDto> getNotificationsByTimestamp(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        log.info("Received request to get notifications by timestamp: startTime = {}, endTime = {}", startTime, endTime);
        List<NotificationDto> notifications = notificationService.getNotificationsByTimestamp(startTime, endTime);
        log.info("Returned {} notifications", notifications.size());
        return notifications;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a notification by Id")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.info("Received request to delete notification by Id: {}", id);
        notificationService.deleteNotification(id);
        log.info("Notification deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete all notifications")
    public ResponseEntity<Void> deleteAllNotifications() {
        log.info("Received request to delete all notifications");
        notificationService.deleteAllNotifications();
        log.info("All notifications deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
