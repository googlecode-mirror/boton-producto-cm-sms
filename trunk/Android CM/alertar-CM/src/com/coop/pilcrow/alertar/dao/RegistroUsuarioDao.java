package com.coop.pilcrow.alertar.dao;

import com.coop.pilcrow.alertar.dao.exception.SolicitudUsuarioDaoException;
import com.coop.pilcrow.alertar.dto.RespuestaServerSolicitudDto;
import com.coop.pilcrow.alertar.dto.SolicitudUsuarioDto;

/**
 * Dao encargado de la comunicaci�n con el server para el registro de usuarios.
 * @author gabriel.teolis
 *
 */
public interface RegistroUsuarioDao {
	/**
	 * Env�a al server una solicitud de registro de usuario.
	 * @param solicitudDto
	 * 			Datos de usuario de la solicitud.
	 * @return	
	 * 			Respuesta del server ante la solicitud.
	 * @throws SolicitudUsuarioDaoException
	 * 			Si algo sale mal durante el env�o.
	 */
	public RespuestaServerSolicitudDto enviarSolicitud(SolicitudUsuarioDto solicitudDto, String metodo) throws SolicitudUsuarioDaoException;

}
