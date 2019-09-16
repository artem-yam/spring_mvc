package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Book;

import java.util.List;

public interface BookDAO {
    
    List<Book> getAllBooks();
    
    //List<Book> searchBooks(String searchText);
    
    //List<Book> getMostPopular();
    
    int addBook(String title, String author, String coverImage);
}
