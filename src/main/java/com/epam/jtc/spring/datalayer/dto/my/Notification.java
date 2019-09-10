package com.epam.jtc.spring.datalayer.dto.my;

import java.util.Date;

public class Notification {
    private static final Date DEFAULT_DATE = new Date();
    
    private int id;
    
    private Book book;
    private String searchText;
    private String category;
    
    private NotificationTypes type;
    
    private Date date = DEFAULT_DATE;
    
    public Notification(int id, Book book, String searchText,
                        String category,
                        NotificationTypes type) {
        this.id = id;
        this.book = book;
        this.searchText = searchText;
        this.category = category;
        this.type = type;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
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
}
