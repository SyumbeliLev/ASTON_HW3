package com.example.bank.repository;

import com.example.bank.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
}