package com.epam.jtc.spring.datalayer.oracle;

import com.epam.jtc.spring.datalayer.BookDAO;
import com.epam.jtc.spring.datalayer.dto.my.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Employee DAO for oracle DB
 */
//@Component
public class OracleBookDAO implements BookDAO {
    /**
     * Logger
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    /**
     * Number of ID column
     */
    private static final int ID_COLUMN = 1;
    /**
     * Number of last name column
     */
    private static final int LAST_NAME_COLUMN = 2;
    /**
     * Number of first name column
     */
    private static final int FIRST_NAME_COLUMN = 3;
    
    /**
     * Message for error while interacting with DB
     */
    private static final String DB_INTERACTION_ERROR_MESSAGE =
        "DB interaction error";
    
    /**
     * Data source to connect DB
     */
    private DataSource dataSource;
    
    //@Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Class constructor
     *
     * @param dataSource {@link DataSource}
     */
    public OracleBookDAO(DataSource dataSource) {
        this.dataSource = dataSource;
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
    public List<Book> getAllEmployees() {
        
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
            dataSource.getConnection()).getAllEmployees()) {
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
    public Book createEmployee(String firstName, String lastName) {
        
        logger.debug("Starting employee {} {} creation", firstName, lastName);
        
        Book newEmployee = null;
        
        try (ResultSet rs = new OracleResultSetsGetter(
            dataSource.getConnection()).createEmployee(firstName, lastName)) {
            rs.next();
            
            /*newEmployee =
                new Employee(rs.getLong(ID_COLUMN), lastName,
                    firstName);*/
            
        } catch (SQLException employeesSQLException) {
        }
        
        logger.debug("Employee {} was created", newEmployee);
        
        return newEmployee;
    }
    
}
