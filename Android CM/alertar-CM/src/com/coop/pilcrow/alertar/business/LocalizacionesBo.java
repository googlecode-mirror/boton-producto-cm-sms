package com.coop.pilcrow.alertar.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.coop.pilcrow.alertar.conf.BotonConf;
import com.coop.pilcrow.alertar.dto.BotonDto;

/**
 * Se va a encargar de la lógica de las localizaciones que va a manejar la app.
 * @author gabriel.teolis
 *
 */
public interface LocalizacionesBo {
	/**
	 * Formateador de coordenadas a String.
	 */
	DecimalFormat formatoCoord = new DecimalFormat("###.######");
	/**
	 * La voy a usar para formatear las fechas.
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * A partir de los recursos de localización que cuenta la app, genera los datos a ser enviados al server
	 * del botón que haya presionado el usuario.
	 * @param locationManager
	 * 			LocationManager de la App.
	 * @param locationGps
	 * 			Location del listener de Gps, actualizado en el onLocationChanged() del listener.
	 * @param locationNetwork
	 * 			Location del listener de Network, actualizado en el onLocationChanged() del listener.
	 * @return
	 * 		BotonDto con los datos a ser enviados al server.
	 */
	public BotonDto generarBotonAEnviar(Context context, LocationManager locationManager, Location locationGps, Location locationNetwork, BotonConf botonConf);
	/**
	 * Setea los valores de location al BotonDto
	 * @param botonDto
	 * 			botonDto con datos, o no.
	 * @param location
	 * 			lcation con datos a setear en un botonDto
	 * @return	
	 * 		botonDto con los datos de location seteados.
	 */
	public BotonDto armarBotonDtoLocation(BotonDto botonDto, Location location);
	
}
