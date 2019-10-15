package helpClasses;

import com.epam.jtc.spring.controllers.BooksController;
import com.epam.jtc.spring.datalayer.OracleJdbcTemplate;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        return mock(OracleJdbcTemplate.class);
    }

    private BookDAO getTestingBookDAO() throws Exception {
        OracleBookDAO dao = mock(OracleBookDAO.class);

        when(dao.getAllBooks()).thenReturn(Arrays.asList(new Book()));

        when(dao.addBook("Test title", "Test author", new byte[0]))
                .thenReturn(new Book("Test title", "Test author"));

        when(dao.addBook("Test title", "Test author", new byte[0]))
                .thenReturn(new Book("Test title", "Test author"));

        Book bookForRatingChange = new Book();
        bookForRatingChange.setId(1);
        bookForRatingChange.setRating(0);

        when(dao.changeRating(1, 0))
                .thenReturn(bookForRatingChange);

        return dao;
    }

    @Bean
    public BooksController booksController() throws Exception {
        return new BooksController(getTestingBookDAO());
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
