package com.epam.jtc.spring.datalayer;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;

/**
 * Factory of DAO
 */
public interface DAOFactory {
    
    /**
     * Gets instance of certain DAOFactory
     *
     * @param type {@link DataSourceType}
     * @return {@link DAOFactory}
     */
    static DAOFactory getInstance(DataSourceType type) {
        return type.getDAOFactory();
    }
    
    /**
     * Books DAO getter
     *
     * @return {@link BookDAO}
     */
    BookDAO getBookDAO();
    
    /**
     * Tags DAO getter
     *
     * @return {@link TagDAO}
     */
    TagDAO getTagDAO();
    
    /**
     * Notifications DAO getter
     *
     * @return {@link NotificationDAO}
     */
    NotificationDAO getNotificationDAO();
}
