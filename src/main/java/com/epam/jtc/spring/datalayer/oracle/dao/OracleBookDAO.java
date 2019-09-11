package com.epam.jtc.spring.datalayer.oracle.dao;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Employee DAO for oracle DB
 */
@Component
public class OracleBookDAO implements BookDAO {
    /**
     * Logger
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    private static final int BOOK_MAX_RATING = 5;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private RowMapper<Book> bookRowMapper = new BeanPropertyRowMapper<Book>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum)
            throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setTitle(rs.getString(2));
            book.setAuthor(rs.getString(3));
            
            if (rs.getBlob(4) != null) {
                InputStream input = rs.getBlob(4).getBinaryStream();
                
                File targetFile =
                    new File("src/main/resources/booksCovers/"
                                 + book.getAuthor() + " " +
                                 book.getTitle() +
                                 ".jpg");
                
                try {
                    FileUtils.copyInputStreamToFile(input, targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                book.setImage(targetFile);
            }
            
            book.setRating(rs.getInt(5));
            return book;
        }
    };
    
    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate
                   .query("select * from books where is_deleted = '0'",
                       bookRowMapper);
    }
    
    @Override
    public List<Book> searchBooks(String searchText) {
        return jdbcTemplate
                   .query("select * from books where " +
                              "instr(lower(title)||' '||lower(author), ?)>0",
                       bookRowMapper, searchText);
    }
    
    @Override
    public List<Book> getMostPopular() {
        return jdbcTemplate
                   .query("select * from books where rating = ?",
                       bookRowMapper, BOOK_MAX_RATING);
    }
    
    @Override
    public void addBook(String title, String author, File coverImage) {
        
        try {
            byte[] file = FileUtils.readFileToByteArray(coverImage);
            
            jdbcTemplate
                .update("insert into books(title, author,image) " +
                            "values (?, ?, ?)", title, author, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
