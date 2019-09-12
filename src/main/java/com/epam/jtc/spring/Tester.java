package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    
    public static void main(String[] args) {
        /*DAOFactory daoFactory = DAOFactory.getInstance(DataSourceType.ORACLE);
        
        NotificationDAO dao;
        //dao = ctx.getBean(OracleBookDAO.class);
        
        dao = daoFactory.getNotificationDAO();
        
        List<Notification> result = new ArrayList<>();
        
        dao.addNotification(2, null, null, NotificationTypes.SEARCH);
        
        System.out.println(result);*/
    }
    
}
