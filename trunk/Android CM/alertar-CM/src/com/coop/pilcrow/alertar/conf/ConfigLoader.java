package com.coop.pilcrow.alertar.conf;

import android.content.SharedPreferences;

/**
 * Se engarga de guardar y recuperar las preferencias de la app.
 * @author gabriel.teolis
 *
 */
public class ConfigLoader {
	/**
	 * Instancia única de configuracion de la aplicacion
	 */
	private static BotonConf botononf = null;
	/**
	 * Archivo de configuración de la Activity.
	 */
	private static SharedPreferences prefs;
	
	public static void setSharedPreferences(SharedPreferences sharedPreferences){
		prefs = sharedPreferences; 
	}
	/**
	 * Instancia de la configuración
	 * @return
	 * 		botonConf con los datos de configuración.
	 */
	public static BotonConf getInstance(){
	    if (botononf == null){
            botononf = ConfigLoader.load();
	    }
	    return botononf;
	        
	}	
	/**
	 * Carga los datos de configuración, de no estar configurado por 
	 * el usuario retorna los valores por defecto.
	 * @return
	 * 		botonConf con los datos de configuración.
	 */
	public static BotonConf load(){	    
	    BotonConf conf = new BotonConf();
	    conf.setDominioURL(prefs.getString(BotonConf.DOMINIO_URL_KEY, conf.getDominioURL()));
	    conf.setNombreUsuario(prefs.getString(BotonConf.NOMBRE_USUARIO_KEY, conf.getNombreUsuario()));
	    conf.setApellidoUsuario(prefs.getString(BotonConf.APELLIDO_USUARIO_KEY, conf.getApellidoUsuario()));
	    conf.setEMail(prefs.getString(BotonConf.EMAIL_KEY, conf.getEMail()));
	    conf.setImei(prefs.getString(BotonConf.IMEI_KEY, conf.getImei()));
	    conf.setNumTel(prefs.getString(BotonConf.NUMTEL_KEY, conf.getNumTel()));
	    BotonConf.setSharedPreferences(prefs);
	    
	    return conf;
	}
    /**
     * Guarda los datos de configuración en el archivo de la actividad.
     * @param prefs
     * 		Archivo de configuración de la actividad.
     * @param conf
     * 		botonConf con los datos de configuración a guardar.
     */
	public static void save(BotonConf conf){
	      SharedPreferences.Editor editor = prefs.edit();
	      editor.putString(BotonConf.NOMBRE_USUARIO_KEY, conf.getNombreUsuario());
	      editor.putString(BotonConf.APELLIDO_USUARIO_KEY, conf.getApellidoUsuario());
	      editor.putString(BotonConf.DOMINIO_URL_KEY, conf.getDominioURL());
	      editor.putString(BotonConf.EMAIL_KEY, conf.getEMail());
	      editor.putString(BotonConf.IMEI_KEY, conf.getImei());
	      editor.putString(BotonConf.NUMTEL_KEY, conf.getNumTel());
	      
	      // Commit the edits!
	      editor.commit();
	}
}
