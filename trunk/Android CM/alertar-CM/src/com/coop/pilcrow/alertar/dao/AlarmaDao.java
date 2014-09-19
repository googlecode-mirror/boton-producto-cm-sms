package com.coop.pilcrow.alertar.dao;

import com.coop.pilcrow.alertar.dao.exception.AlarmaDaoException;
import com.coop.pilcrow.alertar.dto.BotonDto;
import com.coop.pilcrow.alertar.dto.RespuestaServerPosDto;

/**
 * Se encarga de enviar y recibir la información de las alarmas activadas por el usuario.
 * @author gabriel.teolis
 *
 */
public interface AlarmaDao {
	/**
	 * Se encarga de enviar la información del botón al server.
	 * @return
	 * 		RespuetaServerPosDto con la respuesta.
	 */
	public RespuestaServerPosDto enviarPoscion(BotonDto boton) throws AlarmaDaoException;
	
	
	/**
	 * Se encarga de enviar la información del botón cancelado al server.
	 * @return
	 * 		RespuetaServerPosDto con la respuesta.
	 */
	public RespuestaServerPosDto enviarCancelacionAlarma(BotonDto boton) throws AlarmaDaoException;
	
}
