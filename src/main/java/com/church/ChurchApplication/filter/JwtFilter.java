package com.church.ChurchApplication.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.church.ChurchApplication.jwtService.JwtService;
import com.church.ChurchApplication.service.MyUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) 
		{
			token = authHeader.substring(7);
			userName = jwtService.extractUserName(token);
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(userName);
			
			if(jwtService.validateToken(token,userDetails))
			{
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
//	 @Override
//	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//	            throws ServletException, IOException {
//	        String authHeader = request.getHeader("Authorization");
//	        String token = null;
//	        String userIdentifier = null;
//
//	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//	            token = authHeader.substring(7);
//	            userIdentifier = jwtService.extractUserIdentifier(token);
//	        }
//
//	        if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//	            UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(userIdentifier);
//
//	            if (jwtService.validateToken(token, userDetails)) {
//	                UsernamePasswordAuthenticationToken authToken = 
//	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                SecurityContextHolder.getContext().setAuthentication(authToken);
//	            }
//	        }
//	        filterChain.doFilter(request, response);
//	    }
}
