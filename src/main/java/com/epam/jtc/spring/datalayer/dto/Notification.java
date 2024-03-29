package com.epam.jtc.spring.datalayer.dto;

import java.util.Date;
import java.util.Objects;

/**
 * Notification entity
 */
public class Notification {

    private int id;

    private int bookId;

    private String content;

    private String category;

    private NotificationTypes type = NotificationTypes.ADD_BOOK;

    private Date date = new Date();

    /**
     * Default constructor
     */
    public Notification() {
    }

    /**
     * Getter for related book id
     *
     * @return related book id
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Setter for related book id
     *
     * @param bookId new book id
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Getter for content
     *
     * @return content of notification
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter for content
     *
     * @param content new content for notification
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for category
     *
     * @return category of notification
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for category
     *
     * @param category new category for notification
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter for id
     *
     * @return notification's id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     *
     * @param id new id for notification
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for type
     *
     * @return type of notification
     */
    public NotificationTypes getType() {
        return type;
    }

    /**
     * Setter for type
     *
     * @param type new type for notification
     */
    public void setType(NotificationTypes type) {
        this.type = type;
    }

    /**
     * Getter for date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for date
     *
     * @param date new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", type=" + type +
                ", date=" + date +
                '}';
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
        Notification that = (Notification) o;

        if (!(Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getCategory(), that.getCategory()) &&
                Objects.equals(getDate(), that.getDate()))) {
            return false;
        }

        return getId() == that.getId() &&
                getBookId() == that.getBookId() &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBookId(), getContent(), getCategory(),
                getType(), getDate());
    }
}
