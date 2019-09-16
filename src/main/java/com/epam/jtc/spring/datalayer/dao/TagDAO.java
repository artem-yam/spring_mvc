package com.epam.jtc.spring.datalayer.dao;

import java.util.List;

public interface TagDAO {
    
    List<String> getAllTags();
    
    void addTag(String text);

    List<String> getBookTags(int bookId);
    
    void addTagToBook(int bookId, String tag);
}
