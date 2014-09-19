package com.coop.pilcrow.alertar.dto;
/**
 * Datos que el usuario ingresa para registrarse.
 * @author gabriel.teolis
 *
 */
public class RegistroUsuarioDto {
	/**
	 * Nombre de usuario.
	 */
	String nombreUsuario;
	/**
	 * Apellido de usuario.
	 */
	String apellidoUsuario;
	/**
	 * Mail.
	 */
	String EMail;
	/**
	 * IMEI
	 */
	String iMei;
	/**
	 * Número de teléfono.
	 */
	String numeroCelular;
	/**
	 * @return the numeroCelular
	 */
	public String getNumeroCelular() {
		return numeroCelular;
	}

	/**
	 * @param numeroCelular the numeroCelular to set
	 */
	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}
	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	/**
	 * @return the eMail
	 */
	public String getEMail() {
		return EMail;
	}
	/**
	 * @param eMail the eMail to set
	 */
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	/**
	 * @return the iMei
	 */
	public String getIMei() {
		return iMei;
	}
	/**
	 * @param iMei the iMei to set
	 */
	public void setIMei(String iMei) {
		this.iMei = iMei;
	}

	/**
	 * @return the apellidoUsuario
	 */
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	/**
	 * @param apellidoUsuario the apellidoUsuario to set
	 */
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

}
