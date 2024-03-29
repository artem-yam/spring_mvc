package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import com.epam.jtc.spring.datalayer.dto.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Forms user entity from DB entry
 */
@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        User user = new User();
        user.setLogin(rs.getString(1));
        user.setPassword(rs.getString(2));

        return user;
    }
}

