package com.coop.pilcrow.alertar.business.impl;

import java.util.Date;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.coop.pilcrow.alertar.business.LocalizacionesBo;
import com.coop.pilcrow.alertar.conf.BotonConf;
import com.coop.pilcrow.alertar.dto.BotonDto;
import com.coop.pilcrow.alertar.util.Message;
/**
 * Implementación de localizaciones Bo.
 * Se va a encargar de la lógica de las localizaciones que va a manejar la app.
 * @author gabriel.teolis
 *
 */
public class LocalizacionesBoImpl implements LocalizacionesBo {

	@Override
	public BotonDto generarBotonAEnviar(Context context, LocationManager locationManager,
			Location locationGps, Location locationNetwork, BotonConf botonConf) {
		
		BotonDto botonDto = new BotonDto();
		botonDto.setIdUsuario(botonConf.getImei());
		botonDto.setLatitud("");
		botonDto.setLongitud("");
		botonDto.setLocationProvider("");
		botonDto.setAccuracy("");
		botonDto.setTime("");
		botonDto.setAltitude("");
		botonDto.setSpeed("");
		
		
		boolean hayLocationGPS = (locationGps != null);
		boolean hayLocationNetWork = (locationNetwork != null);
		Date dateGps = null;
		Date dateNetwork =	null;	
		
		//De las 2 me quedo con la más nueva.
		if(hayLocationGPS && hayLocationNetWork){
			dateGps = new Date(locationGps.getTime());
			dateNetwork = new Date(locationNetwork.getTime());
			
			//Si la de network es más nueva, la mando.
			if(dateGps.compareTo(dateNetwork) > 0){			
				botonDto = armarBotonDtoLocation(botonDto, locationGps );
//				message.showShortTimeMessage("Mando GPS " + sdf.format(new Date(locationGps.getTime())));
			}else{
				botonDto = armarBotonDtoLocation(botonDto, locationNetwork );
//				message.showShortTimeMessage("Mando network " + sdf.format(new Date(locationNetwork.getTime())));
			}	
			return botonDto;
		}else if(hayLocationGPS){
			botonDto = armarBotonDtoLocation(botonDto, locationGps );
//			message.showShortTimeMessage("Mando GPS " +sdf.format(new Date(locationGps.getTime())));
			return botonDto;
		}else if(hayLocationNetWork){
			botonDto = armarBotonDtoLocation(botonDto, locationNetwork );
//			message.showShortTimeMessage("Mando network " + sdf.format(new Date(locationNetwork.getTime())));
			return botonDto;
		}
		
		return botonDto;
	}
	
	@Override
	public BotonDto armarBotonDtoLocation(BotonDto botonDto, Location location){
	
		//Limpio los campos que puedo llegar a actualizar con la location.
		botonDto.setLatitud("");
		botonDto.setLongitud("");
		botonDto.setLocationProvider("");
		botonDto.setAccuracy("");
		botonDto.setTime("");
		botonDto.setAltitude("");
		botonDto.setSpeed("");
		
		if(location == null){
			return botonDto;
		}
		
		String latitud = formatoCoord.format(location.getLatitude());
		latitud = latitud.replace(",", ".");
		String longitud = formatoCoord.format(location.getLongitude());
		longitud = longitud.replace(",", ".");
		botonDto.setLatitud(latitud);
		botonDto.setLongitud(longitud);
		botonDto.setLocationProvider(location.getProvider());
		
		//para codeIgniter le paso el timestamp
		String fecha = sdf.format(location.getTime());
		long unixTime = location.getTime() / 1000L;
		botonDto.setTime(String.valueOf(unixTime));
		try{
			botonDto.setAccuracy(Float.valueOf(location.getAccuracy()).toString());
			botonDto.setAltitude(Double.valueOf(location.getAltitude()).toString());
			botonDto.setSpeed(Float.valueOf(location.getSpeed()).toString());
		}catch (Exception e){
			//si no vienen esos datos no importa.
		}	
		return botonDto;
	}
}
