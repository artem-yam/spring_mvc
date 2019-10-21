package com.epam.jtc.spring.datalayer.oracleDB;

import com.epam.jtc.spring.datalayer.OracleJdbcTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;

@Component
public class OracleDataSource extends DriverManagerDataSource {

    private static final String DATA_SOURCE_JNDI_NAME =
            "java:comp/env/jdbc/oracle";

    private static final Logger logger =
            LogManager.getLogger(OracleJdbcTemplate.class);

    public OracleDataSource() {
        DriverManagerDataSource ds = null;
        try {
            ds = (DriverManagerDataSource) new JndiTemplate()
                    .lookup(DATA_SOURCE_JNDI_NAME);
        } catch (NamingException namingException) {
            logger.warn("Can't get DataSource from JNDI", namingException);
        }

        if (ds != null) {
            setUrl(ds.getUrl());
            setUsername(ds.getUsername());
            setPassword(ds.getPassword());
            setConnectionProperties(ds.getConnectionProperties());
        }
    }
}
