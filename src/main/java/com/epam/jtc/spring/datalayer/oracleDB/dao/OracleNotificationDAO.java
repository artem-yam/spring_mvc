package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Notification DAO for oracle DB
 */
@Component
public class OracleNotificationDAO implements NotificationDAO {
    
    /**
     * Logger for class
     */
    private static final Logger DAOLogger =
        LogManager.getLogger(OracleNotificationDAO.class);
    /**
     * Query to get all notifications
     */
    private static final String ALL_NOTIFICATIONS_QUERY =
        "select NOTIFICATIONS.id, book, content, category, " +
            "NOTIFICATION_TYPES.type, \"DATE\" from NOTIFICATIONS " +
            "inner join NOTIFICATION_TYPES " +
            "on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE order by \"DATE\"";
    /**
     * Query to add new notification
     */
    private static final String ADD_NOTIFICATION_QUERY =
        "insert into NOTIFICATIONS (BOOK, CONTENT, CATEGORY, TYPE) values " +
            "(?, ?, ?, (select id from NOTIFICATION_TYPES where type = ?))";
    
    /**
     * Query to get certain notification
     */
    private static final String GET_NOTIFICATION_QUERY =
        "select * from (select NOTIFICATIONS.id, book, content, category, " +
            "NOTIFICATION_TYPES.type, \"DATE\" from NOTIFICATIONS " +
            "inner join NOTIFICATION_TYPES on " +
            "NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE " +
            "where (book is null or book=?) and (content is null or content=?) " +
            "and (category is null or category=?) order by NOTIFICATIONS.id " +
            "desc) where rownum=1";
    
    /**
     * Row mapper for result sets from DB 'notifications' table
     */
    @Autowired
    private RowMapper<Notification> notificationRowMapper;
    
    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Notification> getAllNotifications() {
        return jdbcTemplate
                   .query(ALL_NOTIFICATIONS_QUERY, notificationRowMapper);
    }
    
    @Override
    @Transactional
    public Notification addNotification(int bookId, String content,
                                        String category,
                                        NotificationTypes type) {
        jdbcTemplate.update(ADD_NOTIFICATION_QUERY,
            bookId > 0 ? bookId : null, content, category, type.toString());
        
        return jdbcTemplate.queryForObject(GET_NOTIFICATION_QUERY,
            notificationRowMapper, bookId, content, category);
    }
}
