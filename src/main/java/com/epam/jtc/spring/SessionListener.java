package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
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

    @Override
    public void sessionCreated(HttpSessionEvent ev) {
        activeUser = getUserBean(ev);

        logger.info("New session created {}", ev.getSession().getId());


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent ev) {
        logger.info("Session destroyed {} for {}", ev.getSession().getId(),
                activeUser);

    }

    private User getUserBean(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ApplicationContext ctx = WebApplicationContextUtils
                .getWebApplicationContext(
                        session.getServletContext());
        return ctx.getBean(User.class);
    }

}