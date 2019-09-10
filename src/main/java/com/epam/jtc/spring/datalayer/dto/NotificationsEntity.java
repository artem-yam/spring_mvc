package com.epam.jtc.spring.datalayer.dto;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "NOTIFICATIONS", schema = "SYSTEM")
public class NotificationsEntity {
    private long id;
    private String searchText;
    private String category;
    private String type;
    private Time date;
    
    @Id @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "SEARCH_TEXT", nullable = true, length = 50)
    public String getSearchText() {
        return searchText;
    }
    
    public void setSearchText(String searchtext) {
        this.searchText = searchtext;
    }
    
    @Basic
    @Column(name = "CATEGORY", nullable = true, length = 50)
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    @Basic
    @Column(name = "TYPE", nullable = false, length = 50)
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Basic @Column(name = "date", nullable = true)
    public Time getDate() {
        return date;
    }
    
    public void setDate(Time date) {
        this.date = date;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationsEntity that = (NotificationsEntity) o;
        return id == that.id &&
                   Objects.equals(searchText, that.searchText) &&
                   Objects.equals(category, that.category) &&
                   Objects.equals(type, that.type) &&
                   Objects.equals(date, that.date);
    }
    
    @Override public int hashCode() {
        return Objects.hash(id, searchText, category, type, date);
    }
}
