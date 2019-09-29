package com.epam.jtc.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.jtc.spring")
public class SpringConfiguration implements WebMvcConfigurer {


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
}
