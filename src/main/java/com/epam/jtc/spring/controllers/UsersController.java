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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private static final String WRONG_USER_DATA_ERROR_MESSAGE =
            "Wrong user data";

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
    public ResponseEntity loginUser(@Valid User user,
                                    BindingResult bindingResult) {
        logger.info("Start login for user: {}", user);

        List<String> errors = new ArrayList<>();
        Object toReturn = errors;

        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
                errors.add(er.getDefaultMessage());
            }
            //throw new Exception("User is not valid");
        } else {

            User userFromDAO = dao.getUser(user.getLogin());

            logger.info("User from DAO: {}", userFromDAO);

            if (userFromDAO == null ||
                    !userFromDAO.getPassword().equals(user.getPassword())) {
                logger.warn(WRONG_USER_DATA_ERROR_MESSAGE);
                errors.add(WRONG_USER_DATA_ERROR_MESSAGE);
                //throw new Exception("Wrong user data");
            } else {
                activeUser.setLogin(userFromDAO.getLogin());
                activeUser.setPassword(userFromDAO.getPassword());

                toReturn = userFromDAO;
            }
        }
        logger.info("Logged user: {}", toReturn);

        ResponseEntity responseEntity =
                new ResponseEntity<>(toReturn, HttpStatus.ACCEPTED);

        //logger.info("After add try: {}", responseEntity);

        return responseEntity;
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
