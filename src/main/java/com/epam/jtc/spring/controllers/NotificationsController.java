package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
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
    
    /**
     * logger for class
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    private NotificationDAO dao;
    
    @Autowired
    public NotificationsController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                  .getNotificationDAO();
    }
    
    /**
     * Gets all notifications from dao
     *
     * @return list of notifications
     */
    @GetMapping
    public List<Notification> getAllNotifications() {
        return dao.getAllNotifications();
    }
    
    /**
     * Adds the notification to dao
     *
     * @param newNote notification to add
     * @return some representation of successful adding
     */
    @PostMapping
    public Notification addNotification(@RequestBody Notification newNote) {
        return dao.addNotification(newNote.getBookId(), newNote.getContent(),
            newNote.getCategory(), newNote.getType());
    }
}
