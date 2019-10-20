package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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

    private static final Logger logger =
            LogManager.getLogger(BooksController.class);

    private static final String DUPLICATE_KEY_ERROR_MESSAGE =
            "This book already exists";
    private static final String BOOK_ADD_ERROR_MESSAGE =
            "Server error. Can't add the book";

    private BookDAO dao;

    @Autowired
    public BooksController(BookDAO dao) {
        this.dao = dao;
    }

    public BookDAO getDao() {
        return dao;
    }

    public void setDao(@Autowired BookDAO dao) {
        this.dao = dao;
    }

    /**
     * Gets books from dao
     *
     * @param filterName some filter
     * @param searchText text to search
     * @return list of books
     */
    @GetMapping
    public List<Book> getBooks(@Nullable @RequestParam String filterName,
                               @Nullable @RequestParam String searchText) {
        logger.info("Filter books with: filter \'{}\', search text = \'{}\'",
                filterName, searchText);

        List<Book> booksList;

        if (Strings.isBlank(filterName) && Strings.isBlank(searchText)) {
            booksList = dao.getAllBooks();
        } else {
            booksList = dao.filterBooks(filterName, searchText);
        }

        return booksList;
    }

    /**
     * Adds the book to dao
     *
     * @return added book
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity addBook(@Valid Book newBook,
                                  BindingResult bindingResult) {

        logger.info("Adding book : {}", newBook);

        List<String> errors = new ArrayList<>();
        ResponseEntity responseEntity =
                new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);

        if (bindingResult.hasErrors()) {
            logger.debug("Book {} is not valid", newBook);
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.debug(er);
                errors.add(er.getDefaultMessage());
            }
        } else {
            try {
                newBook = dao.addBook(newBook.getTitle(), newBook.getAuthor(),
                        newBook.getImage() == null ? new byte[0] :
                                newBook.getImage().getBytes());

                responseEntity =
                        new ResponseEntity<>(newBook, HttpStatus.OK);
            } catch (Exception ex) {
                String errorMessage = BOOK_ADD_ERROR_MESSAGE;
                if (ex instanceof DuplicateKeyException) {
                    errorMessage = DUPLICATE_KEY_ERROR_MESSAGE;
                }

                logger.debug("Can't add new book", ex);

                errors.add(errorMessage);
            }
        }

        logger.info("Add book method returns: {}", responseEntity);

        return responseEntity;
    }

    /**
     * Changes the book rating
     *
     * @param bookId      id of the book
     * @param changedBook book object with changed info
     * @return book
     */
    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable int bookId,
                           @RequestBody Book changedBook)
            throws Exception {
        logger.info("Changing book {} data to {}", bookId, changedBook);

        return dao.updateBook(bookId, changedBook);
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
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable int bookId) {
        logger.info("Deleting book {}", bookId);
        dao.deleteBook(bookId);
        logger.info("Successful deletion");
    }
}
