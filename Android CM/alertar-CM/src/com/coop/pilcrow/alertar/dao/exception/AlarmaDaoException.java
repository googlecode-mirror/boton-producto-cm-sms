package com.coop.pilcrow.alertar.dao.exception;

public class AlarmaDaoException extends Exception {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor para agregar mensaje y exception.
	 * @param message
	 * @param cause
	 */
	public AlarmaDaoException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Constructor para agregar mensaje.
	 * @param message
	 */
	public AlarmaDaoException(String message) {
		super(message);
	}
}
