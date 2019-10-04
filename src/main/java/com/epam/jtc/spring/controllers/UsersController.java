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

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Controller for books
 */
@RestController
@RequestMapping("/users")
public class UsersController implements Serializable {
    
    /**
     * logger for class
     */
    private static final Logger logger =
        LogManager.getLogger(UsersController.class);
    
    /**
     * DAO for operations with users
     */
    private UserDAO dao;
    
    private User activeUser;
    
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
    
    @Autowired
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
    
    /**
     * Method to login the user
     * t
     *
     * @param user          user to login
     * @param bindingResult {@link BindingResult} binding result with user's validity check
     * @throws Exception some exception while user login
     */
    @PostMapping(value = "/login",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loginUser(@Valid User user, BindingResult bindingResult)
        throws Exception {
        logger.info("Start login for user: {}", user);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
            }
            throw new Exception("User is not valid");
        }
        
        User userFromDAO = dao.getUser(user.getLogin());
        
        logger.info("User from DAO: {}", userFromDAO);
        
        if (userFromDAO == null ||
                !userFromDAO.getPassword().equals(user.getPassword())) {
            throw new Exception("Wrong user data");
        }
        
        activeUser.setLogin(userFromDAO.getLogin());
        activeUser.setPassword(userFromDAO.getPassword());
    }
    
    /**
     * Method to logout the user
     *
     * @throws Exception some exception while user logout
     */
    @PostMapping(value = "/logout")
    public void logoutUser() {
        
        User userToLogOut = dao.getUser(activeUser.getLogin());
        
        if (userToLogOut != null) {
            //.info("Start logout for user: {} ", userToLogOut);
            
            activeUser.reset();
        }
        
    }
}
