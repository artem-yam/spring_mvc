package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dto.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    @RequestMapping(value = "/getAll")
    public List<Notification> getAllNotifications() {
        return DAOFactory.getInstance(DataSourceType.ORACLE)
                   .getNotificationDAO().getAllNotifications();
    }
    
    @RequestMapping(value = "/add")
    public void addNotification(@RequestBody Notification newNote) {
        logger.info("Adding new notify: {}", newNote);
        
        DAOFactory.getInstance(DataSourceType.ORACLE).getNotificationDAO()
            .addNotification(newNote.getBookId(), newNote.getSearchText(),
                newNote.getCategory(), newNote.getType());
    }
}
