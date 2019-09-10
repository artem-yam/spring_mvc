package com.epam.jtc.spring.datalayer.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "NOTIFICATION_TYPES", schema = "SYSTEM")
public class NotificationTypesEntity {
    private long id;
    private String type;
    
    @Id @Column(name = "ID", nullable = false, precision = 0)
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "TYPE", nullable = true, length = 50)
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationTypesEntity that = (NotificationTypesEntity) o;
        return id == that.id &&
                   Objects.equals(type, that.type);
    }
    
    @Override public int hashCode() {
        return Objects.hash(id, type);
    }
}
