package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for books
 */
@Controller
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
    
    /**
     * Constructor
     *
     * @param dataSourceType type of data source
     */
    
    @Autowired
    public BooksController(DataSourceType dataSourceType) {
        dao = DAOFactory.getInstance(dataSourceType)
                  .getBookDAO();
    }
    
    /**
     * Gets all books from dao
     *
     * @return list of books
     */
    @GetMapping
    @ResponseBody
    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }
    
    /**
     * Adds the book to dao
     *
     * @return added book
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addBook(@ModelAttribute("book") @Valid Book newBook,
                          BindingResult bindingResult, Model model) {
        logger.info("Adding book : {}", newBook);
        model.addAttribute("addedBook", null);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError er : bindingResult.getAllErrors()) {
                logger.warn(er);
            }
        } else {
            try {
                Book book = dao.addBook(newBook.getTitle(), newBook.getAuthor(),
                    newBook.getImage().getBytes());
                
                model.addAttribute("addedBook", book);
                
            } catch (Exception ex) {
                String errorMessage = ex.getClass().getSimpleName();
                if (ex instanceof DuplicateKeyException) {
                    errorMessage = DUPLICATE_KEY_ERROR_MESSAGE;
                }
                bindingResult.addError(
                    new ObjectError(newBook.getClass().getSimpleName(),
                        errorMessage));
                logger.warn(ex);
            }
        }
        return "lib";
    }
    
    /**
     * Changes the book rating
     *
     * @param bookId    id of the book
     * @param newRating book's new rating
     * @return book
     */
    @PostMapping("/{bookId}/rating")
    @ResponseBody
    public Book changeBookRating(@PathVariable int bookId,
                                 @RequestBody int newRating)
        throws Exception {
        //logger.info("New rating for book {} = {}", bookId, newRating);
        
        return dao.changeRating(bookId, newRating);
    }
    
    /**
     * Gets image for the book
     *
     * @param bookId id of the book
     * @return bytes representation of the image
     */
    @GetMapping(value = "/{bookId}/image",
        produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getBookImage(@PathVariable int bookId) {
        return dao.getBookImage(bookId);
    }
    
    /**
     * Deletes the book
     *
     * @param bookId id of the book to delete
     */
    @PostMapping("/{bookId}")
    @ResponseBody
    public void deleteBook(@PathVariable int bookId) {
        logger.info("Deleting book {}", bookId);
        dao.deleteBook(bookId);
    }
    
}
