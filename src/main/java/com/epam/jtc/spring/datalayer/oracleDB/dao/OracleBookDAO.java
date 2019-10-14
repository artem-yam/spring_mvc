package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.TagDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
    
    /**
     * Logger for class
     */
    private static final Logger logger =
        LogManager.getLogger(OracleBookDAO.class);
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
     * Query to get image by book id
     */
    private static final String IMAGE_BY_BOOK_ID_QUERY =
        "select image from books where id=?";
    
    /**
     * Query to get book by id
     */
    private static final String BOOK_BY_ID_QUERY =
        "select * from books where id=?";
    
    /**
     * Query to get default book image
     */
    private static final String DEFAULT_IMAGE_QUERY =
        "select image from books where id=0";
    
    /**
     * Query to delete book
     */
    private static final String DELETE_BOOK_QUERY =
        "update books set is_deleted=1 where id=?";
    
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
    
    /**
     * Tags DAO
     */
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
        
        logger.info(books);
        
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
    
    /**
     * Gets the book by it's id
     *
     * @param bookId id of the book
     * @return found book
     */
    private Book getBook(int bookId) {
        logger.debug("Getting book {}", bookId);
        return jdbcTemplate
                   .queryForObject(BOOK_BY_ID_QUERY, bookRowMapper, bookId);
    }
    
    @Override
    @Transactional
    public Book changeRating(int bookId, int newRating) throws Exception {
        logger.debug("Changing book {} rating to {}", bookId, newRating);
        
        try {
            getBook(bookId);
        } catch (RuntimeException getBookException) {
            logger.debug(getBookException);
            throw new SQLException("Can't get book", getBookException);
        }
        
        logger.debug("Successful rating change? {}",
            jdbcTemplate.update(RATING_CHANGE_QUERY, newRating, bookId) ==
                1);
        
        return getBook(bookId);
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
    
    /**
     * Gets default image for book
     *
     * @return default image
     */
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
