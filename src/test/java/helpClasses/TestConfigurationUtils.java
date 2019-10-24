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


    private static final String TEST_BOOK_TITLE = "Test title";
    private static final String TEST_BOOK_AUTHOR = "Test author";
    private static final String TEST_TAG = "Test tag";
    private static final String TEST_LOGIN = "Test login";
    private static final String TEST_PASS = "Test password";


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

        Book testBook = new Book(TEST_BOOK_TITLE, TEST_BOOK_AUTHOR);

        when(dao.getAllBooks()).thenReturn(Arrays.asList(testBook));

        when(dao.addBook(testBook.getTitle(), testBook.getAuthor(),
                new byte[0])).thenReturn(testBook);

        when(dao.addBook(testBook.getTitle(), testBook.getAuthor(),
                new byte[0])).thenReturn(testBook);

        testBook = new Book();

        when(dao.updateBook(testBook.getId(), testBook))
                .thenReturn(testBook);

        when(dao.getBookImage(new Book().getId())).thenReturn(new byte[0]);

        return dao;
    }

    @Bean
    public BooksController booksController() {
        return new BooksController();
    }

    @Bean
    public NotificationDAO getTestingNotificationDAO() {
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
    public NotificationsController notificationsController() {
        return new NotificationsController();
    }

    @Bean
    public TagDAO getTestingTagDAO() {
        OracleTagDAO dao = mock(OracleTagDAO.class);

        List<String> testTags = Arrays.asList(TEST_TAG);

        when(dao.getAllTags())
                .thenReturn(testTags);

        when(dao.bindTagToBook(new Book().getId(), TEST_TAG))
                .thenReturn(testTags);

        testTags = new ArrayList<>();

        when(dao.unbindTag(new Book().getId(), TEST_TAG))
                .thenReturn(testTags);

        return dao;
    }

    @Bean
    public TagsController tagsController() {
        return new TagsController();
    }


    @Bean
    public UserDAO getTestingUserDAO() {
        OracleUserDAO dao = mock(OracleUserDAO.class);

        User testUser = new User();
        testUser.setLogin(TEST_LOGIN);
        testUser.setPassword(TEST_PASS);

        when(dao.getUser(testUser.getLogin()))
                .thenReturn(testUser);

        return dao;
    }

    @Bean
    public UsersController usersController() {
        return new UsersController();
    }

}
