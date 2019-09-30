package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;

public class Tester {
    
    public static void main(String[] args) {
        NotificationDAO dao =
            DAOFactory.getInstance(DataSourceType.ORACLE)
                .getNotificationDAO();
        
        Notification note =
            dao.addNotification(3, "5",
                null, NotificationTypes.RATING);
        
        System.out.println(note);
    }
    
}
