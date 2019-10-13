package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * User DAO for oracle DB
 */
@Component
public class OracleUserDAO implements UserDAO {

    /**
     * Logger for class
     */
    private static final Logger logger =
            LogManager.getLogger(OracleUserDAO.class);

    /**
     * Query to get user by login
     */
    private static final String FIND_USER_QUERY =
            "select * from users where login=?";

    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Row mapper for result sets from DB 'users' table
     */
    @Autowired
    private RowMapper<User> userRowMapper;

    /**
     * Gets user by it's login
     *
     * @param login login
     * @return found user
     * @throws SQLException if user not found
     */
    @Override
    public User getUser(String login) {
        logger.debug("Getting user with login: \'{}\'", login);
        return jdbcTemplate
                .queryForObject(FIND_USER_QUERY, userRowMapper, login);
    }
}
