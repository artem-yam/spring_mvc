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
    
    BookDAO getBookDAO();
    
    TagDAO getTagDAO();
    
    NotificationDAO getNotificationDAO();
}
