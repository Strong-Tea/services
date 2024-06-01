package com.microservice;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.*;
import com.microservice.controller.NotificationController;
import com.microservice.entity.Notification;
import com.microservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = NotificationController.class)
@ExtendWith(MockitoExtension.class)
public class NotificationServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;


    private Notification createNotification() {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID());
        notification.setMessage("CREATED");
        notification.setTimestamp(LocalDateTime.now());
        notification.setOrderId(UUID.randomUUID());
        return notification;
    }

    @Test
    void testGetNotificationById() throws Exception {
        Notification notification = createNotification();
        UUID id = notification.getId();
        when(notificationService.getNotificationById(id)).thenReturn(notification);

        mockMvc.perform(get(
            "/api/v1/notificationservice/notifications/notification/{id}",
                id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(id.toString())));
    }


    @Test
    void testGetNotificationById_NotFound() throws Exception {
        UUID notificationId = UUID.randomUUID();

        mockMvc.perform(get("/notifications/{id}", notificationId))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllNotifications() throws Exception {
        Notification notification = createNotification();
        Notification notification2 = createNotification();
        notification2.setId(UUID.randomUUID());

        List<Notification> notifications = Arrays.asList(notification, notification2);
        when(notificationService.getAllNotifications()).thenReturn(notifications);

        mockMvc.perform(get("/api/v1/notificationservice/notifications"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(notification.getId().toString())))
            .andExpect(jsonPath("$[1].id", is(notification2.getId().toString())));
    }


    @Test
    void testDeleteNotificationById() throws Exception {
        UUID notificationId = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/notificationservice/notifications/notification/{id}", notificationId))
            .andExpect(status().isOk());
        verify(notificationService).deleteNotificationById(notificationId);
    }


    @Test
    void testDeleteAllNotifications() throws Exception {
        doNothing().when(notificationService).deleteAllNotifications();

        mockMvc.perform(delete("/api/v1/notificationservice/notifications"))
            .andExpect(status().isOk());

        verify(notificationService).deleteAllNotifications();
    }
}

