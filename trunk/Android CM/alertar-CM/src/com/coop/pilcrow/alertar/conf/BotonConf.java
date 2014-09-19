package com.coop.pilcrow.alertar.conf;

import java.util.TimeZone;

import android.content.SharedPreferences;

/**
 * Representa todas las configuraciones de la app, almacena los datos en un archivo con configuraciones del tipo SharedPreferences.
 * @author gabriel.teolis
 *
 */
public class BotonConf {
	/**
	 * Archivo de configuración de la Activity.
	 */
	private static SharedPreferences prefs;
	/**
	 * Nombre del archivo de configuración utilizado para almacenar los
	 * datos de preferencias del transmisor.
	 */
	public static final String PREFS_NAME = "alertarPrefsFile";
	 /**
	 * código transmisor utilizado para identificar cuando es inválido.
	 */
	public static String VALOR_INVALIDO = "NULL";
	/**
	 *Clave del atributo que especifica el nombre del usuario.
	 */
	public static String NOMBRE_USUARIO_KEY = "com.coop.alertarPrefsFile.nombreUsuario";
	/**
	 * Clave del atributo que especifica el apellido del usuario.
	 */
	public static String APELLIDO_USUARIO_KEY = "com.coop.alertarPrefsFile.apellidoUsuario"; 
	/**
	 *Clave del atributo que especifica el imei del móvil.
	 */
	public static String IMEI_KEY = "com.coop.alertarPrefsFile.imei";
	/**
	 *Clave del atributo que especifica el número de teléfono del móvil.
	 */
	public static String NUMTEL_KEY = "com.coop.alertarPrefsFile.numTel";
	/**
	 *Clave del atributo que especifica el email del móvil.
	 */
	public static String EMAIL_KEY = "com.coop.alertarPrefsFile.email";
	/**
	 *Clave del atributo que especifica la URL del dominio. 
	 */
	public static String DOMINIO_URL_KEY = "com.coop.alertarPrefsFile.dominioURL";	
	/**
	 *Clave del atributo que especifica la url del webservice a utilizar
	 */
	private static String SERVLET_REGISTRO_USUARIO_URL_KEY = "com.coop.alertar.registroMovil.url";
	/**
	 *Clave del atributo que especifica la url del webservice a utilizar
	 */
	private static String SERVLET_ALARMAR_URL_KEY = "com.coop.alertar.solicitudUsuario.url";
	/**
	 * URL del dominio de alertar.
	 */
	private String dominioURL = "http://www.gontaruk.com.ar/alertar";
	/**
	 * Intervalo de tiempo entre que se pide la informacion de posicion al
	 * dispositivo GPS
	 */
	private long intervaloObtencionSeg = 60;
	/**
	 * Intervalo de distancia entre que se pide la informacion de posicion al
	 * dispositivo GPS
	 */
	private long intervaloObtencionMetros = 50;
	/**
	 * Intervalo de tiempo en que se envia la informacion obtenida al servidor
	 * central
	 */
	private long intervaloEnvioSeg = 10;
	/**
	 * Nombre del usuario registrado
	 */
	private String nombreUsuario = VALOR_INVALIDO ;
	/**
	 * Apellido del usuario registrado
	 */
	private String apellidoUsuario = VALOR_INVALIDO ;
	/**
	 * EMail del usuario.
	 */
	private String eMail = VALOR_INVALIDO;
	/**
	 *ID  del timezone a utilizar.
	 */
	private String TIMEZONE_ID ="TimeZoneID";
	/**
	 * IMEI del teléfono.
	 */
	private String imei = VALOR_INVALIDO;
	/**
	 * número del teléfono.
	 */
	private String numTel = VALOR_INVALIDO;
	
