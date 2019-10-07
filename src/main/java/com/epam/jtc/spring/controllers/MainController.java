package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller for books
 */
@Controller
public class MainController {

    private static final Logger logger =
            LogManager.getLogger(MainController.class);

    private BookDAO dao;


    @Autowired
    public MainController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                .getBookDAO();
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String init(Model model) {

        model.addAttribute("book", new Book());
        //ModelAndView mv = new ModelAndView("index", "book", new Book());

        logger.info("Init model = {}", model);
        //logger.info("Init model = {}", mv);

        return "lib";
        //return mv;
    }

    @PostMapping(value = "/books",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addBook(@ModelAttribute("book") @Valid Book newBook,
                          BindingResult bindingResult,
                          HttpServletRequest request,
                          HttpServletResponse response
    ) {
        logger.info("Adding book : {}", newBook);

        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
            }
        } else {
            dao.addBook(newBook.getTitle(), newBook.getAuthor(),
                    newBook.getImage().getBytes());
            //return "forward:books/add";
        }
        return "lib";
    }

}
