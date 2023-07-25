package org.inneo.auth.config;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;


import org.springframework.lang.NonNull;
import org.inneo.auth.repository.TokenRep;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRep tokenRep;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {
    	final String authHeader = request.getHeader("Authorization");
    	final String jwt;
    	final String username;
    	
    	if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
    		filterChain.doFilter(request, response);
    		return;
    	}
    	
    	try {
    		jwt = authHeader.substring(7);	
    		username = jwtService.extractUsername(jwt); 	 
    		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);		 
    			var isTokenValid = tokenRep.findByToken(jwt).map(tk -> !tk.isExpired() && !tk.isRevoked()).orElse(false);
	
    			if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
    				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
	        
    				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));				 
    				SecurityContextHolder.getContext().setAuthentication(authToken);				 
    			}
    		}	 
		 	filterChain.doFilter(request, response);
		 	
    	}catch (Exception e) {	    		  
    		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		Map<String, String> unauthorized = new HashMap<>();
    		unauthorized.put("unauthorized", "Token invalido ou expirado!");
    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    		new ObjectMapper().writeValue(response.getOutputStream(), unauthorized);    		
    	}		 	
	}		
}
