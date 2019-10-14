package helpClasses;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

/*@EnableJpaRepositories(basePackages = {
        "org.baeldung.repository",
        "org.baeldung.boot.repository"
})*/
@TestConfiguration
public class TestConfigurationUtils {
    
    public TestConfigurationUtils() {
        try {
            setUpDataSourceJNDI();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(JdbcTemplate.class);
    }
    
    /*@Bean("testBookDAO")
    public OracleBookDAO bookDAO() {
        return mock(OracleBookDAO.class);
    }*/
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        ds.setUsername("SYSTEM");
        ds.setPassword("SYSTEM");
        
        return ds;
    }
    
    public void setUpDataSourceJNDI() throws NamingException {
        
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        InitialContext ic = new InitialContext();
        
        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/jdbc");
        ic.createSubcontext("java:comp/env/jdbc/oracle");
        
        DriverManagerDataSource ds = new DriverManagerDataSource();
        
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        ds.setUsername("SYSTEM");
        ds.setPassword("SYSTEM");
        
        ic.rebind("java:comp/env/jdbc/oracle", ds);
    }
}
