package com.coop.pilcrow.alertar;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.coop.pilcrow.alertar.business.UsuarioBo;
import com.coop.pilcrow.alertar.business.exception.RegistroUsuarioException;
import com.coop.pilcrow.alertar.business.impl.UsuarioBoImpl;
import com.coop.pilcrow.alertar.conf.BotonConf;
import com.coop.pilcrow.alertar.conf.ConfigLoader;
import com.coop.pilcrow.alertar.constant.HttpConst;
import com.coop.pilcrow.alertar.dao.RegistroUsuarioDao;
import com.coop.pilcrow.alertar.dao.exception.SolicitudUsuarioDaoException;
import com.coop.pilcrow.alertar.dao.impl.RegistroUsuarioDaoHttpImpl;
import com.coop.pilcrow.alertar.dto.RegistroUsuarioDto;
import com.coop.pilcrow.alertar.dto.RespuestaServerSolicitudDto;
import com.coop.pilcrow.alertar.dto.SolicitudUsuarioDto;
import com.coop.pilcrow.alertar.util.Message;
import com.flurry.android.FlurryAgent;

/**
 * Actividad que se encarga de registrar al usuario en el sistema.
 * @author gabriel.teolis
 *
 */
public class RegistrarUsuarioActivity extends Activity{
	/**
	 * Datos de configuración del botón.
	 */
	BotonConf botonConf = null;
	
