package com.epam.jtc.spring.datalayer.dto;

import java.util.Date;

public class Notification {
    private static final Date DEFAULT_DATE = new Date();
    
    private int id;
    
    private int bookId;
    private String searchText;
    private String category;
    
    private NotificationTypes type;
    
    private Date date = DEFAULT_DATE;
    
    public Notification(int id, int bookId, String searchText,
                        String category,
                        NotificationTypes type, Date date) {
        this.id = id;
        this.bookId = bookId;
        this.searchText = searchText;
        this.category = category;
        this.type = type;
        this.date = date;
    }
    
    public Notification() {
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getSearchText() {
        return searchText;
    }
    
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public NotificationTypes getType() {
        return type;
    }
    
    public void setType(NotificationTypes type) {
        this.type = type;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override public String toString() {
        return "Notification{" +
                   "id=" + id +
                   ", bookId=" + bookId +
                   ", searchText='" + searchText + '\'' +
                   ", category='" + category + '\'' +
                   ", type=" + type +
                   ", date=" + date +
                   '}';
    }
}
