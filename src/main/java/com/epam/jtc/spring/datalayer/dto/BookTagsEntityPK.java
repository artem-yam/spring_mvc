package com.epam.jtc.spring.datalayer.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookTagsEntityPK implements Serializable {
    
    private int book;
    private int tag;
    
    @ManyToOne
    @JoinColumn(name = "BOOK", referencedColumnName = "ID")
    private BooksEntity bookEntity;
    
    @ManyToOne
    @JoinColumn(name = "TAG", referencedColumnName = "ID")
    private AvailableTagsEntity tagEntity;
    
    @Column(name = "BOOK")
    public int getBook() {
        return book;
    }
    
    public void setBook(int book) {
        this.book = book;
    }
    
    @Column(name = "TAG")
    public int getTag() {
        return tag;
    }
    
    public void setTag(int tag) {
        this.tag = tag;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookTagsEntityPK)) {
            return false;
        }
        BookTagsEntityPK that = (BookTagsEntityPK) o;
        return getBook() == that.getBook() &&
                   getTag() == that.getTag() &&
                   Objects.equals(bookEntity, that.bookEntity) &&
                   Objects.equals(tagEntity, that.tagEntity);
    }
    
    @Override public int hashCode() {
        return Objects.hash(getBook(), getTag(), bookEntity, tagEntity);
    }
}