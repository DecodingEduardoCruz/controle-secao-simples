package org.inneo.auth.config;

import lombok.RequiredArgsConstructor;
import org.inneo.auth.repository.TokenRep;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
	private final TokenRep tokenRep;

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    
    jwt = authHeader.substring(7);
    
    var storedToken = tokenRep.findByToken(jwt).orElse(null);
    
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      tokenRep.save(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
