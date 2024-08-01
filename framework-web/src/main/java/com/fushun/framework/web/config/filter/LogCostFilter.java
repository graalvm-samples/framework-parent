package com.fushun.framework.web.config.filter;

import com.fushun.framework.util.util.UUIDUtil;
import com.fushun.framework.web.config.aspect.LogControllerAspect;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@Configuration
@WebFilter(urlPatterns = "/*",filterName = "logCostFilter")
@Order(Ordered.HIGHEST_PRECEDENCE+4)
public class LogCostFilter implements Filter {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    /**
     * 会话ID
     */
    public final static String MDC_KEY_REQ_ID = "request_id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        MDC.put(MDC_KEY_REQ_ID, UUIDUtil.getUUID().toString());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        //如果不存在就生成
        if (httpServletRequest.getHeader(MDC_KEY_REQ_ID) == null) {
            MDC.put(MDC_KEY_REQ_ID, UUIDUtil.getUUID().toString());
        } else {
            MDC.put(MDC_KEY_REQ_ID, httpServletRequest.getHeader(MDC_KEY_REQ_ID));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}