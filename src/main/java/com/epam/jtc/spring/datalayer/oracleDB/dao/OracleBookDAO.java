package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.io.IOUtils.toByteArray;

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
     * Row mapper for result sets from DB 'books' table
     */
    private static final RowMapper<Book> bookRowMapper =
        new BeanPropertyRowMapper<Book>() {
            
            /**
             * Prefix for base64 code representation of image
             */
            private static final String BASE64_CODE_IMAGE_PREFIX =
                "data:image/jpeg;base64,";
            
            @Override
            public Book mapRow(ResultSet rs, int rowNum)
                throws SQLException {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                
                if (rs.getBlob(4) != null) {
                    InputStream imageBlobStream =
                        rs.getBlob(4).getBinaryStream();
                    try {
                        byte[] imageBlobBytes =
                            IOUtils.toByteArray(imageBlobStream);
                        
                        if (!Base64.isBase64(imageBlobBytes)) {
                            imageBlobBytes =
                                Base64.encodeBase64(imageBlobBytes);
                        }
                        book.setImage(BASE64_CODE_IMAGE_PREFIX +
                                          new String(imageBlobBytes));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                book.setRating(rs.getInt(5));
                book.setDeleted(rs.getBoolean(6));
                
                book.setTags(DAOFactory.getInstance(DataSourceType.ORACLE)
                                 .getTagDAO().getBookTags(book.getId()));
                
                return book;
            }
        };
    
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
        "select id from books where title=? and author=?";
    
    /**
     * Query to update book rating
     */
    private static final String RATING_CHANGE_QUERY =
        "update books set rating=? where id=?";
    
    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate
                   .query(ALL_BOOKS_QUERY,
                       bookRowMapper);
    }
    
    @Override
    public int addBook(String title, String author,
                       String coverImage) {
        
        byte[] blobImage = null;
        
        if (coverImage != null) {
            try {
                blobImage = toByteArray(IOUtils.toInputStream(coverImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if (Base64.isBase64(blobImage)) {
                blobImage =
                    Base64.decodeBase64(blobImage);
            }
        }
        
        jdbcTemplate.update(ADD_BOOK_QUERY, title, author, blobImage);
        
        return jdbcTemplate
                   .queryForObject(SEARCH_BOOK_QUERY, int.class, title, author);
    }
    
    @Override
    public void changeRating(int bookId, int newRating) {
        jdbcTemplate.update(RATING_CHANGE_QUERY, newRating, bookId);
    }
    
}
