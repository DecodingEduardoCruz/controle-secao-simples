package org.inneo.auth.repository;

import java.util.Optional;
import org.inneo.auth.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	  Optional<Usuario> findByUsername(String username);
}
