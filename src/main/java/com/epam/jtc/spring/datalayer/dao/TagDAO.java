package com.epam.jtc.spring.datalayer.dao;

import java.util.List;

/**
 * DAO for tags
 */
public interface TagDAO {
    
    /**
     * Gets all tags
     *
     * @return list of tags
     */
    List<String> getAllTags();
    
    /**
     * Gets tags of the book
     *
     * @param bookId id of the book
     * @return list of tags of the book
     */
    List<String> getBookTags(int bookId);
    
    /**
     * Adds tag to the book
     *
     * @param bookId id of the book
     * @param tag    tag to add
     */
    List<String> addTagToBook(int bookId, String tag);
}
