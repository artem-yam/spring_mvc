package com.epam.jtc.spring.datalayer.oracleDB.dao;

import com.epam.jtc.spring.datalayer.dao.TagDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Tag DAO for oracle DB
 */
@Component
public class OracleTagDAO implements TagDAO {

    private static final Logger logger =
            LogManager.getLogger(OracleTagDAO.class);

    private static final String GET_ALL_TAGS_QUERY =
            "select tag from AVAILABLE_TAGS";

    private static final String INSERT_NEW_TAG_QUERY =
            "insert into AVAILABLE_TAGS(tag) values(?)";

    private static final String GET_TAGS_FOR_BOOK_QUERY =
            "select tag from AVAILABLE_TAGS where id in (select tag from BOOK_TAGS where book = ?)";

    private static final String INSERT_TAG_TO_BOOK_QUERY =
            "insert into BOOK_TAGS(book, tag) values (?,(select id from AVAILABLE_TAGS where tag = ?))";

    private static final String UNBIND_TAG_FROM_BOOK_QUERY =
            "delete from BOOK_TAGS where book=? and tag in (select id from AVAILABLE_TAGS where tag = ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getAllTags() {
        logger.debug("Getting all tags");
        return jdbcTemplate
                .queryForList(GET_ALL_TAGS_QUERY, String.class);
    }

    private void addTag(String text) {
        if (!getAllTags().contains(text)) {
            logger
                    .debug("Adding tag \'{}\' to list of all available tags",
                            text);
            jdbcTemplate.update(INSERT_NEW_TAG_QUERY, text);
        }
    }

    @Override
    public List<String> getBookTags(int bookId) {
        logger.debug("Getting tags to book {}", bookId);
        return jdbcTemplate
                .queryForList(GET_TAGS_FOR_BOOK_QUERY, String.class, bookId);
    }

    @Override
    @Transactional
    public List<String> addTagToBook(int bookId, String tag) {
        logger.debug("Adding tag \'{}\' to book {}", tag, bookId);

        addTag(tag);

        if (!getBookTags(bookId).contains(tag)) {
            logger.debug("Tag was added? {}",
                    jdbcTemplate.update(INSERT_TAG_TO_BOOK_QUERY,
                            bookId, tag) == 1);
        }

        return getBookTags(bookId);
    }

    @Override
    @Transactional
    public List<String> unbindTag(int bookId, String tag) {
        logger.debug("Unbinding tag \'{}\' from book {}", tag, bookId);

        if (getBookTags(bookId).contains(tag)) {

            logger.debug("Tag was unbind? {}",
                    jdbcTemplate.update(UNBIND_TAG_FROM_BOOK_QUERY,
                            bookId, tag) == 1);
        }

        return getBookTags(bookId);
    }
}
