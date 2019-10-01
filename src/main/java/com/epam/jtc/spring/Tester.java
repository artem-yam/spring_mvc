package com.epam.jtc.spring;

import com.epam.jtc.spring.controllers.UsersController;
import com.epam.jtc.spring.datalayer.DataSourceType;

public class Tester {
    
    public static void main(String[] args) {
/*        UserDAO dao =
            DAOFactory.getInstance(DataSourceType.ORACLE)
                .getUserDAO();*/
    
        try {
           // new UsersController(DataSourceType.ORACLE).logoutUser(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
}
