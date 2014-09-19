package com.coop.pilcrow.alertar.business.exception;

public class RegistroUsuarioException extends Exception {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception de solicitud vacía.
	 */
	public static String SOLICITUD_VACIA = "La solicitud está vacía.";
	/**
	 * Exception de nombre vacío.
	 */
	public static String NOMBRE_VACIO = "El Nombre está vacío.";
	/**
	 * Exception del tamaño del nombre.
	 */
	public static String TAM_NOMBRE = "El Nombre debe tener un mínimo de 3 letras.";
	/**
	 * Exception del tamaño del apellido.
	 */
	public static String TAM_APELLIDO = "El Apellido debe tener un mínimo de 3 letras.";
	/**
	 * Exception de apellido vacío.
	 */
	public static String APELLIDO_VACIO = "El Apellido está vacío.";
	/**
	 * Exception de nombre inválido.
	 */
	public static String NOMBRE_INVALIDO = "El nombre de usuario sólo puede contener letras o números, sin acentos ni ñ.";
	/**
	 * Exception de IMEI vacío.
	 */
	public static String IMEI_VACIO = "No fue posible obtener el IMEI de su celular.";
	/**
	 * Exception de EMAIL vacío.
	 */
	public static String EMAIL_VACIO = "Ingrese su EMail.";
	/**
	 * Exception de EMAIL inválido.
	 */
	public static String EMAIL_INVALIDO = "Su EMail es inválido. Por favor vuelva a ingresarlo.";
	/**
	 * Exception de número de celular vacío.
	 */
	public static String NUMERO_CEL_VACIO = "Ingrese su número de su celular.";
	/**
	 * Exception de número de la característica empiza con 0.
	 */
	public static String NUMERO_CARACT_CERO = "Por favor ingrese su carcaterísitca sin el 0 inicial.";
	/**
	 * Exception de número de celular menor a 6 números.
	 */
	public static String TAM_NUMERO_CEL = "Su teléfono celuar debe poseer mínimo 6 números.";
	/**
	 * Constructor para agregar mensaje y exception.
	 * @param message
	 * @param cause
	 */
	public RegistroUsuarioException(String message, Throwable cause) {
		super(message, cause);
	}
	public RegistroUsuarioException(String message) {
		super(message);
	}
}