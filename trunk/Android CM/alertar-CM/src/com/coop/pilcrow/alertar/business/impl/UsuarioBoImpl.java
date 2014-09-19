package com.coop.pilcrow.alertar.business.impl;


import com.coop.pilcrow.alertar.business.UsuarioBo;
import com.coop.pilcrow.alertar.business.exception.RegistroUsuarioException;
import com.coop.pilcrow.alertar.dto.RegistroUsuarioDto;
import com.coop.pilcrow.alertar.dto.SolicitudUsuarioDto;

/**
 * Implementación de UsuarioBO.
 * Se va a encargar de manejar los datos de los usuarios.
 * @author gabriel.teolis
 *
 */
public class UsuarioBoImpl implements UsuarioBo{
	
	@Override
	public SolicitudUsuarioDto generarSolicitudUsuario(RegistroUsuarioDto registroUsuarioDto)
			throws RegistroUsuarioException {
		if(registroUsuarioDto == null){
			throw new RegistroUsuarioException(RegistroUsuarioException.SOLICITUD_VACIA);
		}
		if(registroUsuarioDto.getNombreUsuario() == null 
				|| registroUsuarioDto.getNombreUsuario().equals("") ||
				registroUsuarioDto.getNombreUsuario().trim().equals("") ){
			throw new RegistroUsuarioException(RegistroUsuarioException.NOMBRE_VACIO);
		}
		if(registroUsuarioDto.getNombreUsuario().trim().length() < 3){
			throw new RegistroUsuarioException(RegistroUsuarioException.TAM_NOMBRE);
		}
		if(registroUsuarioDto.getApellidoUsuario() == null 
				|| registroUsuarioDto.getApellidoUsuario().equals("") ||
				registroUsuarioDto.getApellidoUsuario().trim().equals("") ){
			throw new RegistroUsuarioException(RegistroUsuarioException.APELLIDO_VACIO);
		}
		if(registroUsuarioDto.getApellidoUsuario().trim().length() < 3){
			throw new RegistroUsuarioException(RegistroUsuarioException.TAM_APELLIDO);
		}
		if(!isAlphaNumeric(registroUsuarioDto.getNombreUsuario())){
			throw new RegistroUsuarioException(RegistroUsuarioException.NOMBRE_INVALIDO);
		}
		if(registroUsuarioDto.getEMail() == null || registroUsuarioDto.getEMail().equals("")){
			throw new RegistroUsuarioException(RegistroUsuarioException.EMAIL_VACIO);
		}
		if (!(registroUsuarioDto.getEMail().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
			throw new RegistroUsuarioException(RegistroUsuarioException.EMAIL_INVALIDO);
        }
		if(registroUsuarioDto.getIMei() == null || registroUsuarioDto.getIMei().equals("")){
			throw new RegistroUsuarioException(RegistroUsuarioException.IMEI_VACIO);
		}
		if(registroUsuarioDto.getNumeroCelular() == null || registroUsuarioDto.getNumeroCelular().equals("")){
			throw new RegistroUsuarioException(RegistroUsuarioException.NUMERO_CEL_VACIO);
		}
		if(registroUsuarioDto.getNumeroCelular().startsWith("0")){
			throw new RegistroUsuarioException(RegistroUsuarioException.NUMERO_CARACT_CERO);
		}
		if(registroUsuarioDto.getNumeroCelular().startsWith("0")){
			throw new RegistroUsuarioException(RegistroUsuarioException.NUMERO_CARACT_CERO);
		}
		String[] numeroCompleto = registroUsuarioDto.getNumeroCelular().split("-");
		if(numeroCompleto[1].length()< 6 ){
			throw new RegistroUsuarioException(RegistroUsuarioException.TAM_NUMERO_CEL);
		}		
		SolicitudUsuarioDto solicitudUsuarioDto = new SolicitudUsuarioDto();
		solicitudUsuarioDto.setNombreUsuario(registroUsuarioDto.getNombreUsuario());
		solicitudUsuarioDto.setApellidoUsuario(registroUsuarioDto.getApellidoUsuario());
		solicitudUsuarioDto.setEMail(registroUsuarioDto.getEMail());
		solicitudUsuarioDto.setIMei(registroUsuarioDto.getIMei());
		solicitudUsuarioDto.setNumeroCelular(registroUsuarioDto.getNumeroCelular());
		
		return solicitudUsuarioDto;
	}
	/**
	 * Valida si el string es alfanumerico.
	 * @param cadena
	 * 			string a validar
	 * @return
	 * 		true si es valido
	 * 		false si no lo es
	 */
	public boolean isAlphaNumeric(String cadena){ 
		String pattern= "^[a-zA-Z0-9 ]*$"; 
		if(cadena.matches(pattern)){ 
			return true; 
		}
		
		return false; 
	}

	
}
