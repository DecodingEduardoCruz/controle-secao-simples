package org.inneo.auth.services;


import java.util.Date;
import java.util.List;

import org.inneo.auth.config.JwtService;
import org.inneo.auth.dtos.AuthRequest;
import org.inneo.auth.dtos.AuthResponse;
import org.inneo.auth.dtos.RegisterRequest;
import org.inneo.auth.exceptions.ObjectDefaultException;
import org.inneo.auth.model.enums.TokenType;
import org.inneo.auth.model.role.Role;
import org.inneo.auth.model.token.Token;
import org.inneo.auth.model.usuario.Usuario;
import org.inneo.auth.repository.TokenRep;
import org.inneo.auth.repository.UsuarioRep;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UsuarioRep usuarioRep;
	private final TokenRep tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse register(RegisterRequest request) {
		
		if(request.getUsername() == null && usuarioRep.findByUsername(request.getUsername()) != null) {
			throw new ObjectDefaultException("Username indisponível.");
		}
		
	    var usuario = Usuario.builder()
	        .username(request.getUsername())
	        .password(passwordEncoder.encode(request.getPassword()))
	        .role(Role.ROLE_USER)
	        .build();
	    var savedUser = usuarioRep.save(usuario);
	    var jwtToken = jwtService.generateToken(usuario);
	    saveUsuarioToken(savedUser, jwtToken);
	    
	    return AuthResponse.builder()
	        .token(jwtToken)
	        .build();
	  }

	  public AuthResponse authenticate(AuthRequest request) {
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            request.getUsername(),
	            request.getPassword()
	        )
	    );
	    var usuario = usuarioRep.findByUsername(request.getUsername())
	        .orElseThrow();
	    var jwtToken = jwtService.generateToken(usuario);
	    revokeAllUsuarioTokens(usuario);
	    saveUsuarioToken(usuario, jwtToken);
	    return AuthResponse.builder()
	        .token(jwtToken)
	        .build();
	  }

	  private void saveUsuarioToken(Usuario usuario, String jwtToken) {
	    var token = Token.builder()
	        .usuario(usuario)
	        .token(jwtToken)
	        .tokenType(TokenType.BEARER)
	        .expired(false)
	        .revoked(false)
	        .dataCadastro(new Date())	        
	        .build();
	    tokenRepository.save(token);
	  }

	  private void revokeAllUsuarioTokens(Usuario usuario) {
	    var validUserTokens = tokenRepository.findAllValidTokenByUsuario(usuario.getId());
	    if (validUserTokens.isEmpty()) return;
	    
	    validUserTokens.forEach(token -> {
	      token.setExpired(true);
	      token.setRevoked(true);
	      token.setDataInativacao(new Date());
	    });
	    tokenRepository.saveAll(validUserTokens);
	  }
	  
	  public List<Usuario> findAll() {
		  return usuarioRep.findAll();
	  }
}
