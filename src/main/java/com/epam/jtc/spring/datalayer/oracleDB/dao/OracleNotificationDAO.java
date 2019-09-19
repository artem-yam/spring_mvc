package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class OracleNotificationDAO implements NotificationDAO {
    
    /**
     * Logger for class
     */
    private static final Logger DAOLogger = LogManager
                                                .getLogger(new Object() {
                                                }.getClass()
                                                               .getEnclosingClass());
    
    /**
     * Row mapper for result sets from DB 'notifications' table
     */
    private static final RowMapper<Notification> notificationRowMapper =
        new BeanPropertyRowMapper<Notification>() {
            @Override
            public Notification mapRow(ResultSet rs, int rowNum)
                throws SQLException {
                Notification notification = new Notification();
                
                notification.setId(rs.getInt(1));
                notification.setBookId(rs.getInt(2));
                notification.setContent(rs.getString(3));
                notification.setCategory(rs.getString(4));
                
                notification
                    .setType(
                        NotificationTypes.valueOf(rs.getString(5)));
                
                SimpleDateFormat dateFormatter =
                    new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                
                String sqlDate = dateFormatter.format(rs.getDate(6));
                
                try {
                    notification.setDate(dateFormatter.parse(sqlDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                
                return notification;
            }
        };
    
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
    public int addNotification(int bookId, String content,
                               String category,
                               NotificationTypes type) {
        //loggerDAO.info("New notification = {}", notify);
        
        return jdbcTemplate
                   .update(ADD_NOTIFICATION_QUERY, bookId > 0 ? bookId : null,
                       content, category, type.toString());
    }
}
