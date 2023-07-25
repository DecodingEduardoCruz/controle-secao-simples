package org.inneo.auth.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import lombok.Builder;
import lombok.Data;

@RestController
@RequiredArgsConstructor
@Tag(name = "home")
@RequestMapping("/")
public class OpenRest {	
	@Operation(summary = "Api de resposta home", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuários encontrados como com sucesso!" ),
			@ApiResponse(responseCode = "400", description = "Requisição de cadastro gerou um erro." ),
			@ApiResponse(responseCode = "401", description = "Usuário não autorizado!" ),
	})
	
	@GetMapping
	public ResponseEntity<SuccessPage> getHomePage() {
		SuccessPage successPage = SuccessPage.builder().success(true).message("Api Auth is Running.").build();
		return ResponseEntity.ok(successPage);
	}
}

@Data @Builder
class SuccessPage {
	private Boolean success;
	private String message;
}
