package org.fahad.spring.core.request;

import org.apache.catalina.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultServiceFilter implements Filter{
    private final String TENANT_ID = "tenantId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("options")) {
//                setCors(servletResponse);
                servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
//            setCors(servletResponse);
            if (!isOpenCall(servletRequest) && !isHealthCall(servletRequest)) {
                UserContext context = validateAndGetUserContextFromRequest((HttpServletRequest) request);
                UserContextProvider.set(context);
            }
            chain.doFilter(request, response);
        } finally {
            UserContextProvider.unsetContext();
        }
    }

    private UserContext validateAndGetUserContextFromRequest(HttpServletRequest request) {
        String tenantId = request.getHeader(TENANT_ID);
        UserContext userContext = new UserContext();
        if(tenantId!=null){
            userContext.setTenantId(tenantId);
        }
        return userContext;
    }

    private boolean isHealthCall(HttpServletRequest servletRequest) {
        return servletRequest.getRequestURI().startsWith("/p/health");
    }

    private boolean isOpenCall(HttpServletRequest servletRequest) {
        return servletRequest.getRequestURI().startsWith("/p/");
    }
}
