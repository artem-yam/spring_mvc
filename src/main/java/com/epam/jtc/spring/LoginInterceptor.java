package com.epam.jtc.spring;

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
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
        throws Exception {
        
        HttpSession session = request.getSession(false);
        
        /*if (session == null) {
            response.sendRedirect(request.getContextPath());
            return false;
        } else {
            Object loggedUser = session.getAttribute("logged_user");
            
            if (loggedUser == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Not logged in user");
                return false;
            }
        }*/
        
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
