package com.fushun.framework.security.strategy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.DefaultRedirectStrategy;

import java.io.IOException;

public class MyDefaultRedirectStrategy extends DefaultRedirectStrategy {

    public MyDefaultRedirectStrategy() {
        super();
    }

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                             String url) throws IOException {
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        if (logger.isDebugEnabled()) {
            logger.debug("Redirecting to '" + redirectUrl + "'");
        }

        response.sendRedirect(redirectUrl);
    }
}
