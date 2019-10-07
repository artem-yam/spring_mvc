package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener removes logged in user when session destroys
 */
@WebListener
@Component
public class SessionListener implements HttpSessionListener {

    private static final Logger logger =
            LogManager.getLogger(SessionListener.class);

    private User activeUser;

    @Autowired
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    @Override
    public void sessionCreated(HttpSessionEvent ev) {
        logger.info("New session created {} for {}", ev.getSession().getId(),
                activeUser);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent ev) {
        logger.info("Session destroyed {} for {}", ev.getSession().getId(),
                activeUser);
    }

}