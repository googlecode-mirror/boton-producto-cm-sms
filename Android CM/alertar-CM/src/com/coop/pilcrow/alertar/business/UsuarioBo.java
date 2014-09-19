package com.coop.pilcrow.alertar.business;


import com.coop.pilcrow.alertar.business.exception.RegistroUsuarioException;
import com.coop.pilcrow.alertar.dto.RegistroUsuarioDto;
import com.coop.pilcrow.alertar.dto.SolicitudUsuarioDto;

/**
 * Se va a encargar de manejar los datos de los usuarios.
 * @author gabriel.teolis
 *
 */
public interface UsuarioBo {
	/**
	 * Registra el usuario en el sistema
	 * @param solicitudUsuarioDto
	 * 			datos de solicitud de registro.
	 */
	public SolicitudUsuarioDto generarSolicitudUsuario(RegistroUsuarioDto registroUsuarioDto) throws RegistroUsuarioException;

}
