package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.User;

/**
 * DAO for users
 */
public interface UserDAO {
    
    /**
     * Gets user by it's login
     *
     * @param login login
     * @return found user
     */
    User getUser(String login);
}
