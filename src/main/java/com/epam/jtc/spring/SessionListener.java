package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener removes logged in user when session destroys
 */
@WebListener
@SessionAttributes("activeUser")
@Component
public class SessionListener implements HttpSessionListener {
    
    private static final Logger logger =
        LogManager.getLogger(SessionListener.class);
    
    @Autowired
    private User activeUser;
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
    
    public void setSessionRegistry(
        SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
    
    @Override
    public void sessionCreated(HttpSessionEvent ev) {
        if (activeUser == null) {
            activeUser = (User) getUserBean(ev, User.class);
        }
        /*sessionRegistry =
            (SessionRegistryImpl) getUserBean(ev, SessionRegistryImpl.class);
        logger.info("Ses info {}",
            sessionRegistry.getSessionInformation(ev.getSession().getId()));*/
        
        logger.info("New session created {} for {}",
            ev.getSession().getId(), activeUser);
        
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent ev) {
        logger.info("Session destroyed {} for {}", ev.getSession().getId(),
            activeUser);
        
    }
    
    private Object getUserBean(HttpSessionEvent event, Class beanClass) {
        HttpSession session = event.getSession();
        ApplicationContext ctx = WebApplicationContextUtils
                                     .getWebApplicationContext(
                                         session.getServletContext());
        return ctx.getBean(beanClass);
    }
    
}