 package com.money.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest  req = (HttpServletRequest) request;

		if("/oauth/token".equalsIgnoreCase(req.getRequestURI()) && ("refresh_token".equalsIgnoreCase(req.getParameter("grant_type")) && req.getCookies()!=null)) {
			for(Cookie cookie: req.getCookies() ) {
				if(cookie.getName().equalsIgnoreCase("refreshtoken")) {
					String refreshToken = cookie.getValue(); 
					req = new RefreshTokenServletRequesteWrapper(req, refreshToken); //TODO: altera para uma classe de instancia.
				}
			}  
		}
		chain.doFilter(req, response);
	}
	
	static class RefreshTokenServletRequesteWrapper extends HttpServletRequestWrapper{
		private String refreshToken;
		public RefreshTokenServletRequesteWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> refreshTokenMap =  new ParameterMap<>();
			refreshTokenMap.putAll(getRequest().getParameterMap());
			refreshTokenMap.put("refresh_token",new String[] {refreshToken});
			refreshTokenMap.setLocked(true);
			return refreshTokenMap;
		}
	}
}
