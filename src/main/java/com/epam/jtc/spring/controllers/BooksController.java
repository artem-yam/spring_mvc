package com.epam.jtc.spring.controllers;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private static final Logger logger = LogManager
            .getLogger(new Object() {
            }.getClass().getEnclosingClass());

    @RequestMapping(value = "/getAll")
            /*  headers = {"Content-Type=application/json",
                  "Accept=application/json"})*/
    //, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Book> getAllBooks() {
        List<Book> books =
                DAOFactory.getInstance(DataSourceType.ORACLE).getBookDAO()
                        .getAllBooks();
        //logger.info("All books: {}", books);
        return books;
    }

    @RequestMapping(value = "/add")
    public int addBook(@RequestBody Book newBook) {
        //logger.info("Adding new book {}", newBook);

        if (newBook.getImage() == null) {
            newBook.setImage(new Book().getImage());
        }
        if (newBook.getImage().contains(",")) {
            newBook.setImage(newBook.getImage()
                    .substring(
                            newBook.getImage().indexOf(',') + 1));
        }

        return DAOFactory.getInstance(DataSourceType.ORACLE).getBookDAO()
                .addBook(newBook.getTitle(), newBook.getAuthor(),
                        newBook.getImage());
    }

    @RequestMapping(value = "/changeRating")
    public int changeBookRating(@RequestBody Book book) {
        DAOFactory.getInstance(DataSourceType.ORACLE).getBookDAO()
                .changeRating(book.getId(), book.getRating());

        return book.getId();
    }
}
