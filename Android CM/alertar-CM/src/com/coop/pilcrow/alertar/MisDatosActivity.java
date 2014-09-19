package com.coop.pilcrow.alertar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
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
 * Le muestra y permite modificar datos al usuario. 
 * @author gustavo.gotaruk
 *
 */
public class MisDatosActivity extends Activity {
	
	/**
	 * Datos de configuración del botón.
	 */
	BotonConf botonConf = null;
	/**
	 * Botón que guarda los nuevos datos del usuario/vecino.
	 */
	private ImageButton botonGuardar;
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
        setContentView(R.layout.mis_datos);
        
        message = new Message(this.getBaseContext());
        
        //Cargo las configuraciones del usuario.
        SharedPreferences settings = getSharedPreferences(BotonConf.PREFS_NAME, 0);
        ConfigLoader.setSharedPreferences(settings);     
        botonConf = ConfigLoader.load(); 
        
        EditText nombre = (EditText) findViewById(R.id.nombre_duenio_txt);
        EditText apellido = (EditText) findViewById(R.id.apellido_duenio_txt);
        EditText email = (EditText) findViewById(R.id.email_txt);
        EditText caract_tel = (EditText) findViewById(R.id.caracteristica_telefono_txt);
        EditText numero_tel = (EditText) findViewById(R.id.numero_telefono_txt);
                
        String[] numeroCompleto = botonConf.getNumTel().split("-");

        caract_tel.setText(numeroCompleto[0]);
        numero_tel.setText(numeroCompleto[1]);
        nombre.setText(botonConf.getNombreUsuario());
        if(botonConf.getApellidoUsuario().equals(BotonConf.VALOR_INVALIDO)){
        	apellido.setText("");
        }else{
        	apellido.setText(botonConf.getApellidoUsuario());
        }
        email.setText(botonConf.getEMail());
        
        botonGuardar = (ImageButton) findViewById(R.id.guardar_datos_button); 
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	actualizarUsuario();  	
            }
        });        
	}
	
	
	/**
	 * Actualiza los datos del vecino en la base del server.
	 */
    private void actualizarUsuario(){				
    	
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
		
		//El imei no se puede modificar.
		registroUsuarioDto.setIMei(botonConf.getImei());	 
	
	
				 
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
			           public void onClick(DialogInterface dialog, int id) {}
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
			message.showLongTimeMessage("Se está procesando su solicitud");
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
					message.showLongTimeMessage(res.getString(R.string.msg_modificar_usuario_error));
				}
				
				habilitarCampos();
				//oculto el progreso.
				findViewById(R.id.proceso_layout).setVisibility(View.GONE); 
				
				//muestro el boton de Guardar
				findViewById(R.id.guardar_datos_button).setVisibility(View.VISIBLE); 
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
						respuestaServerDto = registroUsuarioDao.enviarSolicitud(solicitudDto, HttpConst.PARAM_ACTUALIZAR);
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
		builder.setMessage(R.string.msg_modificar_usuario_exitoso)
		       .setCancelable(false)
		       .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   volver();
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
	 * Cierra la activity.
	 */
	private void volver(){
		this.finish();	
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
