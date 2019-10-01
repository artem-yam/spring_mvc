package com.epam.jtc.spring.datalayer.dto;

/**
 * User entity
 */
public class User {

    /**
     * User login
     */
    private String login;

    /**
     * User password
     */
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

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
}
