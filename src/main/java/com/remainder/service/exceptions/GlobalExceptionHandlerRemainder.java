package com.remainder.service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.remainder.service.utils.CodigosEstadoUtils;
import com.remainder.service.utils.ControllerUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandlerRemainder {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerRemainder.class);
    private static final String REMAINDER_NOT_SUCCESS = "Un error inesperado ha ocurrido en la solicitud, contacte a su equipo de soporte IT";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        log.error(REMAINDER_NOT_SUCCESS, ex.getMessage());
        
        CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, ex.getMessage());
        
        return new ResponseEntity<>(obCodEst.mensajes(), HttpStatus.CONFLICT);
        
    }

}
