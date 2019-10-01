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

/**
 * Controller for books
 */
@RestController
@RequestMapping("/users")
public class UsersController {

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
        logger.info("Start login for user: {} {}", login, password);

        dao.loginUser(login, password);

        logger.info("User logged in");

        request.getSession().setAttribute("logged_user", login);
    }

    @PostMapping(value = "/logout")
    public void logoutUser(HttpServletRequest request,
                           @RequestParam("login") String login)
            throws Exception {
        dao.logoutUser(login);

        request.getSession().removeAttribute("logged_user");
    }

}
