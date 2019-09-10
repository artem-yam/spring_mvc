package com.epam.jtc.spring.datalayer.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "AVAILABLE_TAGS", schema = "SYSTEM")
public class AvailableTagsEntity implements Serializable {
    private long id;
    private String tag;
    
    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    //@GeneratedValue(strategy = GenerationType.TABLE)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Basic @Column(name = "TAG", nullable = true, length = 50)
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvailableTagsEntity that = (AvailableTagsEntity) o;
        return id == that.id &&
                   Objects.equals(tag, that.tag);
    }
    
    @Override public int hashCode() {
        return Objects.hash(id, tag);
    }
}
