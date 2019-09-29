package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Employee DAO for oracleDB DB
 */
@Component
public class OracleBookDAO implements BookDAO {

    /**
     * Logger for class
     */
    private static final Logger DAOLogger = LogManager
            .getLogger(new Object() {
            }.getClass()
                    .getEnclosingClass());
    /**
     * Query to get all books
     */
    private static final String ALL_BOOKS_QUERY =
            "select * from books order by id";
    /**
     * Query to add new book
     */
    private static final String ADD_BOOK_QUERY =
            "insert into books(title, author,image) values (?, ?, ?)";
    /**
     * Query to get book with certain title and author
     */
    private static final String SEARCH_BOOK_QUERY =
            "select * from books where title=? and author=?";
    /**
     * Query to update book rating
     */
    private static final String RATING_CHANGE_QUERY =
            "update books set rating=? where id=?";
    /**
     * Query to get book by id
     */
    private static final String IMAGE_BY_BOOK_ID_QUERY =
            "select image from books where id=?";

    /**
     * Query to get default book image
     */
    private static final String DEFAULT_IMAGE_QUERY =
            "select image from books where id=0";
    /**
     * Row mapper for result sets from DB 'books' table
     */
    @Autowired
    private RowMapper<Book> bookRowMapper;

    /**
     * Row mapper to get byte of image
     */
    @Autowired
    private RowMapper<byte[]> imageRowMapper;
    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TagDAO tagDAO;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = jdbcTemplate.query(ALL_BOOKS_QUERY, bookRowMapper);

        for (Book book : books) {
            book.setTags(tagDAO.getBookTags(book.getId()));
        }

        return books;
    }

    @Override
    public Book addBook(String title, String author,
                        byte[] coverImage) {

        jdbcTemplate.update(ADD_BOOK_QUERY, title, author, coverImage);

        Book book = jdbcTemplate
                .queryForObject(SEARCH_BOOK_QUERY, bookRowMapper, title,
                        author);


        //DAOLogger.info("Added book: {}", book);

        return book;
    }

    @Override
    public void changeRating(int bookId, int newRating) {
        jdbcTemplate.update(RATING_CHANGE_QUERY, newRating, bookId);
    }

    @Override
    public byte[] getBookImage(int bookId) {
        byte[] image = jdbcTemplate
                .queryForObject(IMAGE_BY_BOOK_ID_QUERY, imageRowMapper, bookId);

        if (image == null) {
            image = getDefaultImage();
        }

        return image;
    }

    private byte[] getDefaultImage() {
        return jdbcTemplate
                .queryForObject(DEFAULT_IMAGE_QUERY, imageRowMapper);
    }

}
