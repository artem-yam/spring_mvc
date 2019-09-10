package com.epam.jtc.spring.datalayer.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BOOK_TAGS", schema = "SYSTEM")
public class BookTagsEntity implements Serializable {
    
    private BookTagsEntityPK id;
    
    @EmbeddedId
    public BookTagsEntityPK getId() {
        return id;
    }
    
    public void setId(BookTagsEntityPK id) {
        this.id = id;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookTagsEntity)) {
            return false;
        }
        BookTagsEntity that = (BookTagsEntity) o;
        return Objects.equals(getId(), that.getId());
    }
    
    @Override public int hashCode() {
        return Objects.hash(getId());
    }
}
