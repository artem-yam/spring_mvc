package com.epam.jtc.spring.datalayer.dto;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * User entity
 */
@Component("user")
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
@Lazy(false)
public class User implements Serializable {

    /**
     * User login
     */
    @NotNull
    @Size(min = 5, max = 20)
    private String login = "";

    /**
     * User password
     */
    @NotNull
    @Size(min = 5, max = 20)
    private String password = "";

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

    public boolean isActive() {
        return (login.length() != 0 && password.length() != 0);
    }

    public void reset() {
        this.login = "";
        this.password = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword());
    }
}
