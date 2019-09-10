package com.epam.jtc.spring.datalayer.oracle.dao;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;

import java.util.List;

public class OracleNotificationDAO implements NotificationDAO {


    @Override
    public List<Notification> getAllNotifications() {
        return null;
    }

    @Override
    public List<Notification> getLastNotifications(int count) {
        return null;
    }

    @Override
    public void addNotification(int bookId, String searchText, String category,
                                String type) {

    }
}
