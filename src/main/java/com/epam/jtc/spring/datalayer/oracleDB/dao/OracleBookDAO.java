package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Book DAO for oracle DB
 */
@Component
public class OracleBookDAO implements BookDAO {

    private static final Logger logger =
            LogManager.getLogger(OracleBookDAO.class);

    private static final String ALL_BOOKS_QUERY =
            "select * from books order by id";

    private static final String ADD_BOOK_QUERY =
            "insert into books(title, author,image) values (?, ?, ?)";

    private static final String SEARCH_BOOK_QUERY =
            "select * from books where title=? and author=?";

    private static final String BOOK_UPDATE_QUERY =
            "update books set title=?, author=?,rating=?,is_deleted=? where id=?";

    private static final String IMAGE_BY_BOOK_ID_QUERY =
            "select image from books where id=?";

    private static final String BOOK_BY_ID_QUERY =
            "select * from books where id=?";

    private static final String DEFAULT_IMAGE_QUERY =
            "select image from books where id=0";

    private static final String DELETE_BOOK_QUERY =
            "update books set is_deleted=1 where id=?";

    private static final String SEARCH_BOOKS_QUERY =
            "select * from books where instr(lower(title || author),?)>0";

    private static final String MOST_POPULAR_BOOKS_QUERY_SUFFIX =
            " and rating=5";

    private static final String MOST_POPULAR_FILTER = "most popular";

    @Autowired
    private RowMapper<Book> bookRowMapper;

    @Autowired
    private RowMapper<byte[]> imageRowMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TagDAO tagDAO;

    @Override
    @Transactional
    public List<Book> getAllBooks() {
        logger.debug("Getting all books");

        List<Book> books = jdbcTemplate.query(ALL_BOOKS_QUERY, bookRowMapper);

        for (Book book : books) {
            book.setTags(tagDAO.getBookTags(book.getId()));
        }

        logger.debug(books);

        return books;
    }

    @Override
    public List<Book> filterBooks(String filter, String searchText) {
        logger.debug(
                "Getting filtered books. Filter: \'{}\'; Search text: \'{}\'",
                filter, searchText);

        final String filterQuery;

        if (MOST_POPULAR_FILTER.toLowerCase().equals(filter.toLowerCase())) {
            filterQuery = SEARCH_BOOKS_QUERY + MOST_POPULAR_BOOKS_QUERY_SUFFIX;
        } else {
            filterQuery = SEARCH_BOOKS_QUERY;
        }

        logger.debug("filter query: {}", filterQuery);

        List<Book> books =
                jdbcTemplate.query(filterQuery, bookRowMapper,
                        (searchText.isEmpty() ? " " :
                                searchText.toLowerCase()));

        for (Book book : books) {
            book.setTags(tagDAO.getBookTags(book.getId()));
        }

        logger.debug(books);

        return books;
    }

    @Override
    @Transactional
    public Book addBook(String title, String author,
                        byte[] coverImage) {
        logger.debug("Adding book: title={}, author={}; has cover image?",
                title, author, coverImage.length > 0);

        logger.debug(
                "Successful add? {}", jdbcTemplate.update(ADD_BOOK_QUERY,
                        title, author, coverImage) == 1);

        return jdbcTemplate
                .queryForObject(SEARCH_BOOK_QUERY, bookRowMapper, title,
                        author);
    }

    private Book getBook(int bookId) {
        logger.debug("Getting book {}", bookId);
        return jdbcTemplate
                .queryForObject(BOOK_BY_ID_QUERY, bookRowMapper, bookId);
    }

    @Override
    @Transactional
    public Book updateBook(int bookId, Book changedBook) throws Exception {
        logger.debug("Changing book {} to {}", bookId, changedBook);

        Book bookBeforeUpdate = null;
        Book bookToReturn = null;

        try {
            bookBeforeUpdate = getBook(bookId);

        } catch (RuntimeException getBookException) {
            logger.debug(getBookException);
            throw new SQLException("Can't get book", getBookException);
        }

        if (!changedBook.equals(bookBeforeUpdate)) {
            logger.debug("Successful update? {}",
                    jdbcTemplate
                            .update(BOOK_UPDATE_QUERY,
                                    changedBook.getTitle(),
                                    changedBook.getAuthor(),
                                    changedBook.getRating(),
                                    changedBook.isDeleted(),
                                    bookId) == 1);

            bookToReturn = getBook(bookId);
        } else {
            logger.debug("Book don't need to be updated");

            bookToReturn = bookBeforeUpdate;
        }

        return bookToReturn;
    }

    @Override
    public byte[] getBookImage(int bookId) throws Exception {
        logger.debug("Getting image for book {}", bookId);

        byte[] image;

        try {
            image = jdbcTemplate
                    .queryForObject(IMAGE_BY_BOOK_ID_QUERY,
                            imageRowMapper, bookId);
        } catch (RuntimeException getImageException) {
            logger.debug(getImageException);
            throw new SQLException("Can't get image", getImageException);
        }

        if (image == null || image.length == 0) {
            logger.debug("Image is null, setting default");
            image = getDefaultImage();
        }

        return image;
    }

    private byte[] getDefaultImage() {
        logger.debug("Getting default image");
        return jdbcTemplate
                .queryForObject(DEFAULT_IMAGE_QUERY, imageRowMapper);
    }

    @Override
    public void deleteBook(int bookId) {
        logger.debug("Deleting book {}", bookId);

        logger.debug("Successful delete? {}",
                jdbcTemplate.update(DELETE_BOOK_QUERY, bookId) == 1);
    }

}
