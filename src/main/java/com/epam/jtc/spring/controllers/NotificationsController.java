package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for notifications
 */
@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    private static final Logger logger =
            LogManager.getLogger(NotificationsController.class);

    private NotificationDAO dao;

    /**
     * Constructor
     *
     * @param dao notifications dao
     */
    @Autowired
    public NotificationsController(NotificationDAO dao) {
        this.dao = dao;
    }

    /**
     * Gets all notifications from dao
     *
     * @return list of notifications
     */
    @GetMapping
    public List<Notification> getAllNotifications() {
        logger.debug("getAllNotifications method triggered");

        return dao.getAllNotifications();
    }

    /**
     * Adds the notification to dao
     *
     * @param newNote notification to add
     * @return added notification
     */
    @PostMapping
    public Notification addNotification(@RequestBody Notification newNote) {
        logger.info("Adding notification: {}", newNote);

        return dao.addNotification(newNote);
    }
}
