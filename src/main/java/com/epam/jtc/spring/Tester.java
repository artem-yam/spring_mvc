package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.UserDAO;

import java.sql.SQLException;

public class Tester {
    
    public static void main(String[] args) {
        UserDAO dao =
            DAOFactory.getInstance(DataSourceType.ORACLE)
                .getUserDAO();

        try {
            dao.loginUser("login1","pass1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
