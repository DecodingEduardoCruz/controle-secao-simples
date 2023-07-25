package org.inneo.auth.model;

import lombok.Data;
import java.io.Serializable;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenericEntity implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
}
