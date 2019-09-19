package com.epam.jtc.spring.datalayer;

import com.epam.jtc.spring.datalayer.oracleDB.OracleDAOFactory;
import org.springframework.stereotype.Component;

/**
 * Enum of supported DAO types
 */
@Component
public enum DataSourceType {
    /**
     * Oracle DB
     */
    ORACLE {
        public DAOFactory getDAOFactory() {
            return OracleDAOFactory.getInstance();
        }
    };
    
    /**
     * Gets instance of certain DAO factory
     *
     * @return {@link DAOFactory}
     */
    public abstract DAOFactory getDAOFactory();
}
