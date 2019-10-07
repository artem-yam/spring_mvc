package com.epam.jtc.spring.datalayer.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Book entity
 */
@Component("book")
public class Book implements Serializable {

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

    private CommonsMultipartFile image;
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
     * Image getter
     *
     * @return {@link CommonsMultipartFile} image file
     */
    public CommonsMultipartFile getImage() {
        return image;
    }

    /**
     * Image setter
     *
     * @param image {@link CommonsMultipartFile} image file
     */
    public void setImage(CommonsMultipartFile image) {
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
        return "{id:" + id +
                ", title:'" + title + '\'' +
                ", author:'" + author + '\'' +
                ", image:" + image +
                ", rating:" + rating +
                ", tags:" + tags +
                ", isDeleted:" + isDeleted + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return getId() == book.getId() &&
                getRating() == book.getRating() &&
                isDeleted() == book.isDeleted() &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getImage(), book.getImage()) &&
                Objects.equals(getTags(), book.getTags());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(getId(), getTitle(), getAuthor(), getImage(),
                        getRating(),
                        getTags(), isDeleted());
    }
}
