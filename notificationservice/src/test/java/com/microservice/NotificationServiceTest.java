package com.microservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.microservice.entity.Notification;
import com.microservice.exception.NotFoundException;
import com.microservice.repository.NotificationRepository;
import com.microservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private UUID notificationId;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notificationId = UUID.randomUUID();
        notification = new Notification();
        notification.setId(notificationId);
    }

    @Test
    void testGetNotificationById_NotificationExists() {
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Notification foundNotification = notificationService.getNotificationById(notificationId);

        assertEquals(notification, foundNotification);
        verify(notificationRepository).findById(notificationId);
    }

    @Test
    void testGetNotificationById_NotificationDoesNotExist() {
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> notificationService.getNotificationById(notificationId));
        verify(notificationRepository).findById(notificationId);
    }

    @Test
    void testGetAllNotifications() {
        List<Notification> notifications = Arrays.asList(notification, new Notification());
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> foundNotifications = notificationService.getAllNotifications();

        assertEquals(notifications, foundNotifications);
        verify(notificationRepository).findAll();
    }

    @Test
    void testDeleteNotificationById_NotificationExists() {
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Notification deletedNotification = notificationService.deleteNotificationById(notificationId);

        assertEquals(notification, deletedNotification);
        verify(notificationRepository).deleteById(notificationId);
    }

    @Test
    void testDeleteNotificationById_NotificationDoesNotExist() {
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> notificationService.deleteNotificationById(notificationId));
        verify(notificationRepository, never()).deleteById(notificationId);
    }

    @Test
    void testDeleteAllNotifications() {
        notificationService.deleteAllNotifications();

        verify(notificationRepository).deleteAll();
    }
}

