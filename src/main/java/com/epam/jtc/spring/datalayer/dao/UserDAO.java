package com.epam.jtc.spring.datalayer.dao;

import java.sql.SQLException;

/**
 * DAO for users
 */
public interface UserDAO {

    void loginUser(String login, String password) throws SQLException;

    void logoutUser(String login) throws SQLException;
}
