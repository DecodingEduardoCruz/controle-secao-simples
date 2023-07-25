package org.inneo.auth.repository;

import java.util.List;
import java.util.Optional;

import org.inneo.auth.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRep extends JpaRepository<Token, Long>{
	@Query(value = """
			select t from Token t inner join Usuario u 
			on t.usuario.id = u.id 
			where u.id = :id and (t.expired = false or t.revoked = false)""")
	 List<Token> findAllValidTokenByUsuario(Long id);	
	 Optional<Token> findByToken(String token);
}
