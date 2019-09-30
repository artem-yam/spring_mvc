package com.epam.jtc.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.jtc.spring")
public class SpringConfiguration implements WebMvcConfigurer {

    private static final String DATA_SOURCE_JNDI_NAME =
            "java:comp/env/jdbc/oracle";

    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass()
                    .getEnclosingClass());


    /*@Bean("oracleDataSource")
    public DataSource getOracleDataSource() {
        DataSource ds = null;
        try {
            ds = (DataSource) new JndiTemplate()
                    .lookup("java:comp/env/jdbc/oracle");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return ds;
    }*/

    /*@Bean("configOracleJDBC")
    public JdbcTemplate OracleJdbcTemplate() {
        DataSource ds = null;
        try {
            ds = (DataSource) new JndiTemplate().lookup(DATA_SOURCE_JNDI_NAME);
        } catch (NamingException namingException) {
            logger.warn("Can't get DataSource from JNDI", namingException);
        }

        return new JdbcTemplate(ds);
    }*/

    /*@Bean
    public DataSourceType getOracleDataSourceType() {
        return DataSourceType.ORACLE;
    }*/
}
