package com.coop.pilcrow.alertar.dto;
/**
 * Respuesta del server ante una solicitud de registro de usuario.
 * @author gabriel.teolis
 *
 */
public class RespuestaServerSolicitudDto {
	/**
	 * Respuesta única del server.
	 */
	String respuesta = null;

	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
