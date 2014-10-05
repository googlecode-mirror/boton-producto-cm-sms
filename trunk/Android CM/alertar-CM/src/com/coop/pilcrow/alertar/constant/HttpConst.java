package com.coop.pilcrow.alertar.constant;

/**
 * Constantes para el envío de info al server por medio de http.
 * @author gabriel.teolis
 *
 */
public class HttpConst {
	
	/**
	 * Url del servlet que recibe las alarmas.
	 */
	public static String URL_ALARMA = "http://www.pilcrow.com.ar/serveralertar/alertas/add/";
	/**
	 * Url del servlet que recibe los registros de usuario.
	 */
	public static String URL_REGISTRO = "http://www.pilcrow.com.ar/serveralertar/personas/add/";
	/**
	 * Url del servlet que recibe los registros de usuario.
	 */
	public static String URL_ACTUALIZA = "http://www.pilcrow.com.ar/serveralertar/personas/update/";
	/**
	 * Nombre del parámetro para el id de usuario. 
	 */
	public static String PARAM_USUARIO = "usuario_id";
	/**
	 * Nombre del parámetro para el id del botón. 
	 */
	public static String PARAM_BOTON = "boton_id";
	/**
	 * Nombre del parámetro para la longitud. 
	 */
	public static String PARAM_LONGITUD = "lng";
	/**
	 * Nombre del parámetro para la latitud. 
	 */
	public static String PARAM_LATITUD = "lat";
	/**
	 * Nombre del parámetro para el proveedor de localizaciones utilizado.
	 */
	public static String PARAM_LOCATTIONPROVIDER = "locationProvider";
	/**
	 * Nombre del parámetro para Accuracy del proveedor de localizaciones.
	 */
	public static String PARAM_ACCURACY = "accuracy";
	/**
	 * Nombre del parámetro para Time del proveedor de localizaciones con formato.
	 */
	public static String PARAM_TIME = "time";
	/**
	 * Nombre del parámetro para Altitude del proveedor de localizaciones.
	 */
	public static String PARAM_ALTITUD = "altitude";
	/**
	 * Nombre del parámetro para Speed del proveedor de localizaciones.
	 */
	public static String PARAM_SPEED = "speed";
	/**
	 * Nombre del parámetro para el nombre de usuario. 
	 */
	public static String PARAM_USUARIO_SOLICITUD = "nombreUsuario";
	/**
	 * Nombre del parámetro para el email de usuario. 
	 */
	public static String PARAM_EMAIL_SOLICITUD = "email";
	/**
	 * Nombre del parámetro para el imei de usuario. 
	 */
	public static String PARAM_IMEI_SOLICITUD = "s";
	/**
	 * Nombre del parámetro para el imei de usuario. 
	 */
	public static String PARAM_NUMCEL_SOLICITUD = "t";
	/**
	 * Nombre del parámetro para cancelar la alerta.
	 */
	public static String PARAM_CANCELAR = "cancelar";
	/**
	 * Código de respuesta del server.
	 */
	public static String HTTP_SERVER_OK = "OK";
	/**
	 * Código de cancelación de alerta.
	 */
	public static String HTTP_CANCELAR_ALERTA = "cancelar";
	/**
	 * Método de Registrar usuario nuevo.
	 */
	public static String PARAM_REGISTRAR = "registrar";
	/**
	 * Método de Actualizar usuario.
	 */
	public static String PARAM_ACTUALIZAR = "actualizar";
	/**
	 * Prefijo para los dispositivos sin IMEI.
	 */
	public static String PREFIJO_SIN_IMEI = "tablet_";
}
