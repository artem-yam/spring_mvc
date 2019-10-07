package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Checks session for logged in user
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger =
            LogManager.getLogger(LoginInterceptor.class);

    private User activeUser;

    @Autowired
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);

        logger.info("Request path = {}", request.getContextPath());

        if (session == null) {
            response.sendRedirect(request.getContextPath());
            return false;
        } else {
            logger.info("Session user = {}", activeUser);

            if (!activeUser.isActive()) {
                logger.info("Redirecting to user authorization");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Not logged in user");
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
