package com.coop.pilcrow.alertar.dao.exception;

/**
 * Excepciones en el envío y recepción de HTTP.
 * @author gabriel.teolis
 *
 */
public class HttpDaoException extends AlarmaDaoException {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Mensaje de error cuando hay algún caracter inválido en los parámetros.
     */
    public static final String ERROR_ENCODER_PARAMETROS = "ERROR en los parametros:";
    
    /**
     * Mensaje de error cuando hay algún caracter inválido en la URL.
     */
    public static final String ERROR_URL = "ERROR al formar la URL:";
    
    /**
     * Mensaje de error cuando hay algún problema al conectar con el server.
     */
    public static final String ERROR_CONEXION = "ERROR al conectar con la URL:";
    /**
     * Mensaje de error cuando no puede leer la respuesta del server.
     */
    public static final String ERROR_MANEJO_RESPUESTA = "ERROR al leer la respuesta del server:";
	
    public HttpDaoException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
