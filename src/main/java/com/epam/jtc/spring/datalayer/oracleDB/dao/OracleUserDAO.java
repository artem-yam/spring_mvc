package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * User DAO for oracle DB
 */
@Component
public class OracleUserDAO implements UserDAO {

    private static final Logger logger =
            LogManager.getLogger(OracleUserDAO.class);

    private static final String FIND_USER_QUERY =
            "select * from users where login=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<User> userRowMapper;

    @Override
    public User getUser(String login) {
        logger.debug("Getting user with login: \'{}\'", login);
        return jdbcTemplate
                .queryForObject(FIND_USER_QUERY, userRowMapper, login);
    }
}
