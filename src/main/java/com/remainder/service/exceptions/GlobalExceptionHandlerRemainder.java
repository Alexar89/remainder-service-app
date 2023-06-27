package com.remainder.service.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.remainder.service.utils.CodigosEstadoUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandlerRemainder {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerRemainder.class);
    private static final String REMAINDER_NOT_SUCCESS = "Un error inesperado ha ocurrido en la solicitud, contacte a su equipo de soporte IT";
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.BAD_REQUEST.value(), false, errorMessages.toString());
        return new ResponseEntity<>(obCodEst.mensajes(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid argument type for parameter '" + ex.getName() + "'";
        CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.BAD_REQUEST.value(), false, errorMessage);
        return new ResponseEntity<>(obCodEst.mensajes(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        log.error(REMAINDER_NOT_SUCCESS, ex);
        CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, ex.getMessage());
        return new ResponseEntity<>(obCodEst.mensajes(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error(REMAINDER_NOT_SUCCESS, ex);
        CodigosEstadoUtils obCodEst = new CodigosEstadoUtils(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, ex.getMessage());
        return new ResponseEntity<>(obCodEst.mensajes(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
