package com.remainder.service.utils;

import java.util.HashMap;
import java.util.Map;

public class CodigosEstadoUtils {
	/**
	 * Varibale que indica el status de la peticion
	 */
	public Integer status;
	/**
	 * Variable que indica  el estado de la peticion
	 */
	public Boolean estado;
	/**
	 * 
	 * Variable que indica el una descripcion del estado de la peticion
	 */
	public String descripcion;
	
	/**
	 * Constructor de la clase Codigos Estado
	 * @param status
	 * @param estado
	 * @param descripcion
	 */
	public CodigosEstadoUtils(Integer status,Boolean estado, String descripcion) {
		this.status = status;
		this.estado = estado;
		this.descripcion = descripcion;	
	}
	/**
	 * 
	 * @return
	 */
	public Map<?,?> mensajes(){
		Map<Object,Object> Mensaje = new HashMap<>();
		Mensaje.put("status", this.status);
		Mensaje.put("estado", this.estado);
		Mensaje.put("descripcion", this.descripcion);
		return Mensaje;
	}

}

