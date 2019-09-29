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
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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

    private static final String DAO_BEANS_PACKAGE =
            "com.epam.jtc.spring.datalayer";

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
                ctx.scan(DAO_BEANS_PACKAGE);
                ctx.refresh();

                instance = factory = ctx.getBean(OracleDAOFactory.class);
                instance.context = ctx;
            }
        }

        return factory;
    }

    /**
     * Oracle DB books DAO getter
     *
     * @return {@link OracleBookDAO}
     */
    @Override
    public BookDAO getBookDAO() {
        return context.getBean(OracleBookDAO.class, this);
    }

    /**
     * Oracle DB tags DAO getter
     *
     * @return {@link OracleTagDAO}
     */
    @Override
    public TagDAO getTagDAO() {
        return context.getBean(OracleTagDAO.class);
    }

    /**
     * Oracle DB notifications DAO getter
     *
     * @return {@link OracleNotificationDAO}
     */
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
