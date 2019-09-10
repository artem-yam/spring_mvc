package com.epam.jtc.spring.datalayer.oracle.dao;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OracleBookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OracleBookDAO() {
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAllBooks() {
        
        /*jdbcTemplate.queryForObject("select * from AVAILABLE_TAGS",
            new RowMapper<Object>() {
                public Object mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                    Spitter spitter = new Spitter(); // Отображение
                    spitter.setId(rs.getLong(1)); // результатов
                    spitter.setUsername(rs.getString(2)); // в объект
                    spitter.setPassword(rs.getString(3));
                    spitter.setFullName(rs.getString(4));
                    return spitter;
                }
            }
        );
        */

        List<Map<String, Object>> tags = jdbcTemplate
                .queryForList(
                        "select * from AVAILABLE_TAGS");
  
       /* jdbcTemplate
            .update("insert into AVAILABLE_TAGS(tag) values ('test tag')");*/


        for (Object tag : tags) {
            System.out.println(tag);
        }

        List<Book> employees = new ArrayList<>();
        
        /*try (ResultSet rs = new OracleResultSetsGetter(
            dataSource.getConnection()).getAllBooks()) {
            while (rs.next()) {
               *//* employees.add(
                    new Employee(rs.getLong(ID_COLUMN), rs.getString(
                        LAST_NAME_COLUMN), rs.getString(
                        FIRST_NAME_COLUMN)));*//*
            }
        } catch (SQLException employeesSQLException) {
        }
        
        logger.debug("Received employees list {}", employees);*/

        return employees;
    }

    @Override
    public List<Book> searchBooks(String searchText) {
        return null;
    }

    @Override
    public List<Book> getMostPopular() {
        return null;
    }

    @Override
    public void addBook(String title, String author, String coverImage) {

    }

}
