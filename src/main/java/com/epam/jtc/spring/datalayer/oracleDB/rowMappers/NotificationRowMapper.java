package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleNotificationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Forms notification entity from DB entry
 */
@Component
public class NotificationRowMapper extends BeanPropertyRowMapper<Notification> {
    
    private static final Logger DAOLogger =
        LogManager.getLogger(OracleNotificationDAO.class);
    
    @Override
    public Notification mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        Notification notification = new Notification();
        
        notification.setId(rs.getInt(1));
        notification.setBookId(rs.getInt(2));
        notification.setContent(rs.getString(3));
        notification.setCategory(rs.getString(4));
        
        notification.setType(NotificationTypes.valueOf(rs.getString(5)));
        
        notification.setDate(rs.getTimestamp(6));
        
        DAOLogger.info("Notification: {}", notification);
        
        return notification;
    }
}

