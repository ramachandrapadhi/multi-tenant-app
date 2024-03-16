package com.learningtech.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.learningtech.config.TenantContext;
import com.learningtech.respmodel.ErrorResponse;
import com.learningtech.util.HeaderMapRequestWrapper;
import com.learningtech.util.JSONConveter;
import com.learningtech.util.TanentId;

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
        resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
        String tenantName = req.getHeader("X-TenantID");
       
		if (tenantName == null) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(Boolean.FALSE);
			errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			errorResponse.setMessage("X-TenantID not present in header");
			
			resp.getWriter().write(JSONConveter.objToJson(errorResponse));
			resp.setStatus(HttpStatus.BAD_REQUEST.value());
			return;
		}
        TenantContext.setCurrentTenant(tenantName);
        try {
        	HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
        	
        	Long tanentId = getTanentId(tenantName);
        	if(tanentId==null) {
        		ErrorResponse errorResponse = new ErrorResponse();
    			errorResponse.setStatus(Boolean.FALSE);
    			errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
    			errorResponse.setMessage("Invalid tenant type");
    			
    			resp.getWriter().write(JSONConveter.objToJson(errorResponse));
    			resp.setStatus(HttpStatus.BAD_REQUEST.value());
    			return;
        	}
        	requestWrapper.addHeader("tenantId",String.valueOf(tanentId));
            chain.doFilter(requestWrapper, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
    
	private Long getTanentId(String typeOfTenent) {
		typeOfTenent = typeOfTenent.toLowerCase();
		switch (typeOfTenent) {
		case "admin":
			return TanentId.TANENT_TYPE_ADMIN.tId;
		case "hr":
			return TanentId.TANENT_TYPE_HR.tId;
		case "finance":
			return TanentId.TANENT_TYPE_FINANCE.tId;
		case "technical":
			return TanentId.TANENT_TYPE_TECHNICAL.tId;
		}
		return null;
	}
}
