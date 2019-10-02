package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for books
 */
@RestController
@RequestMapping("/books")
public class BooksController {

    /**
     * logger for class
     */
    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass().getEnclosingClass());

    private BookDAO dao;

    private Validator bookValidator;

    @Autowired
    public BooksController(DataSourceType dataSourceType,
                           @Autowired @Qualifier("bookValidator")
                                   Validator bookValidator) {
        dao = DAOFactory.getInstance(dataSourceType)
                .getBookDAO();

        this.bookValidator = bookValidator;
    }

    @InitBinder
    public void bindValidator(WebDataBinder binder) {
        binder.setValidator(bookValidator);
    }

    /**
     * Gets all books from dao
     *
     * @return list of books
     */
    @GetMapping
    public List<Book> getAllBooks() {
        List<Book> books = dao.getAllBooks();
        //logger.info("All books: {}", books);
        return books;
    }

    /**
     * Adds the book to dao
     *
     * @return added book id
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Book addBook(@Valid Book newBook, BindingResult bindingResult
    ) throws Exception {
        logger.info("Adding book : {}", newBook);

        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
            }
            throw new Exception("Book is not valid");
        }

        return dao.addBook(newBook.getTitle(), newBook.getAuthor(),
                newBook.getImage().getBytes());
    }

    /**
     * Changes the book rating
     *
     * @param bookId    id of the book
     * @param newRating book's new rating
     * @return id of the book
     */
    @PostMapping("/{bookId}/rating")
    public Book changeBookRating(@PathVariable int bookId,
                                 @RequestBody int newRating) {
        //logger.info("New rating for book {} = {}", bookId, newRating);

        return dao.changeRating(bookId, newRating);
    }

    @GetMapping(value = "/{bookId}/image")
    public byte[] getBookImage(@PathVariable int bookId) {
        return dao.getBookImage(bookId);
    }

    @PostMapping("/{bookId}")
    public void deleteBook(@PathVariable int bookId) {
        logger.info("Deleting book {}", bookId);
        dao.deleteBook(bookId);
    }

}
