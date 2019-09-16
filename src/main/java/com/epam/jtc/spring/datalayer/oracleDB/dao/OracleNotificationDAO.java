package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OracleNotificationDAO implements NotificationDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Notification> notificationRowMapper =
        new BeanPropertyRowMapper<Notification>() {
            @Override
            public Notification mapRow(ResultSet rs, int rowNum)
                throws SQLException {
                Notification notification = new Notification();
                
                notification.setId(rs.getInt(1));
                notification.setBookId(rs.getInt(2));
                notification.setSearchText(rs.getString(3));
                notification.setCategory(rs.getString(4));
                
                notification
                    .setType(NotificationTypes.valueOf(rs.getString(5)));
                notification.setDate(rs.getDate(6));
                
                return notification;
            }
        };
    
    @Override
    public List<Notification> getAllNotifications() {
        return jdbcTemplate
                   .query("select NOTIFICATIONS.id, book, search_text, " +
                              "category, NOTIFICATION_TYPES.type, \"date\"" +
                              "from NOTIFICATIONS\n" +
                              "inner join NOTIFICATION_TYPES on " +
                              "NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE",
                       notificationRowMapper);
    }
    
    @Override
    public List<Notification> getLastNotifications(int count) {
        return jdbcTemplate
                   .query("select * from (" +
                              "select NOTIFICATIONS.id, book, search_text, " +
                              "category, NOTIFICATION_TYPES.type, \"date\" " +
                              "from NOTIFICATIONS inner join NOTIFICATION_TYPES " +
                              "on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE " +
                              "order by \"date\" desc) where rownum <= ?",
                       notificationRowMapper, count);
    }
    
    @Override
    public void addNotification(int bookId, String searchText, String category,
                                NotificationTypes type) {
        jdbcTemplate
            .update("insert into NOTIFICATIONS " +
                        "(BOOK, SEARCH_TEXT, CATEGORY, TYPE) values " +
                        "(?, ?, ?, (select id from NOTIFICATION_TYPES " +
                        "where type = ?))",
                bookId, searchText, category, type.toString());
    }
}
