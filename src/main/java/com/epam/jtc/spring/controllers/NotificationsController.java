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
        List<Notification> notes = DAOFactory.getInstance(DataSourceType.ORACLE)
                                       .getNotificationDAO()
                                       .getAllNotifications();
        
        logger.info("Notes: {}", notes);
        
        return notes;
    }
    
    /*public List<Book> searchBooks(String searchText) {
        return null;
    }*/
    
    @RequestMapping(value = "/add")
    public void addNotification(@RequestBody Notification newNote) {
        /*logger.info("NewBook = {}", newBook);
        if (newBook.getImagePath().contains(",")) {
            newBook.setImagePath(newBook.getImagePath()
                    .substring(newBook.getImagePath().indexOf(',') + 1));
        }

        DAOFactory.getInstance(DataSourceType.ORACLE).getBookDAO()
                .addBook(newBook.getTitle(), newBook.getAuthor(),
                        newBook.getImagePath());*/
    }
}
