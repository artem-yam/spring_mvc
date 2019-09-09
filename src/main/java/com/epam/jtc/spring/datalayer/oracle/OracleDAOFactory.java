package com.epam.jtc.spring.datalayer.oracle;

import com.epam.jtc.spring.datalayer.BookDAO;
import com.epam.jtc.spring.datalayer.DAOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Locale;

/**
 * Factory for oracle DB DAO
 */
public class OracleDAOFactory implements DAOFactory {
    /**
     * Logger
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    /**
     * JNDI data source name
     */
    private static final String DATA_SOURCE_NAME = "java:/comp/env/jdbc/MyDB";
    /**
     * Singleton class instance
     */
    private static volatile OracleDAOFactory instance;
    /**
     * Data source to connect DB
     */
    private DataSource dataSource;
    
    /**
     * Private constructor
     */
    private OracleDAOFactory() {
        logger.debug("New instance of {} created", this.getClass());
    }
    
    /**
     * Class singleton instance getter
     *
     * @return {@link OracleDAOFactory}
     */
    public static OracleDAOFactory getInstance() {
        OracleDAOFactory factory = instance;
        if (factory == null) {
            synchronized (OracleDAOFactory.class) {
                instance = factory = new OracleDAOFactory();
                factory.getDataSource();
            }
        }
        
        return factory;
    }
    
    /**
     * Data source getter
     */
    private void getDataSource() {
        Locale.setDefault(Locale.ENGLISH);
        
        Context ctx;
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(DATA_SOURCE_NAME);
            ctx.close();
        } catch (NamingException namingException) {
            logger.error("Error while receiving data source object",
                namingException);
        }
        
        logger.debug("Data source received {}", dataSource);
    }
    
    /**
     * Oracle DB employee DAO getter
     *
     * @return {@link OracleBookDAO}
     */
    @Override
    public BookDAO getEmployeeDAO() {
        return new OracleBookDAO(dataSource);
    }
}
