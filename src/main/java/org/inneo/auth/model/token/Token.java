package org.inneo.auth.model.token;

import java.util.Date;

import org.inneo.auth.model.enums.TokenType;
import org.inneo.auth.model.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
	@Id
	@GeneratedValue
	public Long id;

	@Column(unique = true)
	public String token;

	@Enumerated(EnumType.STRING)
	public TokenType tokenType = TokenType.BEARER;

	@Column(name = "renovado")
	private boolean revoked;
	
	@Column(name = "expirado")
	private boolean expired;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro; 
	
	@Column(name = "data_inativacao")
	private Date dataInativacao;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	public Usuario usuario;
}
