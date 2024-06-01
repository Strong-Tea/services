package com.microservice.service;

import com.microservice.entity.Notification;
import com.microservice.exception.NotFoundException;
import com.microservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Notification with id: %s was not found", id)
            ));
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }


    public Notification deleteNotificationById(UUID id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()) {
            throw new NotFoundException(String.format("Notification with id: %s was not found", id));
        }
        notificationRepository.deleteById(id);
        return notification.get();
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}