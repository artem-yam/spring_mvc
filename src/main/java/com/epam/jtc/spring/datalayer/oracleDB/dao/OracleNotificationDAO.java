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

    private static final Logger loggerDAO = LogManager
            .getLogger(new Object() {
            }.getClass()
                    .getEnclosingClass());

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Notification> getAllNotifications() {
        return jdbcTemplate
                .query("select NOTIFICATIONS.id, book, search_text, " +
                                "category, NOTIFICATION_TYPES.type, \"DATE\"" +
                                "from NOTIFICATIONS " +
                                "inner join NOTIFICATION_TYPES on " +
                                "NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE " +
                                "order by \"DATE\"",
                        notificationRowMapper);
    }

    @Override
    public List<Notification> getLastNotifications(int count) {
        return jdbcTemplate
                .query("select * from (" +
                                "select NOTIFICATIONS.id, book, search_text, " +
                                "category, NOTIFICATION_TYPES.type, \"DATE\" " +
                                "from NOTIFICATIONS inner join NOTIFICATION_TYPES " +
                                "on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE " +
                                "order by \"DATE\" desc) where rownum <= ?",
                        notificationRowMapper, count);
    }

    @Override
    public int addNotification(int bookId, String searchText,
                               String category,
                               NotificationTypes type) {
        Notification notify = new Notification();
        notify.setBookId(bookId);
        notify.setSearchText(searchText);
        notify.setCategory(category);
        notify.setType(type);

        //loggerDAO.info("New notification = {}", notify);

        return jdbcTemplate
                .update("insert into NOTIFICATIONS " +
                                "(BOOK, SEARCH_TEXT, CATEGORY, TYPE) values " +
                                "(?, ?, ?, (select id from NOTIFICATION_TYPES " +
                                "where type = ?))",
                        bookId > 0 ? bookId : null, searchText, category,
                        type.toString());
    }
}
