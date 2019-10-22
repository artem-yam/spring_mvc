package helpClasses;

import com.epam.jtc.spring.controllers.BooksController;
import com.epam.jtc.spring.controllers.NotificationsController;
import com.epam.jtc.spring.controllers.TagsController;
import com.epam.jtc.spring.controllers.UsersController;
import com.epam.jtc.spring.datalayer.OracleJdbcTemplate;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.dto.Notification;
import com.epam.jtc.spring.datalayer.dto.User;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleNotificationDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleTagDAO;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleUserDAO;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfigurationUtils {

    public TestConfigurationUtils() {
        BasicConfigurator.configure();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(OracleJdbcTemplate.class);
    }

    @Bean
    public RowMapper rowMapper() {
        return mock(RowMapper.class);
    }

    @Bean
    public User user() {
        return mock(User.class);
    }

    @Bean
    public BookDAO getTestingBookDAO() throws Exception {
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

    @Bean
    public BooksController booksController() throws Exception {
        return new BooksController();
    }

    @Bean
    public NotificationDAO getTestingNotificationDAO() throws Exception {
        OracleNotificationDAO dao = mock(OracleNotificationDAO.class);

        Notification testNotification = new Notification();
        testNotification.setDate(new Date(0));

        when(dao.getAllNotifications())
                .thenReturn(Arrays.asList(testNotification));

        when(dao.addNotification(testNotification))
                .thenReturn(testNotification);

        return dao;
    }

    @Bean
    public NotificationsController notificationsController() throws Exception {
        return new NotificationsController();
    }

    @Bean
    public TagDAO getTestingTagDAO() throws Exception {
        OracleTagDAO dao = mock(OracleTagDAO.class);

        List<String> testTags = Arrays.asList("Test tag");

        when(dao.getAllTags())
                .thenReturn(testTags);

        when(dao.bindTagToBook(1, "Test tag"))
                .thenReturn(testTags);

        testTags = new ArrayList<>();

        when(dao.unbindTag(1, "Test tag"))
                .thenReturn(testTags);

        return dao;
    }

    @Bean
    public TagsController tagsController() throws Exception {
        return new TagsController();
    }


    @Bean
    public UserDAO getTestingUserDAO() throws Exception {
        OracleUserDAO dao = mock(OracleUserDAO.class);

        User testUser = new User();
        testUser.setLogin("Test login");
        testUser.setPassword("Test password");

        when(dao.getUser("Test login"))
                .thenReturn(testUser);

        return dao;
    }

    @Bean
    public UsersController usersController() throws Exception {
        return new UsersController();
    }

}
