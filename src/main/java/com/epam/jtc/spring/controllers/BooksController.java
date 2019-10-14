package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.oracleDB.dao.OracleBookDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    private static final Logger logger =
            LogManager.getLogger(BooksController.class);

    private static final String DUPLICATE_KEY_ERROR_MESSAGE =
            "This book already exists";

    /**
     * DAO for operations with books
     */
    private BookDAO dao;

    @Autowired
    public BooksController(BookDAO dao) {
        this.dao = dao;
    }

    public BooksController() {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.scan("com.epam.jtc.spring.datalayer");
        ctx.refresh();

        dao = ctx.getBean(OracleBookDAO.class);
    }

    public BookDAO getDao() {
        return dao;
    }

    public void setDao(@Autowired BookDAO dao) {
        this.dao = dao;
    }

    /**
     * Gets all books from dao
     *
     * @return list of books
     */
    @GetMapping
    public List<Book> getAllBooks() {
        logger.debug("Getting all books with dao: {}", dao);

        return dao.getAllBooks();
    }

    /**
     * Adds the book to dao
     *
     * @return added book
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity addBook(@ModelAttribute("book") @Valid Book newBook,
                                  BindingResult bindingResult) {
        logger.info("Adding book : {}", newBook);

        List<String> errors = new ArrayList<>();
        Object toReturn = errors;

        if (bindingResult.hasErrors()) {
            logger.debug("Book {} is not valid", newBook);
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.debug(er);
                errors.add(er.getDefaultMessage());
            }
        } else {
            try {
                logger.info("Add try");

                newBook = dao.addBook(newBook.getTitle(), newBook.getAuthor(),
                        newBook.getImage() == null ? new byte[0] :
                                newBook.getImage().getBytes());

                toReturn = newBook;

                logger.info("Successful add");
            } catch (Exception ex) {
                String errorMessage = ex.getClass().getSimpleName();
                if (ex instanceof DuplicateKeyException) {
                    errorMessage = DUPLICATE_KEY_ERROR_MESSAGE;
                }

                logger.debug("Can't add new book", ex);

                errors.add(errorMessage);
            }
        }

        ResponseEntity responseEntity =
                new ResponseEntity<>(toReturn, HttpStatus.OK);

        logger.info("Add book method returns: {}", toReturn);

        return responseEntity;
    }

    /**
     * Changes the book rating
     *
     * @param bookId    id of the book
     * @param newRating book's new rating
     * @return book
     */
    @PostMapping("/{bookId}/rating")
    public Book changeBookRating(@PathVariable int bookId,
                                 @RequestBody int newRating)
            throws Exception {
        logger.info("Changing book {} rating to {}", bookId, newRating);

        return dao.changeRating(bookId, newRating);
    }

    /**
     * Gets image for the book
     *
     * @param bookId id of the book
     * @return bytes representation of the image
     */
    @GetMapping(value = "/{bookId}/image",
            produces = {MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE})
    public byte[] getBookImage(@PathVariable int bookId) throws Exception {
        logger.debug("Getting image for book {}", bookId);
        return dao.getBookImage(bookId);
    }

    /**
     * Deletes the book
     *
     * @param bookId id of the book to delete
     */
    @PostMapping("/{bookId}")
    public void deleteBook(@PathVariable int bookId) {
        logger.info("Deleting book {}", bookId);
        dao.deleteBook(bookId);
        logger.info("Successful deletion");
    }

}
