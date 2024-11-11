package be.pxl.services.controller;

import be.pxl.services.domain.Notification;
import be.pxl.services.services.MessageSenderService;
import be.pxl.services.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notification/")
public class NotificationController {
    private final NotificationService notificationService;
    private final MessageSenderService messageSenderService;
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody Notification notification){
        log.info("Sending notification: {}", notification);
        notificationService.sendMessage(notification);
        messageSenderService.sendMessage(notification.getMessage());
    }
}
