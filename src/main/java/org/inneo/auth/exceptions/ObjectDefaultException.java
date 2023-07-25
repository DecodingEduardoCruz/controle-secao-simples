package org.inneo.auth.exceptions;

public class ObjectDefaultException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectDefaultException(String msg ) {
        super( msg );
    }

    public ObjectDefaultException(String msg, Throwable ceuse ) {
        super( msg, ceuse );
    }

}
