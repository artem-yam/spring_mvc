package com.epam.jtc.spring.datalayer;

import com.epam.jtc.spring.datalayer.dto.Book;

import java.util.List;

public interface BookDAO {
    
    List<Book> getAllEmployees();
    
    Book createEmployee(String firstName, String lastName);
}
