package com.epam.jtc.spring.datalayer.oracleDB;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleNotificationDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleTagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Factory for oracleDB DB DAO
 */
@Component
public class OracleDAOFactory implements DAOFactory, AutoCloseable {
    /**
     * Logger
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    /**
     * Singleton class instance
     */
    private static volatile OracleDAOFactory instance;
    
    /**
     * Spring application context
     */
    private ConfigurableApplicationContext context;
    
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
                AnnotationConfigApplicationContext ctx =
                    new AnnotationConfigApplicationContext();
                ctx.scan("com.epam.jtc.spring.datalayer");
                ctx.refresh();
                
                instance = factory = ctx.getBean(OracleDAOFactory.class);
                instance.context = ctx;
            }
        }
        
        return factory;
    }
    
    /**
     * Oracle DB employee DAO getter
     *
     * @return {@link OracleBookDAO}
     */
    @Override
    public BookDAO getBookDAO() {
        return context.getBean(OracleBookDAO.class);
    }
    
    @Override
    public TagDAO getTagDAO() {
        return context.getBean(OracleTagDAO.class);
    }
    
    @Override
    public NotificationDAO getNotificationDAO() {
        return context.getBean(OracleNotificationDAO.class);
    }
    
    @Override
    public void close() {
        if (context != null) {
            context.close();
        }
    }
}
