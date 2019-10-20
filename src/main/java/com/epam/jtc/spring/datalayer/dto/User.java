package com.epam.jtc.spring.datalayer.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * User entity
 */
@Component("user")
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
@Lazy(false)
public class User {

    @NotNull
    @Size(min = 5, max = 20)
    private String login = "";

    @NotNull
    @Size(min = 5, max = 20)
    private String pass = "";

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
     * @return pass
     */
    public String getPassword() {
        return pass;
    }

    /**
     * Password setter
     *
     * @param password pass
     */
    public void setPassword(String password) {
        this.pass = password;
    }

    public boolean isActive() {
        return (login.length() != 0);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", pass='" + pass + '\'' +
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
