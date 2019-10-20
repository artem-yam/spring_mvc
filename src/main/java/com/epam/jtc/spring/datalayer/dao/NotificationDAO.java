package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Notification;

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
     * @param notification notification to add
     * @return added notification
     */
    Notification addNotification(Notification notification);

}
