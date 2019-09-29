package com.epam.jtc.spring.datalayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * JDBC template
 */

@Component
public class OracleJdbcTemplate extends JdbcTemplate {

    private static final String DATA_SOURCE_JNDI_NAME =
            "java:comp/env/jdbc/oracle";

    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass()
                    .getEnclosingClass());


    /*@Autowired
    public OracleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }*/


    public OracleJdbcTemplate() {
        super();

        DataSource ds = null;
        try {
            ds = (DataSource) new JndiTemplate().lookup(DATA_SOURCE_JNDI_NAME);
        } catch (NamingException namingException) {
            logger.warn("Can't get DataSource from JNDI", namingException);
        }

        this.setDataSource(ds);
    }
}

