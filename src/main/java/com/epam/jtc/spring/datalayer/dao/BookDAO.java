package com.epam.jtc.spring.datalayer.dao;

import com.epam.jtc.spring.datalayer.dto.Book;

import java.sql.SQLException;
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
     * @param bookId    id of the book
     * @param newRating new book rating
     */
    Book changeRating(int bookId, int newRating) throws Exception;
    
    /**
     * Gets book image
     *
     * @param bookId id of the book
     * @return images bytes string
     */
    byte[] getBookImage(int bookId) throws Exception;
    
    void deleteBook(int bookId);
}
