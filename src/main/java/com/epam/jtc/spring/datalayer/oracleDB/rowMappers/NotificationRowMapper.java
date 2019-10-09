package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Forms notification entity from DB entry
 */
@Component
public class NotificationRowMapper implements RowMapper<Notification> {
    
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
        
        return notification;
    }
}

