package com.epam.jtc.spring.datalayer.dto;

import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "BOOKS", schema = "SYSTEM")
public class BooksEntity implements Serializable {
    private long id;
    private String title;
    private String author;
    private byte[] image;
    private Long rating;
    private NumericBooleanType isDeleted;
    
    public NumericBooleanType getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(NumericBooleanType isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    @Id @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "TITLE", nullable = false, length = 50)
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Basic
    @Column(name = "AUTHOR", nullable = false, length = 50)
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Basic @Column(name = "IMAGE", nullable = true)
    public byte[] getImage() {
        return image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    
    @Basic
    @Column(name = "RATING", nullable = true, precision = 0)
    public Long getRating() {
        return rating;
    }
    
    public void setRating(Long rating) {
        this.rating = rating;
    }
    
    @Basic
    @Column(name = "IS_DELETED", nullable = true, precision = 0)
    @Type(type="org.hibernate.type.NumericBooleanType")
    public NumericBooleanType getDeleted() {
        return isDeleted;
    }
    
    public void setDeleted(NumericBooleanType deleted) {
        isDeleted = deleted;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BooksEntity that = (BooksEntity) o;
        return id == that.id &&
                   Objects.equals(title, that.title) &&
                   Objects.equals(author, that.author) &&
                   Arrays.equals(image, that.image) &&
                   Objects.equals(rating, that.rating) &&
                   Objects.equals(isDeleted, that.isDeleted);
    }
    
    @Override public int hashCode() {
        int result = Objects.hash(id, title, author, rating, isDeleted);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
