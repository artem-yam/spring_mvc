package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Component
public class NotificationRowMapper extends BeanPropertyRowMapper<Notification> {

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
        } catch (ParseException dateParseException) {
            logger.warn("Date parsing error", dateParseException);
        }

        return notification;
    }
}

