package org.inneo.auth.model.token;

import java.util.Date;

import org.inneo.auth.model.GenericEntity;
import org.inneo.auth.model.enums.TokenType;
import org.inneo.auth.model.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_token")
public class Token extends GenericEntity{
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	public String token;

	@Enumerated(EnumType.STRING)
	public TokenType tokenType;

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
