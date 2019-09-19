package com.epam.jtc.spring.datalayer.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Book entity
 */
public class Book {
    
    /**
     * Default book rating
     */
    private static final int DEFAULT_RATING = 0;
    
    /**
     * Default image coded in base64
     */
    private static final String DEFAULT_IMAGE_BASE64_CODE =
        "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCAD8ALYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD+/iiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACv5YP8Agux/wUf/AGz/ANjX9pH4U+Af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";
    
    /**
     * Book id
     */
    private int id;
    
    /**
     * Book title
     */
    private String title;
    
    /**
     * Book author
     */
    private String author;
    
    /**
     * Book image
     */
    private String image = DEFAULT_IMAGE_BASE64_CODE;
    
    /**
     * Book rating
     */
    private int rating = DEFAULT_RATING;
    
    /**
     * Book tags
     */
    private List<String> tags = new ArrayList<>();
    
    /**
     * is book deleted
     */
    private boolean isDeleted = false;
    
    /**
     * Default constructor
     */
    public Book() {
    }
    
    /**
     * Getter for boolean isDeleted
     *
     * @return isDeleted
     */
    public boolean isDeleted() {
        return isDeleted;
    }
    
    /**
     * Setter for isDeleted
     *
     * @param deleted is book was deleted
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    
    /**
     * Getter for image
     *
     * @return book's image
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Setter for image
     *
     * @param image new image for book
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Getter for id
     *
     * @return book's id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Setter for id
     *
     * @param id new id for book
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Getter for title
     *
     * @return book's title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Setter for title
     *
     * @param title new title for book
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Getter for author
     *
     * @return book's author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Setter for author
     *
     * @param author new author for book
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Getter for rating
     *
     * @return book's rating
     */
    public int getRating() {
        return rating;
    }
    
    /**
     * Setter for rating
     *
     * @param rating new rating for book
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    /**
     * Getter for tags
     *
     * @return book's tags list
     */
    public List<String> getTags() {
        return tags;
    }
    
    /**
     * Setter for tags
     *
     * @param tags new tags list for book
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
