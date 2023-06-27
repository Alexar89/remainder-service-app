package com.remainder.service.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.remainder.service.utils.CodigosEstadoUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("login/")
public class LoginController {
	
	private static final String LOGIN_CORRECT = "Ingreso autorizado";
	private static final String FORBIDDEN_ACCESS= "Acceso no permitido";
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);


	@Value("${session.duration.hours:1}")
	private int hours;

	@Value("${session.duration.minutes:0}")
	private int minutes;

	@Value("${application.secret}")
	private String secret;

	private long session;

	@PostMapping(value = "/ingresa",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(final HttpServletRequest request, @RequestHeader("login") @NonNull String sLogin, @RequestHeader("clave") @NonNull String sClave) {
		try {
			// Sanitizar clave y login
			String loginValidado = String.valueOf(request.getHeader("login"));
			String claveValidada = String.valueOf(request.getHeader("clave"));
			// emula la confirmación de que el usuario existe en base de datos
			Boolean resultadoLogin = true; 
			
			if(resultadoLogin) {
				
				if(!loginValidado.isBlank() && !claveValidada.isBlank()) {
					
					Date current = new Date();
					
					Map<Object, Object> result = new HashMap<>();
					
					if(session == 0) {
						this.session = TimeUnit.HOURS.toMillis(hours)
								+ TimeUnit.MINUTES.toMillis(minutes);
						log.info(String.format("Session duration : %d millis", this.session));
					}
					
					result.put("token",Jwts.builder().setSubject(loginValidado)
							.claim("activo", "Si")
							.claim("Detalle", "Autorizado")
							.claim("menus", "Lista de opciones")
							.setIssuedAt(current)
							.setExpiration(new Date(current.getTime() + session))
							.signWith(SignatureAlgorithm.HS256,secret).compact());
					
					log.info(LOGIN_CORRECT);
					
					return new ResponseEntity<>(result,HttpStatus.OK);
				}
			}
			
			CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.FORBIDDEN.value(),false,FORBIDDEN_ACCESS);
			log.warn(FORBIDDEN_ACCESS);
			return new ResponseEntity<>(obCodEst,HttpStatus.FORBIDDEN);
			
		} catch (Exception exception) {
			throw exception;
		}
	}

}
