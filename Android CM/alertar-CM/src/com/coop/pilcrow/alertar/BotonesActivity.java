package com.coop.pilcrow.alertar;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.coop.pilcrow.alertar.business.LocalizacionesBo;
import com.coop.pilcrow.alertar.business.impl.LocalizacionesBoImpl;
import com.coop.pilcrow.alertar.conf.BotonConf;
import com.coop.pilcrow.alertar.conf.ConfigLoader;
import com.coop.pilcrow.alertar.constant.HttpConst;
import com.coop.pilcrow.alertar.dao.AlarmaDao;
import com.coop.pilcrow.alertar.dao.exception.AlarmaDaoException;
import com.coop.pilcrow.alertar.dao.impl.AlarmaDaoHttpImpl;
import com.coop.pilcrow.alertar.dto.BotonDto;
import com.coop.pilcrow.alertar.dto.RespuestaServerPosDto;
import com.coop.pilcrow.alertar.util.Message;
//import com.flurry.android.FlurryAgent;

/**
 * Actividad que se encarga del manejo de los botones.
 * @author gabriel.teolis
 *
 */
public class BotonesActivity extends Activity {
	/**
	 * Datos de configuración del botón.
	 */
	BotonConf botonConf = null;
	
	/**
	 * Location manager para las posiciones.
	 */
	private LocationManager locationManager;
	/**
	 * Listener que escuchará los cambios de posición del teléfono por network.
	 */
	private LocationListener networkLocationListener = null;
	/**
	 * Listener que escuchará los cambios de posición del teléfono por GPS.
	 */
	private LocationListener gpsLocationListener = null;
	/**
	 * Listener que escuchará los cambios de posición del teléfono.
	 */
	private LocationListener locationListener = null;
	/**
	 * Location tendrá los datos de location obtenidos a lo largo del ciclo de vida de la activity.
	 */
	Location location = null;
	/**
	 * Tendrá los datos del último location descartado a lo largo del ciclo de vida de la activity.
	 */
	Location locationAnterior = null;
	/**
	 * Tiempo máximo, en segundos, de antigüedad de una location.
	 */
	private static final int DELTA_MAX_SEG = 1000 * 60 * 20;
	/**
	 * Mensajes para el usuario.
	 */
	private Message message;
	/**
	 * Formateador de coordenadas a String.
	 */
	DecimalFormat formatoCoord = new DecimalFormat("###.######");
	/**
	 * Thread que va a enviar datos al server.
	 */
	Thread envioDatosServerTh = null;
	/**
	 * Flag para saber si el thread está enviando datos.
	 */
	private boolean threadEnviando = false;
	/**
	 * Eriqueta para error en el thread de envío.
	 */
	private String ERROR_THREAD = "ERROR_THREAD";
	/**
	 * Datos del botón presionado.
	 */
	private BotonDto botonDto;
	/**
	 * le avisa cuando obtuvo datos del server.
	 */
	private Handler mensajeHandler = null;
	/**
	 * formateador de fechas.
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * flag por si quiero cancelar el envío de datos al server.
	 */
	boolean reitentarEnvio = true;
	/**
	 * flag botón enviado activado.
	 */
	boolean botonEnviadoActivo = false;
	/**
	 * Tipo de alerta activa.
	 */
	int tipoAlertaActiva = 1;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.botones_main);
		
		message = new Message(this.getBaseContext());
		reitentarEnvio = true;
		
		//Cargo las configuraciones del usuario.
        SharedPreferences settings = getSharedPreferences(BotonConf.PREFS_NAME, 0);
        ConfigLoader.setSharedPreferences(settings);     
        botonConf = ConfigLoader.load(); 
        
		//inicio el listener para posiciones
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
		      !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			  showAlertDialogGPSNetworkOn();
		}
		
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected()) {
			showAlertDialogNetworkOn();
		}
		
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				actualizaLocation(location);
				
				//ni bien obtiene info veo si tengo el botón activado.
				if(botonEnviadoActivo){
					enviarAlerta(tipoAlertaActiva);
				}
			}
			public void onStatusChanged(String provider, int status, Bundle extras) {		
				//message.showShortTimeMessage("GPS_StatusChanged:" + status);
			}
			public void onProviderEnabled(String proveedor) {
				//message.showShortTimeMessage("Proveedor habilitado: " + proveedor);
			}
			public void onProviderDisabled(String proveedor) {
				//message.showShortTimeMessage("Proveedor deshabilitado: " + proveedor);
			}
		};
		
		// Activamos notificaciones de localización del GPS.
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			
		// Activamos notifiaciones de localización de network.
		if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		}
		
		//Botón que envía la posición.
		ImageButton botonEnviarAlerta = (ImageButton) findViewById(R.id.bombero_btn); 
		botonEnviarAlerta.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					tipoAlertaActiva = 1;
					enviarAlerta(tipoAlertaActiva);         	
				}
			});
		
		//Botón que envía la posición.
		ImageButton botonMedico = (ImageButton) findViewById(R.id.medico_btn); 
		botonMedico.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					tipoAlertaActiva = 2;
					enviarAlerta(tipoAlertaActiva);             	
				}
			});

		//Botón que envía la posición.
		ImageButton botonPolicia = (ImageButton) findViewById(R.id.policia_btn); 
		botonPolicia.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tipoAlertaActiva = 3;
				enviarAlerta(tipoAlertaActiva);         	
			}
		});
		
		//Botón para volver a la vista principal.
		ImageButton botonVolver = (ImageButton) findViewById(R.id.volver_btn); 
		botonVolver.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				findViewById(R.id.fin_proceso_layout).setVisibility(View.GONE);
				findViewById(R.id.proceso_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.layout_dos).setVisibility(View.GONE);
				findViewById(R.id.layout_uno).setVisibility(View.VISIBLE); 				
			}
		});
		
	}
	/**
	 * Actualiza los datos de location, se fija si es muy vieja primero.
	 * dejará en location la actual, de lo contrario en locationAnterior. 
	 */
	private synchronized void actualizaLocation(Location locationNew){
		long timeLocation = locationNew.getTime();
		long timeAcutal = System.currentTimeMillis();
		long deltaTime =  timeAcutal - timeLocation;
		
		
		DateFormat df = DateFormat.getTimeInstance();
		df.setTimeZone(botonConf.getTimeZone());
		String timelocationString = df.format(timeLocation);
		String timeActualString = df.format(timeAcutal);
		
		
		//me fijo qué tan vieja es.
//		if(deltaTime > DELTA_MAX_SEG){
//			//message.showLongTimeMessage("#####locationAnterior: " + timelocationString + " actual " + timeActualString );
//			locationAnterior = locationNew;
//			location = null;
//		}else{
			//message.showLongTimeMessage("location: " + timelocationString + " actual " + timeActualString );
			location = locationNew;
//		}
		//TODO GAT: ver si quiero esperar una de buena precisión o darle un límite de tiempo y avisar que demora mucho o mandar la que tengo.
		
	}
	
	
	@Override    
	protected void onResume() {
		super.onResume();
	}
	@Override    
	protected void onPause() {
		
		if(!botonEnviadoActivo){
			
			//Dejo de escuchar al servidor de posiciones.
			locationManager.removeUpdates(locationListener);
		}
		super.onPause();
	}
	@Override
	protected void onDestroy() {

		//Dejo de escuchar al servidor de posiciones.
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}
	
	
	/**
	 * Envia los datos de la alerta al server y muestra la respuesta en pantalla.
	 * TODO: GAT ver si se lanza un service si se va mientras se está enviando datos.
 	 */
	private synchronized void enviarAlerta(int idBoton){
		Resources res = getResources();
		//seteo que apretó el botón.
		botonEnviadoActivo = true;
		
		//Si ya está enviando no le doy bola.
		if(threadEnviando){
			message.showLongTimeMessage(res.getString(R.string.msg_alerta_ya_presionada));
			return;
		}
		
		reitentarEnvio = true;
		
		//Genero los datos a mandar a partir de los datos de las location obtenidas por la subapp.
		LocalizacionesBo localizacionesBo = new LocalizacionesBoImpl();
		
		//Armo el dto para enviar.
		botonDto = new BotonDto();
		botonDto.setIdAlerta(Integer.valueOf(idBoton).toString());
		botonDto.setIdUsuario(botonConf.getImei());
		botonDto = localizacionesBo.armarBotonDtoLocation(botonDto, location);
		
		//modifico la pantalla, no le muestro los otros botones.
		actualizarVistaBotonActivo(idBoton);
		
		//Si no tengo data salgo sin desactivar el botón, va a mandar cuando tenga data, se encargará el onLocationChange de cada listener.
		if(botonDto.getLongitud().equals("") || botonDto.getLatitud().equals("")){
			return;
		}
		
		//Aviso que estoy enviando la info.
		actualizarVistaEnviando();
		
		//va a mandar los mensajes por pantalla con el resultado el envioDatosServerTh.
		mensajeHandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				String respuesta = (String) msg.obj;
				
				//terminó la conexión.
				threadEnviando = false;
				
				//desactivo el botón.
				botonEnviadoActivo = false;
				
				//muestro el resultado.
				//message.showLongTimeMessage(respuesta);
				actualizarVistaAtendido(respuesta);
			}
		};
		
		//Muestro en pantalla que voy a enviar la info.
		//actualizarVistaEnviando();
		
		//Sale a mandar los datos, cuando termina le avisa a mensajeHandler.
		envioDatosServerTh = new Thread(new Runnable() {
			@Override
			public void run() {
				android.os.Message msg = new android.os.Message();
				msg.obj = "0";
				
				//Aviso que está consultando.
				threadEnviando = true;
				AlarmaDao alarmaDao = new AlarmaDaoHttpImpl();
				RespuestaServerPosDto respuestaServerDto = null;
				boolean volverAEnviar = true;
				int contReintentos = 0;
				while(volverAEnviar && reitentarEnvio){
					try {
						respuestaServerDto = alarmaDao.enviarPoscion(botonDto);
						msg.obj = respuestaServerDto.getRespuesta();
						
						//Mientras la respuesta sea igual a vacío, sigo tratando de mandar los datos.
						volverAEnviar = respuestaServerDto.getRespuesta().equals("");
					
						if(volverAEnviar){
							contReintentos++;
							if(contReintentos > 30){
								reitentarEnvio = false;
								msg.obj = ERROR_THREAD;
							}
						}
					} catch (AlarmaDaoException e) {
						msg.obj = e.getMessage();
						reitentarEnvio = false;
					}
				}
				mensajeHandler.sendMessage(msg);
			}
		});
		envioDatosServerTh.start();
	}
	/**
	 * Se va a encargar de modificar la vista al presionar un botón.
	 */
	private void actualizarVistaBotonActivo(int idBoton){
		ImageView pressed_button = (ImageView) findViewById(R.id.image_pressed_button); 
		//el objeto procesoTxt puede ser modificado, por eso lo vuelvo a su texto original
		TextView procesoTxt = (TextView) findViewById(R.id.aviso_proceso_txt);
		procesoTxt.setText(R.string.aviso_proceso);
		findViewById(R.id.layout_uno).setVisibility(View.GONE);
		findViewById(R.id.layout_dos).setVisibility(View.VISIBLE);
		switch (idBoton){
			case 1: //bombero
				pressed_button.setImageResource(R.drawable.bombero);
				break;
			case 2: //medico
				pressed_button.setImageResource(R.drawable.medico);
				break;
			case 3: //policia
				pressed_button.setImageResource(R.drawable.policia);
				break;
			default:
				break;
		}		
	}
	/**
	 * Se va a encargar de modificar la vista al enviar los datos al server.
	 */
	private void actualizarVistaEnviando(){
		TextView procesoTxt = (TextView) findViewById(R.id.aviso_proceso_txt); 
		procesoTxt.setText(R.string.msg_enviando_alerta);
	}
	/**
	 * Se va a encargar de modificar la vista con el resultado del server..
	 */
	private void actualizarVistaAtendido(String respuesta){
		TextView avisoFinTxt = (TextView) findViewById(R.id.aviso_fin_txt); 
		if(respuesta.contains(HttpConst.HTTP_SERVER_OK)){
			avisoFinTxt.setText(R.string.msg_enviando_ok);
		}else{
			avisoFinTxt.setText(R.string.msg_enviado_bad);
		}		
		//oculto el de progreso.
		findViewById(R.id.proceso_layout).setVisibility(View.GONE); 
		findViewById(R.id.fin_proceso_layout).setVisibility(View.VISIBLE);		
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	
	
	/**
	 * @author gustavo.gontaruk
	 * Este método muestra en pantalla un AlertDialog que permite al usuario encender las redes móviles y el GPS
	 */
	private void showAlertDialogGPSNetworkOn(){
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(R.string.atencion);
		  builder.setMessage(R.string.alertaDialogLocationPreferencesMessage);
		  builder.setPositiveButton(R.string.ajustes, new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialogInterface, int i) {
			      Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			      startActivity(intent);
			  }
		  });
		  builder.setNegativeButton(R.string.omitir, new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialogInterface, int i) {
				  //BotonesActivity.this.finish();
			  }
		  });
		  Dialog alertDialog = builder.create();
		  alertDialog.setCanceledOnTouchOutside(false);
		  alertDialog.show();
	}
	
	/**
	 * @author gabriel.teolis
	 * Muestra en pantalla un AlertDialog que permite al usuario encender la conexión de datos del móvil.
	 */
	private void showAlertDialogNetworkOn(){
		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		  builder.setTitle(R.string.atencion);
		  builder.setMessage(R.string.alertaDialogNetworkPreferencesMessage);
		  builder.setPositiveButton(R.string.ajustes, new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialogInterface, int i) {
				  Intent intent = new Intent(Intent.ACTION_MAIN);
				  intent.setClassName("com.android.phone", "com.android.phone.Settings");
				  startActivity(intent);
			  }
		  });
		  builder.setNegativeButton(R.string.omitir, new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialogInterface, int i) {
				  //BotonesActivity.this.finish();
			  }
		  });
		  Dialog alertDialog = builder.create();
		  alertDialog.setCanceledOnTouchOutside(false);
		  alertDialog.show();
	}
	
	/////////////////////////////////// OPTIONS MENU ////////////////////////////////////////////////////////////////////////////////////
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.botones_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {	       
			case R.id.menuButton_miperfil:
				intent.setClass(BotonesActivity.this, MisDatosActivity.class);
				startActivity(intent);     
				return true;
			default:
				return super.onContextItemSelected((MenuItem) item);
		}
	}	
	
	/////////////////////////////////// OPTIONS MENU ////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onStart()
	{
		super.onStart();
		//TODO GAT generar otra app en el flurry para este botón.
		//FlurryAgent.onStartSession(this, "FPJSSQ956RK969FWV5TF");
	}
	 
	@Override
	protected void onStop()
	{
		super.onStop();		
		//FlurryAgent.onEndSession(this);
	}
}
