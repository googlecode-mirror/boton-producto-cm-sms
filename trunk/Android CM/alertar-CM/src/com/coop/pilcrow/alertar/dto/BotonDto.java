package com.coop.pilcrow.alertar.dto;

/**
 * Dto con la info del botón y de la posición a enviar al server.
 * @author gabriel.teolis
 *
 */
public class BotonDto {
	/**
	 * Id de la alerta del botón apretado.
	 */
	String idAlerta;
	/**
	 * Usuario que dio a alerta.
	 */
	String idUsuario;
	/**
	 * Latitud del celular.
	 */
	String latitud;
	/**
	 * Longitud del celular.
	 */
	String longitud;
	/**
	 * Proveedor de location de la alarma.
	 */
	String locationProvider;
	/**
	 * Accuracy del proveedor de localizaciones.
	 */
	String accuracy;
	/**
	 * Time del proveedor de localizaciones con formato.
	 */
	String time;
	/**
	 * Altitude del proveedor de localizaciones.
	 */
	String altitude;
	/**
	 * Speed del proveedor de localizaciones.
	 */
	String speed;
	

	/**
	 * @return the idAlerta
	 */
	public String getIdAlerta() {
		return idAlerta;
	}
	/**
	 * @param idAlerta the idAlerta to set
	 */
	public void setIdAlerta(String idAlerta) {
		this.idAlerta = idAlerta;
	}
	/**
	 * @return the idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}
	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	/**
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}
	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	/**
	 * Obtiene todos los datos separados por corchetes.
	 * @return todos los atributos en un string separados por corchetes.
	 */
	public String obtenerParametros(){
		return "[" + this.idAlerta + "][" + this.idUsuario + "][" + this.latitud + "][" + this.longitud + "]";
	}
	/**
	 * @return the locationProvider
	 */
	public String getLocationProvider() {
		return locationProvider;
	}
	/**
	 * @param locationProvider the locationProvider to set
	 */
	public void setLocationProvider(String locationProvider) {
		this.locationProvider = locationProvider;
	}
	/**
	 * @return the accuracy
	 */
	public String getAccuracy() {
		return accuracy;
	}
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	/**
	 * @return the speed
	 */
	public String getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}
}
