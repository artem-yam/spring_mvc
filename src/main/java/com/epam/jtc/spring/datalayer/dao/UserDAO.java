package com.epam.jtc.spring.datalayer.dao;

import java.sql.SQLException;

/**
 * DAO for users
 */
public interface UserDAO {
    
    /**
     * Perform user login
     *
     * @param login    user's login
     * @param password user's password
     * @throws SQLException some exception while user login
     */
    void loginUser(String login, String password) throws SQLException;
    
    /**
     * Perform user logout
     *
     * @param login user's login
     * @throws SQLException some exception while user logout
     */
    void logoutUser(String login) throws SQLException;
}
