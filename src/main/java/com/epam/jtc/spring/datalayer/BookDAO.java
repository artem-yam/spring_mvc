package com.epam.jtc.spring.datalayer;

import com.epam.jtc.spring.datalayer.dto.my.Book;

import java.util.List;

public interface BookDAO {
    
    List<Book> getAllEmployees();
    
    Book createEmployee(String firstName, String lastName);
}
