package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for books
 */
@Controller
public class MainController {

    private static final Logger logger =
            LogManager.getLogger(MainController.class);


    private User activeUser;

    @Autowired
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String init(Model model) {

        model.addAttribute("book", new Book());
        model.addAttribute("activeUser", activeUser);
        logger.debug("Initial model = {}", model);

        return "lib";
    }

}
