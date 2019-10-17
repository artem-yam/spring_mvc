package helpClasses;

import com.epam.jtc.spring.controllers.BooksController;
import com.epam.jtc.spring.controllers.NotificationsController;
import com.epam.jtc.spring.controllers.TagsController;
import com.epam.jtc.spring.controllers.UsersController;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;
import com.epam.jtc.spring.datalayer.dto.User;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleNotificationDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleTagDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleUserDAO;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfigurationUtils {

    public TestConfigurationUtils() {
        BasicConfigurator.configure();
        /*try {
            setUpDataSourceJNDI();
        } catch (NamingException e) {
            e.printStackTrace();
        }*/
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    private BookDAO getTestingBookDAO() throws Exception {
        OracleBookDAO dao = mock(OracleBookDAO.class);

        when(dao.getAllBooks()).thenReturn(Arrays.asList(new Book()));

        when(dao.addBook("Test title", "Test author", new byte[0]))
                .thenReturn(new Book("Test title", "Test author"));

        when(dao.addBook("Test title", "Test author", new byte[0]))
                .thenReturn(new Book("Test title", "Test author"));

        Book bookForUpdate = new Book();
        bookForUpdate.setId(1);
        bookForUpdate.setRating(0);

        when(dao.updateBook(1, bookForUpdate))
                .thenReturn(bookForUpdate);

        when(dao.getBookImage(1))
                .thenReturn(new byte[0]);

        return dao;
    }

    private NotificationDAO getTestingNotificationDAO() throws Exception {
        OracleNotificationDAO dao = mock(OracleNotificationDAO.class);

        when(dao.getAllNotifications())
                .thenReturn(Arrays.asList(new Notification()));

        when(dao.addNotification(0, null, null, NotificationTypes.ADD_BOOK))
                .thenReturn(new Notification());

        return dao;
    }

    private TagDAO getTestingTagDAO() throws Exception {
        OracleTagDAO dao = mock(OracleTagDAO.class);

        List<String> testTags = Arrays.asList("Test tag");

        when(dao.getAllTags())
                .thenReturn(testTags);

        when(dao.addTagToBook(1, "Test tag"))
                .thenReturn(testTags);

        testTags = new ArrayList<>();

        when(dao.unbindTag(1, "Test tag"))
                .thenReturn(testTags);

        return dao;
    }

    private UserDAO getTestingUserDAO() throws Exception {
        OracleUserDAO dao = mock(OracleUserDAO.class);

        User testUser = new User();
        testUser.setLogin("Test login");
        testUser.setPassword("Test password");

        when(dao.getUser("Test login"))
                .thenReturn(testUser);

        return dao;
    }

    @Bean
    public BooksController booksController() throws Exception {
        return new BooksController(getTestingBookDAO());
    }

    @Bean
    public NotificationsController notificationsController() throws Exception {
        return new NotificationsController(getTestingNotificationDAO());
    }

    @Bean
    public TagsController tagsController() throws Exception {
        return new TagsController(getTestingTagDAO());
    }

    @Bean
    public UsersController usersController() throws Exception {
        return new UsersController(getTestingUserDAO());
    }


    private void setUpDataSourceJNDI() throws NamingException {

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/jdbc");
        ic.createSubcontext("java:comp/env/jdbc/oracle");

        ic.rebind("java:comp/env/jdbc/oracle", new DriverManagerDataSource());
    }
}