	/**
	 * Verifica los valores configurables.
	 * @return
	 * 		true si todos los valores son validos.
	 */
	public static boolean isConfiguracionValida(BotonConf conf){
		if(	BotonConf.isValorConfiguracionValido(conf.getImei())&&
				BotonConf.isValorConfiguracionValido(conf.getNumTel())&&
				BotonConf.isValorConfiguracionValido(conf.getNombreUsuario()))
			return true;
		
		return false;
	}
	
	/**
	 * Determina si un valor de configuracion es valido.
	 * Es valido si no es null y si es distinto al valor invalido por defecto.
	 * @param valorConfiguracion
	 * @return true si el valor es válido, false si es un valor inválido
	 */
	public static boolean isValorConfiguracionValido(String valorConfiguracion) {
		return valorConfiguracion != null && !valorConfiguracion.equals(VALOR_INVALIDO);
	}	
	
	
	public String getNombreUsuario() {
	    return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
	    this.nombreUsuario = nombreUsuario;
	}
	/**
	 * Obtiene el intervalo de tiempo para obtener los datos que serán informados.
	 * Se pretenderá mantener la informacion de la
	 * posicion 1 vez cada este intervalo de tiempo.
	 *
	 */
	public long getIntervaloObtencionSeg(){
	    return intervaloObtencionSeg;
	}
	
	public void setIntervaloObtencionSeg(long intervaloObtencionSeg) {
	    this.intervaloObtencionSeg = intervaloObtencionSeg;
	}
	
	public long getIntervaloEnvioSeg() {
	    return intervaloEnvioSeg;
	}
	
	public void setIntervaloEnvioSeg(long intervaloEnvioSeg) {
	    this.intervaloEnvioSeg = intervaloEnvioSeg;
	}
	
    /**
     *Obtiene la url del web service
     */
    public static String getServletRegistroMovilURL(){
    	String dominio = prefs.getString(DOMINIO_URL_KEY, 
    			"http://www.gontaruk.com.ar/alertar");
        String url = prefs.getString(SERVLET_REGISTRO_USUARIO_URL_KEY,
        		"/savePosition.php");        
        return dominio.concat(url);
    }
    
    /**
     *Obtiene la url del web service
     */
    public static String getServletAlarmaURL(){
    	String dominio = prefs.getString(DOMINIO_URL_KEY, 
    			"http://www.gontaruk.com.ar/alertar");
        String url = prefs.getString(SERVLET_ALARMAR_URL_KEY,
        		"/savePosition.php");        
        return dominio.concat(url);
    }
   
    /**
     *Asigna el midlet del cual se obtendran configuraciones por medio de claves
     *de los atributos de la aplicacion
     */
    public static void setSharedPreferences(SharedPreferences sharedPreferences){
		prefs = sharedPreferences; 
	}
    public TimeZone getTimeZone() {
        String id = prefs.getString(TIMEZONE_ID,"GMT-03:00");
        TimeZone tz = TimeZone.getTimeZone(id);
        return tz;
    }
    /**
	 * Obtiene el intervalo de distancia para obtener los datos que serán informados.
	 * Se pretenderá mantener la informacion de la
	 * posicion 1 vez cada este intervalo de distancia.
	 *
	 */
	public long getIntervaloObtencionMetros(){
	    return intervaloObtencionMetros;
	}
	public void setIntervaloObtencionMetros(long intervaloObtencionMetros) {
	    this.intervaloObtencionMetros = intervaloObtencionMetros;
	}
	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDominioURL() {
		return dominioURL;
	}
	public void setDominioURL(String dominioURL) {
		this.dominioURL = dominioURL;
	}
	/**
	 * @return the eMail
	 */
	public String getEMail() {
		return eMail;
	}
	/**
	 * @param eMail the eMail to set
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the numTel
	 */
	public String getNumTel() {
		return numTel;
	}

	/**
	 * @param numTel the numTel to set
	 */
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	/**
	 * @return the apellidoUsuario
	 */
	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	/**
	 * @param apellidoUsuario the apellidoUsuario to set
	 */
	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}
}
