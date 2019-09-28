package com.epam.jtc.spring.datalayer.dto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Book entity
 */
@Component
public class Book {

    /**
     * Default book rating
     */
    private static final int DEFAULT_RATING = 0;
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
     * Book imageBytes
     */
    private byte[] imageBytes = new byte[0];

    private String image = "";
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
     * Getter for imageBytes
     *
     * @return book's imageBytes
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    /**
     * Setter for imageBytes
     *
     * @param imageBytes new imageBytes for book
     */
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", image='" + image.substring(0, 10) + '\'' +
                ", rating=" + rating +
                ", tags=" + tags +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
