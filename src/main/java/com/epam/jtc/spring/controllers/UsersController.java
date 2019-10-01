package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Controller for books
 */
@RestController
@RequestMapping("/users")
public class UsersController /*implements Serializable */ {
    
    /**
     * logger for class
     */
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    private UserDAO dao;
    
    @Autowired
    public UsersController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                  .getUserDAO();
    }
    
    @PostMapping(value = "/login",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loginUser(HttpServletRequest request,
                          @RequestParam("login") String login,
                          @RequestParam("password") String password)
        throws Exception {
        //logger.info("Start login for user: {} {}", login, password);
        
        request.getSession().setAttribute("user_controller", this);
        
        dao.loginUser(login.trim(), password.trim());
        
        //logger.info("User logged in");
        
        request.getSession().setAttribute("logged_user", login);
    }
    
    @PostMapping(value = "/logout")
    public void logoutUser(HttpServletRequest request) throws Exception {
        
        String userToLogOut = null;
        
        if (request.getSession(false) != null) {
            userToLogOut =
                (String) request.getSession(false).getAttribute("logged_user");
        }
        
        if (userToLogOut != null) {
            
            logger.info("Start logout for user: {} ", userToLogOut);
            
            performDAOLogout(userToLogOut);
            
            if (request.getSession(false) != null) {
                request.getSession(false).removeAttribute("logged_user");
            }
        }
        
    }
    
    public void performDAOLogout(String login) throws SQLException {
        dao.logoutUser(login);
    }
    
}
