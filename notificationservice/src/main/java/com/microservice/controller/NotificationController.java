package com.microservice.controller;

import com.microservice.entity.Notification;
import com.microservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Контроллер для операций с уведомлениями")
@RestController
@Slf4j
@RequestMapping("/api/v1/notificationservice/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    @Operation(summary = "Получить извещение по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная операция",
            content = @Content(schema = @Schema(implementation = Notification.class))),
        @ApiResponse(responseCode = "404", description = "Извещение не найдено")
    })
    public Notification getNotification(@PathVariable UUID id) {
        log.info("Get notification with id {}", id);
        return notificationService.getNotificationById(id);
    }


    @GetMapping
    @Operation(summary = "Получить все извещения")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная операция",
            content = @Content(schema = @Schema(implementation = Notification.class))),
        @ApiResponse(responseCode = "404", description = "Извещения не найдены")
    })
    public List<Notification> getAllNotifications() {
        log.info("Get all notifications");
        return notificationService.getAllNotifications();
    }


    @DeleteMapping("/notification/{id}")
    @Operation(summary = "Удалить извещение по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешное удаление"),
        @ApiResponse(responseCode = "404", description = "Извещение не найдено")
    })
    public Notification deleteNotification(@PathVariable UUID id) {
        log.info("Delete notification with id {}", id);
        return notificationService.deleteNotificationById(id);
    }


    @DeleteMapping
    @Operation(summary = "Удалить все извещения")
    public void deleteAllNotifications() {
        log.info("Delete all notifications");
        notificationService.deleteAllNotifications();
    }
}
