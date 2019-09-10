package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Notification;

import java.util.List;

public interface NotificationDAO {

    List<Notification> getAllNotifications();

    List<Notification> getLastNotifications(int count);

    void addNotification(int bookId, String searchText, String category,
                         String type);

}
