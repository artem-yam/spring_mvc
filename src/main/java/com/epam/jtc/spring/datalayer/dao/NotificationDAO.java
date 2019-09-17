package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;

import java.util.List;

public interface NotificationDAO {

    List<Notification> getAllNotifications();

    List<Notification> getLastNotifications(int count);

    int addNotification(int bookId, String content, String category,
                        NotificationTypes type);

}
