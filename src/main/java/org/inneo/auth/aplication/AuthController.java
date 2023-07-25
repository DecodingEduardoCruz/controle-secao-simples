package org.inneo.auth.aplication;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.inneo.auth.dtos.AuthRequest;
import org.inneo.auth.dtos.AuthResponse;
import org.inneo.auth.dtos.RegisterRequest;
import org.inneo.auth.dtos.UsuarioDto;
import org.inneo.auth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
@RequestMapping("/api/v1/auth")
public class AuthController {	
	private final AuthService service;
	
	@Operation(summary = "Cadastrar novo usuário", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!" ),
			@ApiResponse(responseCode = "400", description = "Requisição de cadastro gerou um erro." ),
			@ApiResponse(responseCode = "401", description = "Usuário não autorizado!" )
	})
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody  @Valid RegisterRequest request) {
	   return ResponseEntity.ok(service.register(request));
	}
  
	@Operation(summary = "Autenticar usuário", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso!" ),
			@ApiResponse(responseCode = "400", description = "Requisição de cadastro gerou um erro." ),
			@ApiResponse(responseCode = "401", description = "Usuário não autorizado!" )
	})
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody  @Valid AuthRequest request) {
	   return ResponseEntity.ok(service.authenticate(request));
	}	
	
	@Operation(summary = "Listar todos usuários", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuários encontrados como com sucesso!" ),
			@ApiResponse(responseCode = "400", description = "Requisição de cadastro gerou um erro." ),
			@ApiResponse(responseCode = "401", description = "Usuário não autorizado!" )
	})
	@GetMapping("/todos")
	public ResponseEntity<?> findAll(){
		List<UsuarioDto> usuarios = service.findAll().stream().map(UsuarioDto::new).toList();
		return ResponseEntity.ok(usuarios);
	}
}
