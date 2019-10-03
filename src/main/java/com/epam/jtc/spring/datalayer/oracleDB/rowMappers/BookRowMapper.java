package com.epam.jtc.spring.datalayer.oracleDB.rowMappers;

import com.epam.jtc.spring.datalayer.dto.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Forms book entity from DB entry
 */
@Component
public class BookRowMapper extends BeanPropertyRowMapper<Book> {
    
    @Override
    public Book mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(1));
        book.setTitle(rs.getString(2));
        book.setAuthor(rs.getString(3));
        book.setRating(rs.getInt(5));
        book.setDeleted(rs.getBoolean(6));
        
        return book;
    }
}