	/**
	 * Botón que registra el móvil.
	 */
	private ImageButton botonRegistrar;
	/**
	 * Flag ya configurado.
	 */
	private boolean configurado = false;
	/**
	 * Thread que va a enviar datos al server.
	 */
	Thread envioDatosServerTh = null;
	/**
	 * Flag para saber si el thread está enviando datos.
	 */
	private boolean threadEnviando = false;
	/**
	 * Datos del usuario.
	 */
	private SolicitudUsuarioDto solicitudDto;
	/**
	 * le avisa cuando obtuvo datos del server.
	 */
	private Handler mensajeHandler = null;
	/**
	 * Mensajes para el usuario.
	 */
	private Message message;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);
        
        message = new Message(this.getBaseContext());
        
        //Cargo las configuraciones del usuario.
        SharedPreferences settings = getSharedPreferences(BotonConf.PREFS_NAME, 0);
        ConfigLoader.setSharedPreferences(settings);     
        botonConf = ConfigLoader.load();        
        
        configurado = (botonConf != null && BotonConf.isConfiguracionValida(botonConf));
        
        if(configurado){
        	iniciarBoton();
        }else{
	    	AlertDialog.Builder builderUsuario = new AlertDialog.Builder(this);
			
			builderUsuario.setMessage(R.string.msg_registrar_boton)
			       .setCancelable(true)
			       .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {	
			        	  // mostrarAdvertenciaUso();
			           }
			       })
			       .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	 //mostrarCartelNoConfigurado();
			        	 finish();
			           }
			       });
			AlertDialog alert = builderUsuario.create();		
			
			alert.show();	
	        botonRegistrar = (ImageButton) findViewById(R.id.registrar_usuario_button); 
	        botonRegistrar.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	registrarUsuario();  	
	            }
	        });
        }
    }
	/**
	 * Inicia le botón.
	 */
	private void iniciarBoton(){
		Intent intent = new Intent();
		intent.setClass(RegistrarUsuarioActivity.this, BotonesActivity.class);
		startActivity(intent);    
		this.finish();
		return;
	}
	/**
	 * Registra el móvil con los datos ingresados.
	 */
    private void registrarUsuario(){				
		
    	//registro con los datos.    	
		RegistroUsuarioDto registroUsuarioDto = new RegistroUsuarioDto();
		EditText nombreDuenioTxt = (EditText) this.findViewById(R.id.nombre_duenio_txt);
		registroUsuarioDto.setNombreUsuario(nombreDuenioTxt.getText().toString());
		EditText apellidoDuenioTxt = (EditText) this.findViewById(R.id.apellido_duenio_txt);
		registroUsuarioDto.setApellidoUsuario(apellidoDuenioTxt.getText().toString());
		EditText emailTxt = (EditText) this.findViewById(R.id.email_txt);
		registroUsuarioDto.setEMail(emailTxt.getText().toString());
		EditText caracteristicaTxt = (EditText) this.findViewById(R.id.caracteristica_telefono_txt);
		String caracteristica = caracteristicaTxt.getText().toString();
		EditText numeroTelefonoTxt = (EditText) this.findViewById(R.id.numero_telefono_txt);
		String numero = numeroTelefonoTxt.getText().toString();
		registroUsuarioDto.setNumeroCelular(caracteristica + "-"+ numero);
		
		//Obtengo los datos del celular.
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		if(telManager != null && telManager.getDeviceId() != null && !telManager.getDeviceId().equals("")){
			registroUsuarioDto.setIMei(telManager.getDeviceId());
		}else{
			registroUsuarioDto.setEMail(HttpConst.PREFIJO_SIN_IMEI + registroUsuarioDto.getEMail());	 
		}
		
		//Valido los datos ingresados por el usuario.
		if(registroUsuarioDto.getNombreUsuario().equals("") || registroUsuarioDto.getNombreUsuario().equals(BotonConf.VALOR_INVALIDO)
				|| registroUsuarioDto.getApellidoUsuario().equals("") || registroUsuarioDto.getApellidoUsuario().equals(BotonConf.VALOR_INVALIDO)
				|| registroUsuarioDto.getEMail().equals("") || registroUsuarioDto.getEMail().equals(BotonConf.VALOR_INVALIDO)
				|| numero.equals("") || numero.equals(BotonConf.VALOR_INVALIDO)
				|| caracteristica.equals("") || caracteristica.equals(BotonConf.VALOR_INVALIDO)){
			AlertDialog.Builder builderUsuario = new AlertDialog.Builder(this);
			
			//validación de campos requeridos.
			builderUsuario.setMessage(R.string.msg_configurar_error_valor_vacio)
			       .setCancelable(true)
			       .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {	
			        }
			       });
			AlertDialog alert = builderUsuario.create();		
			alert.show();	
			return;
		}
		
		//Envío la solicitud.
		enviarSolicitud(registroUsuarioDto);
	}
    
	
	/**
	 * Envia los datos al server.
 	 */
	private void enviarSolicitud(RegistroUsuarioDto registroUsuarioDto){
		
		//Si ya está enviando no le doy bola.
		if(threadEnviando){
			message.showLongTimeMessage("Estamos procesando su solicitud");
			return;
		}
		
		//muestro el progreso.
		findViewById(R.id.proceso_layout).setVisibility(View.VISIBLE); 
		
		//bloqueo los campos.
		bloquearCampos();
		
		//Genero los datos a mandar a partir de los datos del celular.
		UsuarioBo usuarioBo = new UsuarioBoImpl();
		try {
			solicitudDto = usuarioBo.generarSolicitudUsuario(registroUsuarioDto);
		} catch (RegistroUsuarioException e1) {
			message.showLongTimeMessage(e1.getMessage());
			habilitarCampos();
			findViewById(R.id.proceso_layout).setVisibility(View.GONE); 
			return;
		}

		//va a mandar los mensajes por pantalla con el resultado el envioDatosServerTh.
		mensajeHandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				String respuesta = (String) msg.obj;
				
				//terminó la conexión.
				threadEnviando = false;
				
				findViewById(R.id.proceso_layout).setVisibility(View.GONE); 
				
				if(respuesta.contains(HttpConst.HTTP_SERVER_OK)){
					guardarUsuario();
				}else{
					
					//muestro el resultado.
					Resources res = getResources();
					message.showLongTimeMessage(res.getString(R.string.msg_registrar_usuario_error));
					habilitarCampos();
				}
			}
		};
		
		//Sale a mandar los datos, cuando termina le avisa a mensajeHandler.
		envioDatosServerTh = new Thread(new Runnable() {
			@Override
			public void run() {
				android.os.Message msg = new android.os.Message();
				msg.obj = "0";
				
				//Aviso que está consultando.
				threadEnviando = true;
				RegistroUsuarioDao registroUsuarioDao = new RegistroUsuarioDaoHttpImpl();
				RespuestaServerSolicitudDto respuestaServerDto = null;
				boolean volverAEnviar = true;
				while(volverAEnviar){
					try {
						respuestaServerDto = registroUsuarioDao.enviarSolicitud(solicitudDto, HttpConst.PARAM_REGISTRAR);
						msg.obj = respuestaServerDto.getRespuesta();
						
						//Mientras la respuesta sea igual a vacío, sigo tratando de mandar los datos.
						volverAEnviar = respuestaServerDto.getRespuesta().equals("");						
					} catch (SolicitudUsuarioDaoException e) {
						msg.obj = e.getMessage();
					}
				}
				mensajeHandler.sendMessage(msg);
			}
		});
		envioDatosServerTh.start();
	}
    /**
     * Guarda los datos del usuario en el teléfono.
     */
	private void guardarUsuario(){
		
		//guardo el nombre de usuario, si sale con éxito quedará en la configuración del móvil.
		botonConf.setNombreUsuario(solicitudDto.getNombreUsuario());
		botonConf.setApellidoUsuario(solicitudDto.getApellidoUsuario());
		botonConf.setEMail(solicitudDto.getEMail());
		botonConf.setImei(solicitudDto.getIMei());
		botonConf.setNumTel(solicitudDto.getNumeroCelular());

		//Guardo la configuración.
		ConfigLoader.save(botonConf);
		
		//mensaje de éxito.
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage(R.string.msg_registrar_usuario_exitoso)
		       .setCancelable(false)
		       .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   mostrarAdvertenciaDeUso();
		           }
		       });
		AlertDialog alert = builder.create();		
		alert.show();
	}
	/**
	 * Voy a bloquear todos los text.
	 */
	private void bloquearCampos(){
		EditText nombreDuenioTxt = (EditText) this.findViewById(R.id.nombre_duenio_txt);
		nombreDuenioTxt.setEnabled(false);
		EditText apellidoDuenioTxt = (EditText) this.findViewById(R.id.apellido_duenio_txt);
		apellidoDuenioTxt.setEnabled(false);
		EditText emailTxt = (EditText) this.findViewById(R.id.email_txt);
		emailTxt.setEnabled(false);
		EditText caracteristicaTxt = (EditText) this.findViewById(R.id.caracteristica_telefono_txt);
		caracteristicaTxt.setEnabled(false);
		EditText numeroTelefonoTxt = (EditText) this.findViewById(R.id.numero_telefono_txt);
		numeroTelefonoTxt.setEnabled(false);
	}
	/**
	 * Habilito los campos.
	 */
	private void habilitarCampos(){
		EditText nombreDuenioTxt = (EditText) this.findViewById(R.id.nombre_duenio_txt);
		nombreDuenioTxt.setEnabled(true);
		EditText apellidoDuenioTxt = (EditText) this.findViewById(R.id.apellido_duenio_txt);
		apellidoDuenioTxt.setEnabled(true);
		EditText emailTxt = (EditText) this.findViewById(R.id.email_txt);
		emailTxt.setEnabled(true);
		EditText caracteristicaTxt = (EditText) this.findViewById(R.id.caracteristica_telefono_txt);
		caracteristicaTxt.setEnabled(true);
		EditText numeroTelefonoTxt = (EditText) this.findViewById(R.id.numero_telefono_txt);
		numeroTelefonoTxt.setEnabled(true);
	}
	/**
	 * Le muestra un mensaje de advertencia.
	 */
	private void mostrarAdvertenciaDeUso(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage(R.string.msg_advertencia_uso)
		       .setCancelable(false)
		       .setTitle(R.string.msg_titulo_advertencia)
		       .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   iniciarBoton();
		           }
		       });
		AlertDialog alert = builder.create();		
		alert.show();
	}
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		EditText nombreDuenioTxt = (EditText) this.findViewById(R.id.nombre_duenio_txt);
		EditText apellidoDuenioTxt = (EditText) this.findViewById(R.id.apellido_duenio_txt);
		EditText emailTxt = (EditText) this.findViewById(R.id.email_txt);
		EditText caracteristicaTxt = (EditText) this.findViewById(R.id.caracteristica_telefono_txt);
		EditText numeroTelefonoTxt = (EditText) this.findViewById(R.id.numero_telefono_txt);
		outState.putString("nombre", nombreDuenioTxt.getText().toString());
		outState.putString("apellido", apellidoDuenioTxt.getText().toString());
		outState.putString("mail", emailTxt.getText().toString());
		outState.putString("caract", caracteristicaTxt.getText().toString());
		outState.putString("numero", numeroTelefonoTxt.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		EditText nombreDuenioTxt = (EditText) this.findViewById(R.id.nombre_duenio_txt);
		nombreDuenioTxt.setText(savedInstanceState.getString("nombre"));
		EditText apellidoDuenioTxt = (EditText) this.findViewById(R.id.apellido_duenio_txt);
		apellidoDuenioTxt.setText(savedInstanceState.getString("apellido"));
		EditText emailTxt = (EditText) this.findViewById(R.id.email_txt);
		emailTxt.setText(savedInstanceState.getString("mail"));
		EditText caracteristicaTxt = (EditText) this.findViewById(R.id.caracteristica_telefono_txt);
		caracteristicaTxt.setText(savedInstanceState.getString("caract"));
		EditText numeroTelefonoTxt = (EditText) this.findViewById(R.id.numero_telefono_txt);
		numeroTelefonoTxt.setText(savedInstanceState.getString("numero"));
        
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();

	}
	@Override
	protected void onStart()
	{
		super.onStart();
		FlurryAgent.onStartSession(this, "FPJSSQ956RK969FWV5TF");
	}
	 
	@Override
	protected void onStop()
	{
		super.onStop();		
		FlurryAgent.onEndSession(this);
	}
}
