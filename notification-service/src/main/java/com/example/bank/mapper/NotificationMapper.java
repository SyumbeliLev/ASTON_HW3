package com.example.bank.mapper;

import com.example.bank.dto.NotificationDto;
import com.example.bank.model.Notification;
import lombok.experimental.UtilityClass;


@UtilityClass
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(notification.getMessage());
        notificationDto.setTimestamp(notification.getTimestamp());
        return notificationDto;
    }

    public Notification toEntity(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setMessage(notificationDto.getMessage());
        notification.setTimestamp(notificationDto.getTimestamp());
        return notification;
    }
}
