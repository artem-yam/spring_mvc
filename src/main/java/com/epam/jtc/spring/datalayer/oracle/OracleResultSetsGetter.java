package com.epam.jtc.spring.datalayer.oracle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Accesses DB and returns queries result sets
 */
public class OracleResultSetsGetter implements AutoCloseable {
    /**
     * Logger
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    /**
     * Number of parameter for last name
     */
    private static final int LAST_NAME_PARAMETER_NUMBER = 1;
    /**
     * Number of parameter for first name
     */
    private static final int FIRST_NAME_PARAMETER_NUMBER = 2;
    public static final int GENERATED_KEYS_INDEX = 1;
    
    /**
     * DD connection
     */
    private Connection connection;
    
    /**
     * Prepared Statement
     */
    private PreparedStatement ps;
    
    /**
     * Constructor
     *
     * @param connection {@link Connection}
     */
    public OracleResultSetsGetter(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Executes query to get all employees
     *
     * @return Query {@link ResultSet}
     * @throws SQLException DB error
     */
    @DBQuery(text = "SELECT * FROM EMPLOYEES ORDER BY EMPLOYEEID")
    @SuppressWarnings("findsecbugs:SQL_INJECTION_JDBC")
    public ResultSet getAllEmployees() throws SQLException {
        
        String queryText =
            new Object() {
            }.getClass().getEnclosingMethod().getAnnotation(DBQuery.class)
                .text();
        
        logger.debug("Executing query {}", queryText);
        
        ps = connection.prepareStatement(queryText);
        
        ResultSet rs = ps.executeQuery();
        
        logger.debug("Returning result set {}", rs);
        
        return rs;
    }
    
    /**
     * Executes query to create new employee
     *
     * @param firstName new employee first name
     * @param lastName  new employee last name
     * @return Query {@link ResultSet}
     * @throws SQLException DB error
     */
    @DBQuery(
        text = "INSERT INTO EMPLOYEES(EMPLOYEEID,LASTNAME,FIRSTNAME) VALUES (EMPLOYEES_SEQ.NEXTVAL,?,?)")
    @SuppressWarnings("findsecbugs:SQL_INJECTION_JDBC")
    public ResultSet createEmployee(String firstName, String lastName)
        throws SQLException {
        
        String queryText =
            new Object() {
            }.getClass().getEnclosingMethod().getAnnotation(DBQuery.class)
                .text();
        
        logger.debug("Executing query {} with parameters {}, {}", queryText,
            firstName, lastName);
        
        ps = connection.prepareStatement(
            queryText, new int[]{GENERATED_KEYS_INDEX});
        ps.setString(LAST_NAME_PARAMETER_NUMBER, lastName);
        ps.setString(FIRST_NAME_PARAMETER_NUMBER, firstName);
        
        ps.execute();
        
        ResultSet rs = ps.getGeneratedKeys();
        
        logger.debug("Returning result set {}", rs);
        
        return rs;
    }
    
    @Override
    public void close() throws Exception {
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
