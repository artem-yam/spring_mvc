package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    
    private static final String WRONG_USER_DATA_ERROR_MESSAGE =
        "Wrong user data";
    
    /**
     * DAO for operations with users
     */
    private UserDAO dao;
    
    @Autowired
    private User activeUser;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
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
    
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
    
    public void setSessionRegistry(
        SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
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
    public ResponseEntity logInUser(@Valid User user, HttpServletRequest req,
                                    BindingResult bindingResult) {
        logger.info("Start log in for user: {}", user);
        
        List<String> errors = new ArrayList<>();
        Object toReturn = errors;
        
        if (bindingResult.hasErrors()) {
            logger.debug("User {} data is not valid", user);
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.debug(er);
                errors.add(er.getDefaultMessage());
            }
        } else {
            User userFromDAO = dao.getUser(user.getLogin());
            
            logger.debug("User from DAO: {}", userFromDAO);
            
            if (userFromDAO == null ||
                    !userFromDAO.getPassword().equals(user.getPassword())) {
                logger.debug("Incorrect login or password");
                errors.add(WRONG_USER_DATA_ERROR_MESSAGE);
            } else {
                
                /*User.DEFAULT_LOGIN = userFromDAO.getLogin();
                User.DEFAULT_PASS = userFromDAO.getPassword();*/
                
                req.getSession().invalidate();
                
                activeUser.setLogin(userFromDAO.getLogin());
                activeUser.setPassword(userFromDAO.getPassword());
                
                /*sessionRegistry.registerNewSession(req.getSession(true).getId(),
                    activeUser);*/
                
                toReturn = userFromDAO;
                
                logger.info("Successful log in: {}", userFromDAO);
            }
        }
        
        ResponseEntity responseEntity =
            new ResponseEntity<>(toReturn, HttpStatus.ACCEPTED);
        
        logger.info("Log in user method returns: {}", toReturn);
        
        return responseEntity;
    }
    
    /**
     * Method to logout the user
     *
     * @throws Exception some exception while user logout
     */
    @PostMapping(value = "/logout")
    public void logOutUser(HttpServletRequest req) {
        logger.info("Start log out for user: {}", activeUser);
        
        User userToLogOut = dao.getUser(activeUser.getLogin());
        
        if (userToLogOut != null) {
            logger.info("Successful log out: {} ", userToLogOut);
            
            req.getSession().invalidate();
            activeUser.reset();
        }
        
    }
}
