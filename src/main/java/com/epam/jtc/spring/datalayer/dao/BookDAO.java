package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Book;

import java.util.List;

/**
 * DAO for books
 */
public interface BookDAO {

    /**
     * Gets all books
     *
     * @return list of books
     */
    List<Book> getAllBooks();

    /**
     * Filters books with the filter and performs search
     *
     * @param filter     filter name
     * @param searchText text to search
     * @return filtered list of books
     */
    List<Book> filterBooks(String filter, String searchText);

    /**
     * Adds new book
     *
     * @param title      title of the book
     * @param author     author of the book
     * @param coverImage image of the book
     * @return new book id
     */
    Book addBook(String title, String author, byte[] coverImage);

    /**
     * Changes rating of the book
     *
     * @param bookId      id of the book
     * @param changedBook book with changes
     */
    Book updateBook(int bookId, Book changedBook) throws Exception;

    /**
     * Gets book image
     *
     * @param bookId id of the book
     * @return images bytes string
     */
    byte[] getBookImage(int bookId) throws Exception;

    /**
     * Deletes book
     *
     * @param bookId id of the book
     */
    void deleteBook(int bookId);
}
