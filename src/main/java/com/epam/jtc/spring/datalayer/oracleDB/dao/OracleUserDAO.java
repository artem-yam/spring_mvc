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
    private static final String DIFFERENT_PASSWORDS_MESSAGE =
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
    private User getUser(String login) throws SQLException {
        User user = jdbcTemplate
                        .queryForObject(FIND_USER_QUERY, userRowMapper, login);
        
        if (user == null) {
            // юзер не существует
            throw new SQLException(NULL_USER_MESSAGE);
        }
        return user;
    }
    
    @Override
    public void loginUser(String login, String password) throws SQLException {
        //DAOLogger.info("Start login for user: {} {}", login, password);
        
        User user = getUser(login);
        
        //DAOLogger.info("Received user to login: {}", user);
        
        if (user.isOnline()) {
            // уже онлайн
            DAOLogger.info("User already online");
            throw new SQLException(USER_ALREADY_ONLINE_MESSAGE);
        } else if (!user.getPassword().equals(password)) {
            // неправильный пароль
            DAOLogger.info("Wrong user password");
            throw new SQLException(DIFFERENT_PASSWORDS_MESSAGE);
        } else {
            //ставим онлайн
            DAOLogger.info("Updating user status");
            jdbcTemplate.update(SET_ONLINE_QUERY, login);
            DAOLogger.info("User logged in");
        }
    }
    
    @Override
    public void logoutUser(String login) throws SQLException {
        DAOLogger.info("Start logout for user: {} ", login);
        
        User user = getUser(login);
        
        DAOLogger.info("Received user to logout: {}", user);
        
        if (user.isOnline()) {
            // ставим офлайн
            jdbcTemplate.update(SET_OFFLINE_QUERY, login);
        }
        
        DAOLogger.info("User logged OUT");
    }
}
