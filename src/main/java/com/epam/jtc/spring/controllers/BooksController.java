package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Book> getAllBooks() {
        List<Book> books = dao.getAllBooks();
        //logger.info("All books: {}", books);
        return books;
    }
    
    /**
     * Adds the book to dao
     *
     * @param newBook book to add
     * @return added book id
     */
    @PostMapping
    public int addBook(@RequestBody Book newBook) {
        
        if (newBook.getImage() == null) {
            newBook.setImage(new Book().getImage());
        }
        if (newBook.getImage().contains(",")) {
            newBook.setImage(newBook.getImage()
                                 .substring(
                                     newBook.getImage().indexOf(',') + 1));
        }
        
        return dao.addBook(newBook.getTitle(), newBook.getAuthor(),
            newBook.getImage());
    }
    
    /**
     * Changes the book rating
     *
     * @param bookId    id of the book
     * @param newRating book's new rating
     * @return id of the book
     */
    @PostMapping("/{bookId}/rating")
    public int changeBookRating(@PathVariable int bookId,
                                @RequestBody int newRating) {
        //logger.info("New rating for book {} = {}", bookId, newRating);
        
        dao.changeRating(bookId, newRating);
        
        return bookId;
    }
}
