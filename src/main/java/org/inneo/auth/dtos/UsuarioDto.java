package org.inneo.auth.dtos;

import org.inneo.auth.model.usuario.Usuario;

public record UsuarioDto(Long id, String username) {
	 public UsuarioDto(Usuario usuario){
	        this(usuario.getId(), usuario.getUsername());
	    }
}
