package com.epam.jtc.spring.datalayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CustomJdbcTemplate extends JdbcTemplate {
    
    @Autowired
    public CustomJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }
}
