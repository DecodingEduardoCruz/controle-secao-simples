package org.inneo.auth.aplication;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.inneo.auth.dtos.AuthenticationRequest;
import org.inneo.auth.dtos.AuthenticationResponse;
import org.inneo.auth.dtos.RegisterRequest;
import org.inneo.auth.dtos.UsuarioDto;
import org.inneo.auth.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody  @Valid RegisterRequest request) {
	   return ResponseEntity.ok(service.register(request));
	}
  
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody  @Valid AuthenticationRequest request) {
	   return ResponseEntity.ok(service.authenticate(request));
	}	
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		List<UsuarioDto> usuarios = service.findAll().stream().map(UsuarioDto::new).toList();
		return ResponseEntity.ok(usuarios);
	}
}
