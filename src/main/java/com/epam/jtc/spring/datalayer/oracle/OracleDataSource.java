package com.epam.jtc.spring.datalayer.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class OracleDataSource extends DriverManagerDataSource {
    
    //@Autowired
    public OracleDataSource() {
        setDriverClassName("oracle.jdbc.driver.OracleDriver");
        setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        setUsername("SYSTEM");
        setPassword("SYSTEM");
    }
}
