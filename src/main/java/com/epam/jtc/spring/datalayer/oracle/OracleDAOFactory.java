package com.epam.jtc.spring.datalayer.oracle;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.oracle.dao.OracleBookDAO;
import com.epam.jtc.spring.datalayer.oracle.dao.OracleNotificationDAO;
import com.epam.jtc.spring.datalayer.oracle.dao.OracleTagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Factory for oracle DB DAO
 */
@Component
public class OracleDAOFactory implements DAOFactory {
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            }
        }

        return factory;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Oracle DB employee DAO getter
     *
     * @return {@link OracleBookDAO}
     */
    @Override
    public BookDAO getBookDAO() {
        return new OracleBookDAO(jdbcTemplate);
    }

    @Override
    public TagDAO getTagDAO() {
        return new OracleTagDAO();
    }

    @Override
    public NotificationDAO getNotificationDAO() {
        return new OracleNotificationDAO();
    }
}
