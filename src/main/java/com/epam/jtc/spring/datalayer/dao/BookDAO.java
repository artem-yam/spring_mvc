package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Book;

import java.io.File;
import java.util.List;

public interface BookDAO {
    
    List<Book> getAllBooks();
    
    //List<Book> searchBooks(String searchText);
    
    //List<Book> getMostPopular();
    
    void addBook(String title, String author, String coverImage);
}
