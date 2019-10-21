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
     * Adds new tag
     *
     * @param text text of tag
     */
    void addTag(String text);

    /**
     * Binds tag to the book
     *
     * @param bookId id of the book
     * @param tag    tag to add
     * @return list of the book tags
     */
    List<String> bindTagToBook(int bookId, String tag);

    /**
     * Unbinds tag from the book
     *
     * @param bookId id of the book
     * @param tag    tag
     * @return list of the book tags
     */
    List<String> unbindTag(int bookId, String tag);
}
