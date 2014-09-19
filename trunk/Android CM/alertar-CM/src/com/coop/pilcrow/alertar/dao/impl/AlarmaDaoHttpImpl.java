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
import com.coop.pilcrow.alertar.dao.AlarmaDao;
import com.coop.pilcrow.alertar.dao.exception.AlarmaDaoException;
import com.coop.pilcrow.alertar.dao.exception.HttpDaoException;
import com.coop.pilcrow.alertar.dto.BotonDto;
import com.coop.pilcrow.alertar.dto.RespuestaServerPosDto;

/*
 * Implementación de AlarmaDao con envío por GET.
 */
public class AlarmaDaoHttpImpl implements AlarmaDao{
	
	@Override
	public RespuestaServerPosDto enviarPoscion(BotonDto botonDto) throws AlarmaDaoException{
		RespuestaServerPosDto respuestaServerPosDto = new RespuestaServerPosDto();
		
		//Armo la info a enviar.
		String data = "";
        try{
        	data = URLEncoder.encode(HttpConst.PARAM_USUARIO, "UTF-8") + "/" + URLEncoder.encode(botonDto.getIdUsuario(), "UTF-8"); 
        	if(botonDto.getIdAlerta()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_BOTON, "UTF-8") + "/" + URLEncoder.encode(botonDto.getIdAlerta(), "UTF-8"); 
        	if(botonDto.getLatitud()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LATITUD, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLatitud(), "UTF-8");
        	if(botonDto.getLongitud()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LONGITUD, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLongitud(), "UTF-8");
        	if(botonDto.getLocationProvider()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LOCATTIONPROVIDER, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLocationProvider(), "UTF-8");
        	if(botonDto.getAccuracy()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_ACCURACY, "UTF-8") + "/" + URLEncoder.encode(botonDto.getAccuracy(), "UTF-8");
        	if(botonDto.getSpeed()!= null)	
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_SPEED, "UTF-8") + "/" + URLEncoder.encode(botonDto.getSpeed(), "UTF-8");
        	if(botonDto.getTime()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_TIME, "UTF-8") + "/" + URLEncoder.encode(botonDto.getTime(), "UTF-8");
        	if(botonDto.getAltitude()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_ALTITUD, "UTF-8") + "/" + URLEncoder.encode(botonDto.getAltitude(), "UTF-8");
        }catch(UnsupportedEncodingException ex){
        	throw new AlarmaDaoException(HttpDaoException.ERROR_ENCODER_PARAMETROS + botonDto.obtenerParametros(), ex.getCause());
        }

        // Defino la URL a la cual enviar la info.
        URL url = null;
		try {
			url = new URL(HttpConst.URL_ALARMA);
		} catch (MalformedURLException e) {
			throw new AlarmaDaoException(HttpDaoException.ERROR_URL + HttpConst.URL_ALARMA, e.getCause());
		}
        
        // Send data request.
		URLConnection conn = null;
		try {
			conn = new URL(url + data).openConnection();
			conn.setRequestProperty("Accept-Charset", "UTF-8");
		} catch (IOException e) {
			throw new AlarmaDaoException(HttpDaoException.ERROR_CONEXION, e.getCause());
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
			throw new AlarmaDaoException(HttpDaoException.ERROR_MANEJO_RESPUESTA, e.getCause());
		} 
		return respuestaServerPosDto;
    }
	
	@Override
	public RespuestaServerPosDto enviarCancelacionAlarma(BotonDto botonDto)
			throws AlarmaDaoException {
		RespuestaServerPosDto respuestaServerPosDto = new RespuestaServerPosDto();
		
		//Armo la info a enviar.
		String data = "";
        try{
        	data = URLEncoder.encode(HttpConst.PARAM_USUARIO, "UTF-8") + "/" + URLEncoder.encode(botonDto.getIdUsuario(), "UTF-8"); 
        	if(botonDto.getIdAlerta()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_BOTON, "UTF-8") + "/" + URLEncoder.encode(botonDto.getIdAlerta(), "UTF-8"); 
        	if(botonDto.getLatitud()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LATITUD, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLatitud(), "UTF-8");
        	if(botonDto.getLongitud()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LONGITUD, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLongitud(), "UTF-8");
        	if(botonDto.getLocationProvider()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_LOCATTIONPROVIDER, "UTF-8") + "/" + URLEncoder.encode(botonDto.getLocationProvider(), "UTF-8");
        	if(botonDto.getAltitude()!= null)
        		data += "/" + URLEncoder.encode(HttpConst.PARAM_CANCELAR, "UTF-8") + "/" + URLEncoder.encode(HttpConst.HTTP_CANCELAR_ALERTA, "UTF-8");
        }catch(UnsupportedEncodingException ex){
        	throw new AlarmaDaoException(HttpDaoException.ERROR_ENCODER_PARAMETROS + botonDto.obtenerParametros(), ex.getCause());
        }

        // Defino la URL a la cual enviar la info.
        URL url = null;
		try {
			url = new URL(HttpConst.URL_ALARMA);
		} catch (MalformedURLException e) {
			throw new AlarmaDaoException(HttpDaoException.ERROR_URL + HttpConst.URL_ALARMA, e.getCause());
		}
        
        // Send data request.
		URLConnection conn = null;
		try {
			conn = new URL(url + "?" + data).openConnection();
			conn.setRequestProperty("Accept-Charset", "UTF-8");
		} catch (IOException e) {
			throw new AlarmaDaoException(HttpDaoException.ERROR_CONEXION, e.getCause());
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
			throw new AlarmaDaoException(HttpDaoException.ERROR_MANEJO_RESPUESTA, e.getCause());
		} 
		return respuestaServerPosDto;
    }
 
}
