package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tag DAO for oracle DB
 */
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
     * Query to get all available tags
     */
    private static final String GET_ALL_TAGS_QUERY =
        "select tag from AVAILABLE_TAGS";
    
    /**
     * Query to insert new tag
     */
    private static final String INSERT_NEW_TAG_QUERY =
        "insert into AVAILABLE_TAGS(tag) values(?)";
    
    /**
     * Query to get all tags of the book
     */
    private static final String GET_TAGS_FOR_BOOK_QUERY =
        "select tag from AVAILABLE_TAGS where id in (select tag from BOOK_TAGS where book = ?)";
    
    /**
     * Query to insert the tag to the book
     */
    private static final String INSERT_TAG_TO_BOOK_QUERY =
        "insert into BOOK_TAGS(book, tag) values (?,(select id from AVAILABLE_TAGS where tag = ?))";
    
    /**
     * JDBC template to connect DB
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<String> getAllTags() {
        return jdbcTemplate
                   .queryForList(GET_ALL_TAGS_QUERY, String.class);
    }
    
    private void addTag(String text) {
        if (!getAllTags().contains(text)) {
            jdbcTemplate.update(INSERT_NEW_TAG_QUERY, text);
        }
    }
    
    @Override
    public List<String> getBookTags(int bookId) {
        return jdbcTemplate
                   .queryForList(GET_TAGS_FOR_BOOK_QUERY, String.class, bookId);
    }
    
    @Override
    public List<String> addTagToBook(int bookId, String tag) {
        
        addTag(tag);
        
        if (!getBookTags(bookId).contains(tag)) {
            jdbcTemplate.update(INSERT_TAG_TO_BOOK_QUERY, bookId, tag);
        }
        
        return getBookTags(bookId);
    }
}
