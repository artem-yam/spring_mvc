package helpClasses;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*@EnableJpaRepositories(basePackages = {
        "org.baeldung.repository",
        "org.baeldung.boot.repository"
})*/
public class TestConfigurationUtils {

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
        //DriverManagerDataSource ds = mock(DriverManagerDataSource.class);

        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        ds.setUsername("SYSTEM");
        ds.setPassword("SYSTEM");

        /*ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("sa");*/


        ic.rebind("java:comp/env/jdbc/oracle", ds);
    }
}
