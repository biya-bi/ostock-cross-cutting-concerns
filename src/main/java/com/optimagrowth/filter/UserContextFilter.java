package com.optimagrowth.filter;

import static com.optimagrowth.http.HeaderNames.AUTH_TOKEN;
import static com.optimagrowth.http.HeaderNames.CORRELATION_ID;
import static com.optimagrowth.http.HeaderNames.ORGANIZATION_ID;
import static com.optimagrowth.http.HeaderNames.USER_ID;

import java.io.IOException;

import com.optimagrowth.context.UserContext;
import com.optimagrowth.context.UserContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;

        var context = prepareContext(httpServletRequest);

        log.debug("Correlation ID is {}", context.getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    protected UserContext getContext() {
        return UserContextHolder.getContext();
    }

    private UserContext prepareContext(HttpServletRequest httpServletRequest) {
        var context = retrieveContext();

        context.setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        context.setUserId(httpServletRequest.getHeader(USER_ID));
        context.setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        context.setOrganizationId(httpServletRequest.getHeader(ORGANIZATION_ID));

        return context;
    }

    private UserContext retrieveContext() {
        var context = getContext();
        var currentContext = UserContextHolder.getContext();
        if (context == null || context == currentContext) {
            return currentContext;
        }
        UserContextHolder.setContext(context);
        return context;
    }

}