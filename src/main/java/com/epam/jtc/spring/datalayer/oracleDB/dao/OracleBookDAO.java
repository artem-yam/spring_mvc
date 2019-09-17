package com.epam.jtc.spring.datalayer.oracleDB.dao;

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
     * Logger
     */
    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass().getEnclosingClass());

    //private static final int BOOK_MAX_RATING = 5;

    private final RowMapper<Book> bookRowMapper =
            new BeanPropertyRowMapper<Book>() {

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
                            book.setImage("data:image/jpeg;base64," +
                                    new String(imageBlobBytes));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    book.setRating(rs.getInt(5));
                    book.setDeleted(rs.getBoolean(6));

                    return book;
                }
            };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate
                .query("select * from books order by id",
                        bookRowMapper);
    }

    /*@Override
    public List<Book> searchBooks(String searchText) {
        return jdbcTemplate
                .query("select * from books where " +
                                "instr(lower(title)||' '||lower(author), ?)>0",
                        bookRowMapper, searchText);
    }*/

    /*@Override
    public List<Book> getMostPopular() {
        return jdbcTemplate
                .query("select * from books where rating = ?",
                        bookRowMapper, BOOK_MAX_RATING);
    }*/

    @Override
    public int addBook(String title, String author,
                       String coverImage) {
/*        logger.info("Adding book = {},{}",
            title, author);*/

        byte[] blobImage = null; //Base64.getDecoder().decode(coverImage);

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

        jdbcTemplate.update("insert into books(title, author,image) " +
                        "values (?, ?, ?)",
                title, author, blobImage);

        return jdbcTemplate
                .queryForObject("select id from books " +
                                "where title=? and author=?",
                        int.class, title, author);
    }

    @Override
    public void changeRating(int bookId, int newRating) {
        jdbcTemplate.update("update books set rating=? where id=?",
                newRating, bookId);
    }

}
