package com.learningtech.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Not in use
//@Component
public class RequestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		System.out.println("==============preHandle========================");
		String requestURI = request.getRequestURI();
		System.out.println("========requestURI======"+requestURI+"========================");
		String tenantId = request.getHeader("X-TenantID");
		if (tenantId == null) {
			response.getWriter().write("X-Tenant-ID not present in header");
			response.setStatus(Integer.valueOf(String.valueOf(HttpStatus.BAD_REQUEST)));
			return false;
		}
		
		TenantContext.setCurrentTenant(tenantId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		TenantContext.clear();
	}
}
