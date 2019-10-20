package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Notification;
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

    private static final Logger logger =
            LogManager.getLogger(OracleNotificationDAO.class);

    private static final String ALL_NOTIFICATIONS_QUERY =
            "select NOTIFICATIONS.id, book, content, category, " +
                    "NOTIFICATION_TYPES.type, \"DATE\" from NOTIFICATIONS " +
                    "inner join NOTIFICATION_TYPES " +
                    "on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE order by \"DATE\"";

    private static final String ADD_NOTIFICATION_QUERY =
            "insert into NOTIFICATIONS (BOOK, CONTENT, CATEGORY, TYPE) values " +
                    "(?, ?, ?, (select id from NOTIFICATION_TYPES where type = ?))";

    private static final String GET_NOTIFICATION_QUERY =
            "select * from (select NOTIFICATIONS.id, book, content, category, " +
                    "NOTIFICATION_TYPES.type, \"DATE\" from NOTIFICATIONS " +
                    "inner join NOTIFICATION_TYPES on " +
                    "NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE " +
                    "where (book is null or book=?) and (content is null or content=?) " +
                    "and (category is null or category=?) order by NOTIFICATIONS.id " +
                    "desc) where rownum=1";

    @Autowired
    private RowMapper<Notification> notificationRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Notification> getAllNotifications() {
        logger.debug("Getting all notifications");
        return jdbcTemplate
                .query(ALL_NOTIFICATIONS_QUERY, notificationRowMapper);
    }

    @Override
    @Transactional
    public Notification addNotification(Notification notification) {
        logger.debug("Adding notification: {}", notification);

        logger.debug("Successful add? {}",
                jdbcTemplate.update(ADD_NOTIFICATION_QUERY,
                        notification.getBookId() > 0 ?
                                notification.getBookId() : null,
                        notification.getContent(), notification.getCategory(),
                        notification.getType().toString()) == 1);

        return jdbcTemplate.queryForObject(GET_NOTIFICATION_QUERY,
                notificationRowMapper, notification.getBookId(),
                notification.getContent(), notification.getCategory());
    }
}
