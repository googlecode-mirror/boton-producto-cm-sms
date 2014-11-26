package com.coop.pilcrow.alertar.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.coop.pilcrow.alertar.constant.HttpConst;
import com.coop.pilcrow.alertar.dao.RegistroUsuarioDao;
import com.coop.pilcrow.alertar.dao.exception.HttpDaoException;
import com.coop.pilcrow.alertar.dao.exception.SolicitudUsuarioDaoException;
import com.coop.pilcrow.alertar.dto.RespuestaServerSolicitudDto;
import com.coop.pilcrow.alertar.dto.SolicitudUsuarioDto;

public class RegistroUsuarioDaoHttpImpl implements RegistroUsuarioDao{
	@Override
	public RespuestaServerSolicitudDto enviarSolicitud(
			SolicitudUsuarioDto solicitudDto, String metodo)
			throws SolicitudUsuarioDaoException {
		RespuestaServerSolicitudDto respuestaServerPosDto = new RespuestaServerSolicitudDto();
		
		if(solicitudDto == null){
			throw new SolicitudUsuarioDaoException("No hay datos de solicitud.");
		}
		//Armo la info a enviar.
		String data = "";
        try{
        	if(solicitudDto.getNombreUsuario() != null){
        		String concatNombre = solicitudDto.getNombreUsuario();
        		if(solicitudDto.getApellidoUsuario()!= null){
            		concatNombre = solicitudDto.getNombreUsuario() + " " + solicitudDto.getApellidoUsuario();
            	}
        		data = URLEncoder.encode(HttpConst.PARAM_USUARIO_SOLICITUD, "UTF-8") + "/" + URLEncoder.encode(concatNombre, "UTF-8").replace("+", "%20"); 
        	}
        	
        	if(solicitudDto.getEMail() != null){
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_EMAIL_SOLICITUD, "UTF-8") + "/" + URLEncoder.encode(solicitudDto.getEMail(), "UTF-8"); 
        	}
        	if(solicitudDto.getIMei() != null){
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_IMEI_SOLICITUD, "UTF-8") + "/" + URLEncoder.encode(solicitudDto.getIMei(), "UTF-8");
        	}
        	if(solicitudDto.getNumeroCelular() != null){
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_NUMCEL_SOLICITUD, "UTF-8") + "/" + URLEncoder.encode(solicitudDto.getNumeroCelular(), "UTF-8");
        	}
        }catch(UnsupportedEncodingException ex){
        	throw new SolicitudUsuarioDaoException(HttpDaoException.ERROR_ENCODER_PARAMETROS, ex.getCause());
        }

        // Defino la URL a la cual enviar la info.
        URL url = null;
		try {
			if (metodo == HttpConst.PARAM_ACTUALIZAR){
				url = new URL(HttpConst.URL_ACTUALIZA);
			}else if(metodo == HttpConst.PARAM_REGISTRAR){
				url = new URL(HttpConst.URL_REGISTRO);
			}
			
		} catch (MalformedURLException e) {
			throw new SolicitudUsuarioDaoException(HttpDaoException.ERROR_URL + HttpConst.URL_REGISTRO, e.getCause());
		}
        
        // Send data request.
		URLConnection conn = null;
		try {
			conn = new URL(url + data).openConnection();
			conn.setRequestProperty("Accept-Charset", "UTF-8");
		} catch (IOException e) {
			throw new SolicitudUsuarioDaoException(HttpDaoException.ERROR_CONEXION, e.getCause());
		} 
		
		try{
			// Get de la respuesta del server.
			BufferedReader bufferReader = null;
			bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			String lineaRespuesta = null;
			
			// Leo la respuesta del server.
			while((lineaRespuesta = bufferReader.readLine()) != null){
				
				// Append de la respuesta a la linea.
				stringBuilder.append(lineaRespuesta + "\n");
			}
			
			//Seteo la respuesta al dto a retornar.
			respuestaServerPosDto.setRespuesta(stringBuilder.toString());
			bufferReader.close();
		} catch (IOException e) {
			throw new SolicitudUsuarioDaoException(HttpDaoException.ERROR_MANEJO_RESPUESTA, e.getCause());
		} 
		respuestaServerPosDto.setRespuesta("OK");
		return respuestaServerPosDto;
    
	}
}
