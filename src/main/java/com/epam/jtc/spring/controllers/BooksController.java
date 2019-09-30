package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     * @return added book id
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book addBook(@RequestParam("title") String title,
                        @RequestParam("author") String author,
                        @RequestParam("image") MultipartFile image
    ) throws IOException {
        return dao.addBook(title, author,
            image.getBytes());
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
        //logger.info("getting image for book {}", bookId);
        
        return dao.getBookImage(bookId);
    }
    
}
