package com.epam.jtc.spring.datalayer.dto.my;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Book {
    private static final String DEFAULT_IMAGE_PATH =
        "web/resources/images/noCover.jpg";
    private static final int DEFAULT_RATING = 0;
    
    private int id;
    private String title;
    private String author;
    
    //private String imagePath = DEFAULT_IMAGE_PATH;
    private File image = new File(DEFAULT_IMAGE_PATH);
    
    private int rating = DEFAULT_RATING;
    private Set<String> tags = new HashSet<>();
    
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public File getImage() {
        return image;
    }
    
    public void setImage(File image) {
        this.image = image;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public Set<String> getTags() {
        return tags;
    }
    
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
