package com.coop.pilcrow.alertar.business.exception;

public class RegistroUsuarioException extends Exception {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception de solicitud vac�a.
	 */
	public static String SOLICITUD_VACIA = "La solicitud est� vac�a.";
	/**
	 * Exception de nombre vac�o.
	 */
	public static String NOMBRE_VACIO = "El Nombre est� vac�o.";
	/**
	 * Exception del tama�o del nombre.
	 */
	public static String TAM_NOMBRE = "El Nombre debe tener un m�nimo de 3 letras.";
	/**
	 * Exception del tama�o del apellido.
	 */
	public static String TAM_APELLIDO = "El Apellido debe tener un m�nimo de 3 letras.";
	/**
	 * Exception de apellido vac�o.
	 */
	public static String APELLIDO_VACIO = "El Apellido est� vac�o.";
	/**
	 * Exception de nombre inv�lido.
	 */
	public static String NOMBRE_INVALIDO = "El nombre de usuario s�lo puede contener letras o n�meros, sin acentos ni �.";
	/**
	 * Exception de IMEI vac�o.
	 */
	public static String IMEI_VACIO = "No fue posible obtener el IMEI de su celular.";
	/**
	 * Exception de EMAIL vac�o.
	 */
	public static String EMAIL_VACIO = "Ingrese su EMail.";
	/**
	 * Exception de EMAIL inv�lido.
	 */
	public static String EMAIL_INVALIDO = "Su EMail es inv�lido. Por favor vuelva a ingresarlo.";
	/**
	 * Exception de n�mero de celular vac�o.
	 */
	public static String NUMERO_CEL_VACIO = "Ingrese su n�mero de su celular.";
	/**
	 * Exception de n�mero de la caracter�stica empiza con 0.
	 */
	public static String NUMERO_CARACT_CERO = "Por favor ingrese su carcater�sitca sin el 0 inicial.";
	/**
	 * Exception de n�mero de celular menor a 6 n�meros.
	 */
	public static String TAM_NUMERO_CEL = "Su tel�fono celuar debe poseer m�nimo 6 n�meros.";
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