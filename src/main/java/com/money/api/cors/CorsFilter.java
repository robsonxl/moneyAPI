package com.money.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	private String origemPermitida = "http:localhost:8000";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Acess-Control-Allow-Origin","POST, GET, DELETE, PUT, OPTIONS");
		res.setHeader("Acess-Control-Allow-Methods", "Authorization, Content-type Accept");
		
		if(("OPTIONS".equalsIgnoreCase(req.getMethod()) && (origemPermitida.equals(res.getHeader("Origin"))))){
			res.setHeader("Acess-Control-Allow-Methods",origemPermitida);
			res.setHeader("Acess-Control-Allow-Credentials", "true");
			res.setHeader("Acess-Control-Max-Age", "3600");
			res.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(request, response);
		}
	}
}
