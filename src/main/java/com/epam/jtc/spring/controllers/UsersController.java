package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dao.UserDAO;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for books
 */
@RestController
@RequestMapping("/userSession")
public class UsersController {

    private static final Logger logger =
            LogManager.getLogger(UsersController.class);

    private static final String WRONG_USER_PASS_ERROR_MESSAGE =
            "Wrong password";
    private static final String WRONG_USER_DATA_ERROR_MESSAGE =
            "Wrong user data";
    private static final String CANT_ACCESS_USER_ERROR_MESSAGE =
            "Can't access to user in DB";

    @Autowired
    private UserDAO dao;

    @Autowired
    private User activeUser;

    /**
     * Method to login the user
     * t
     *
     * @param user          user to login
     * @param bindingResult {@link BindingResult} binding result with user's validity check
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity logInUser(@Valid User user,
                                    BindingResult bindingResult) {
        logger.info("Start log in for user: {}", user);

        List<String> errors = new ArrayList<>();
        ResponseEntity responseEntity =
                new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);

        if (bindingResult.hasErrors()) {
            logger.debug("User {} data is not valid", user);
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.debug(er);
                errors.add(er.getDefaultMessage());
            }
        } else {
            try {
                User userFromDAO = dao.getUser(user.getLogin());

                logger.debug("User from DAO: {}", userFromDAO);

                if (!userFromDAO.getPassword().equals(user.getPassword())) {
                    logger.debug("Incorrect login or password");
                    errors.add(WRONG_USER_PASS_ERROR_MESSAGE);
                } else {
                    activeUser.setLogin(userFromDAO.getLogin());
                    userFromDAO.setPassword("");

                    responseEntity =
                            new ResponseEntity<>(userFromDAO, HttpStatus.OK);

                    logger.info("Successful log in: {}", userFromDAO);
                }
            } catch (Exception ex) {
                String errorMessage = CANT_ACCESS_USER_ERROR_MESSAGE;
                if (ex instanceof EmptyResultDataAccessException) {
                    errorMessage = WRONG_USER_DATA_ERROR_MESSAGE;
                }

                logger.info("Can't log in user", ex);

                errors.add(errorMessage);
            }


        }

        logger.info("Log in user method returns: {}", responseEntity);

        return responseEntity;
    }

    /**
     * Method to logout the user
     */
    @DeleteMapping
    public void logOutUser(HttpSession session) {
        logger.info("Log out for user: {}", activeUser);

        session.invalidate();
    }
}
