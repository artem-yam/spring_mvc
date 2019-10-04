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
    private static final Logger DAOLogger =
        LogManager.getLogger(OracleUserDAO.class);
    
    private static final String NULL_USER_MESSAGE =
        "User with this login doesn't exist!";
    private static final String USER_ALREADY_ONLINE_MESSAGE =
        "The user is already online!";
    private static final String DIFFERENT_PASS_MESSAGE =
        "The password doesn't correspond!";
    
    /**
     * Query to get all books
     */
    private static final String FIND_USER_QUERY =
        "select * from users where login=?";
    
    private static final String SET_ONLINE_QUERY =
        "update users set is_online=1 where login=?";
    
    private static final String SET_OFFLINE_QUERY =
        "update users set is_online=0 where login=?";
    
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
        return jdbcTemplate
                   .queryForObject(FIND_USER_QUERY, userRowMapper, login);
    }
}
