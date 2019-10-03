package com.epam.jtc.spring.datalayer.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * User entity
 */
public class User {
    
    /**
     * User login
     */
    @NotNull
    @Size(min = 5, max = 20)
    private String login;
    
    /**
     * User password
     */
    @NotNull
    @Size(min = 5, max = 20)
    private String password;
    
    /**
     * Is user online
     */
    private boolean isOnline;
    
    /**
     * Default constructor
     */
    public User() {
    }
    
    /**
     * Login getter
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Login setter
     *
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Password getter
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Password setter
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Getter for boolean 'is online' field
     *
     * @return is online
     */
    public boolean isOnline() {
        return isOnline;
    }
    
    /**
     * Setter for boolean 'is online' field
     *
     * @param online is online
     */
    public void setOnline(boolean online) {
        isOnline = online;
    }
    
    @Override
    public String toString() {
        return "User{" +
                   "login='" + login + '\'' +
                   ", password='" + password + '\'' +
                   ", isOnline=" + isOnline +
                   '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return isOnline() == user.isOnline() &&
                   Objects.equals(getLogin(), user.getLogin()) &&
                   Objects.equals(getPassword(), user.getPassword());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), isOnline());
    }
}
