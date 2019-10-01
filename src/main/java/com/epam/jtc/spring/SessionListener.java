package com.epam.jtc.spring;

import com.epam.jtc.spring.controllers.UsersController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
@Component
public class SessionListener implements HttpSessionListener {
    
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    @Override
    public void sessionCreated(HttpSessionEvent ev) {
        logger.info("New session created {}", ev.getSession().getId());
        
        ev.getSession().setMaxInactiveInterval(10);
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent ev) {
        logger.info("Session destroyed {}", ev.getSession().getId());
        
        UsersController controller =
            (UsersController) ev.getSession().getAttribute("user_controller");
        
        logger.info("User controller {}", controller);
        
        if (controller != null) {
            try {
                controller.performDAOLogout(
                    (String) ev.getSession().getAttribute("logged_user"));
            } catch (Exception logoutException) {
                logger.warn("Cant logout user after session destroyed: ",
                    logoutException);
            }
        }
        
    }
    
}