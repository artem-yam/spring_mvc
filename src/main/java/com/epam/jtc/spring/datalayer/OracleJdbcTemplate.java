package com.epam.jtc.spring.datalayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * JDBC template
 */
@Component("oracleJdbcTemplate")
public class OracleJdbcTemplate extends JdbcTemplate {

    private static final String DATA_SOURCE_JNDI_NAME =
            "java:comp/env/jdbc/oracle";

    private static final Logger logger =
            LogManager.getLogger(OracleJdbcTemplate.class);

    public OracleJdbcTemplate() {
        super(new DriverManagerDataSource());
    }

    @Autowired
    public OracleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Bean
    public static DataSource receiveDataSource() throws NamingException {
        DataSource ds = null;
        try {
            ds = (DataSource) new JndiTemplate().lookup(DATA_SOURCE_JNDI_NAME);
        } catch (NamingException namingException) {
            logger.warn("Can't get DataSource from JNDI", namingException);
        }

        return ds;
    }


}

