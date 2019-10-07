package com.epam.jtc.spring;

import org.apache.logging.log4j.CloseableThreadContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Adds session ID as parameter
 */
public class SessionFilter implements Filter {
    private static final String SESSION_ID_PARAMETER_NAME = "sessionId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try (final CloseableThreadContext.Instance ctc =
                     CloseableThreadContext.put(SESSION_ID_PARAMETER_NAME,
                             ((HttpServletRequest) request).getSession()
                                     .getId())) {
            request.setCharacterEncoding("UTF-8");

            chain.doFilter(request, response);
        }
    }
}