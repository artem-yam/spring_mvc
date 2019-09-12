package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dto.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    
    @RequestMapping(value = "/getAll")
      /*  headers = {"Content-Type=application/json",
            "Accept=application/json"})*/
    //produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Book> getAllBooks() {
        return DAOFactory.getInstance(DataSourceType.ORACLE).getBookDAO()
                   .getAllBooks();
    }
    
    public List<Book> searchBooks(String searchText) {
        return null;
    }
    
    public List<Book> getMostPopular() {
        return null;
    }
    
    public void addBook(String title, String author, File coverImage) {
    }
}
