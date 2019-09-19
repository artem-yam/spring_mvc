package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OracleTagDAO implements TagDAO {
    
    /**
     * Logger for class
     */
    private static final Logger DAOLogger = LogManager
                                                .getLogger(new Object() {
                                                }.getClass()
                                                               .getEnclosingClass());
    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<String> getAllTags() {
        return jdbcTemplate
                   .queryForList(
                       "select tag from AVAILABLE_TAGS",
                       String.class);
    }
    
    @Override
    public void addTag(String text) {
        if (!getAllTags().contains(text)) {
            jdbcTemplate
                .update("insert into AVAILABLE_TAGS(tag) values(?)", text);
        }
    }
    
    @Override
    public List<String> getBookTags(int bookId) {
        return jdbcTemplate
                   .queryForList(
                       "select tag from AVAILABLE_TAGS where id in " +
                           "(select tag from BOOK_TAGS where book = ?)",
                       String.class, bookId);
    }
    
    @Override
    public void addTagToBook(int bookId, String tag) {
        
        addTag(tag);
        
        if (!getBookTags(bookId).contains(tag)) {
            jdbcTemplate
                .update("insert into BOOK_TAGS(book, tag) values " +
                            "(?,(select id from AVAILABLE_TAGS where tag = ?))",
                    bookId, tag);
        }
    }
}
