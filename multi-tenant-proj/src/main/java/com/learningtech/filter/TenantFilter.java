package com.learningtech.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.learningtech.config.TenantContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String tenantName = req.getHeader("X-TenantID");
       
		if (tenantName == null) {
			String respStr = "{\r\n"
					+ "  \"status\":false,\r\n"
					+ "  \"statusCode\":400,\r\n"
					+ "  \"message\": \"X-TenantID not present in header\"\r\n"
					+ "}";
			resp.getWriter().write(respStr);
			resp.setStatus(HttpStatus.BAD_REQUEST.value());
			return;
		}
        TenantContext.setCurrentTenant(tenantName);
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
