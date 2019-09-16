package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
        NotificationDAO dao =
                DAOFactory.getInstance(DataSourceType.ORACLE)
                        .getNotificationDAO();

        List<Notification> result = new ArrayList<>();

        System.out.println("Получаем книги");

        result = dao.getAllNotifications();

        System.out.println(result);
    }

}
