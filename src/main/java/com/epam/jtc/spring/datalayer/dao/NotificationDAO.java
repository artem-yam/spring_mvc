package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;

import java.util.List;

/**
 * DAO for notifications
 */
public interface NotificationDAO {
    
    /**
     * Gets all notifications
     *
     * @return list of notifications
     */
    List<Notification> getAllNotifications();
    
    /**
     * Adds new notification
     *
     * @param bookId   id of related book
     * @param content  content of the notification
     * @param category category of the notification
     * @param type     type of the notification
     * @return added notification
     */
    Notification addNotification(int bookId, String content, String category,
                                 NotificationTypes type);
    
}
