package com.epam.jtc.spring.datalayer.oracle;

import com.epam.jtc.spring.datalayer.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Employee DAO for oracle DB
 */
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
    
    /**
     * Class constructor
     *
     * @param dataSource {@link DataSource}
     */
    public OracleBookDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public List<Book> getAllEmployees() {
        List<Book> employees = new ArrayList<>();
        
        try (ResultSet rs = new OracleResultSetsGetter(
            dataSource.getConnection()).getAllEmployees()) {
            while (rs.next()) {
               /* employees.add(
                    new Employee(rs.getLong(ID_COLUMN), rs.getString(
                        LAST_NAME_COLUMN), rs.getString(
                        FIRST_NAME_COLUMN)));*/
            }
        } catch (SQLException employeesSQLException) {
        }
        
        logger.debug("Received employees list {}", employees);
        
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
