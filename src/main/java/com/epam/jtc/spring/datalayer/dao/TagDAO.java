package com.epam.jtc.spring.datalayer.dao;

import java.util.List;

public interface TagDAO {

    List<String> getAllTags();

    void addTag(String text);

    void addTagToBook(int bookId, String tag);

    List<String> getBookTags(int bookId);
}
