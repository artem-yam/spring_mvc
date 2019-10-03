package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Controller for books
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    
    /**
     * logger for class
     */
    private static final Logger logger =
        LogManager.getLogger(UsersController.class);
    
    /**
     * DAO for operations with users
     */
    private UserDAO dao;
    
    /**
     * Controller
     *
     * @param dataSourceType type of data source
     */
    @Autowired
    public UsersController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                  .getUserDAO();
        
    }
    
    /**
     * Method to login the user
     *
     * @param request       {@link HttpServletRequest} request
     * @param user          user to login
     * @param bindingResult {@link BindingResult} binding result with user's validity check
     * @throws Exception some exception while user login
     */
    @PostMapping(value = "/login",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loginUser(HttpServletRequest request,
                          @Valid User user, BindingResult bindingResult)
        throws Exception {
        logger.info("Start login for user: {}", user);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
            }
            throw new Exception("User is not valid");
        }
        
        request.getSession().setAttribute("user_controller", this);
        
        dao.loginUser(user.getLogin(), user.getPassword());
        
        //logger.info("User logged in");
        
        request.getSession().setAttribute("logged_user", user.getLogin());
    }
    
    /**
     * Method to logout the user
     *
     * @param request {@link HttpServletRequest} request
     * @throws Exception some exception while user logout
     */
    @PostMapping(value = "/logout")
    public void logoutUser(HttpServletRequest request) throws Exception {
        
        String userToLogOut = null;
        
        if (request.getSession(false) != null) {
            userToLogOut =
                (String) request.getSession(false)
                             .getAttribute("logged_user");
        }
        
        if (userToLogOut != null) {
            
            logger.info("Start logout for user: {} ", userToLogOut);
            
            performDAOLogout(userToLogOut);
            
            if (request.getSession(false) != null) {
                request.getSession(false).removeAttribute("logged_user");
            }
        }
        
    }
    
    /**
     * Simple user logout
     *
     * @param login login of user to logout
     * @throws SQLException some exception while user logout
     */
    public void performDAOLogout(String login) throws SQLException {
        dao.logoutUser(login);
    }
    
}
