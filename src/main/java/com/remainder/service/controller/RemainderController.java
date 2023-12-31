package com.remainder.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.remainder.service.entity.RemainderInput;
import com.remainder.service.utils.ControllerUtils;



@RestController
@RequestMapping("/api/remainder")
public class RemainderController {
	
	private static final String REMAINDER_SUCCESS = "Calcule of Remainder Success";
	private static final Logger log = LoggerFactory.getLogger(RemainderController.class);
	
	/*  Posibles valores de K 
	 * Calculamos K1 como K = N – N % X + Y
	 * Calculamos K2 como K = N – N%X + Y – X.
	 * Si K1 esta dentro del rango [0, N], retorna K1.
	 * En otro caso, si K2 Esta en el rango de [0, N], retorna K2.
	 * Finalmente si no se cumple K1 o K2, retorna -1
	 */
    @PostMapping(value="/solveremainderpost",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>>  solveRemainderPost(final HttpServletRequest request, @RequestBody @Validated @NonNull RemainderInput testCase) {
    	
    	try {
    		
    		Map<String,Object> response = new HashMap<String,Object>();
    		ArrayList<Integer> results = new ArrayList<>();
    		//Sanitizar Valores
	    	Integer xValue = Integer.valueOf(testCase.getX());
			Integer yValue = Integer.valueOf(testCase.getY());
			Integer nValue = Integer.valueOf(testCase.getN());
			// Posibles valores de resultado
			Integer k1;
			Integer k2;
		
    		k1 = nValue - nValue % xValue + yValue;
    		k2 = nValue - nValue % xValue + (yValue- xValue);
    			
    		if(k1 <= nValue) {
    				
    			results.add(k1);
    				
    		}else {
    				
    			results.add(k2);
    				
    		}
				
			log.info(REMAINDER_SUCCESS);
			
			response.put("Remainder Result",results);
			
			//Verificar si se ha actualizado el token en la solicitud
			ControllerUtils.build().secured(response,request);
				
			return new ResponseEntity<>(response, HttpStatus.OK);
 
		} catch (Exception error) {
			throw error; // manejo con clase GlobalExceptionHandlerRemainder
		}
    }

    @GetMapping(value="/solveremainderget",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> solveRemainderGet(final HttpServletRequest request, @RequestParam  @NonNull Integer xValue, @RequestParam @NonNull Integer yValue, @RequestParam @NonNull Integer nValue) {
    	try {
    		
    		Map<String,Object> response = new HashMap<String,Object>();
    		ArrayList<Integer> results = new ArrayList<>();
    		
    		//Sanitizar Valores
	    	Integer xValueValid = Integer.valueOf(xValue);
			Integer yValueValid = Integer.valueOf(yValue);
			Integer nValueValid = Integer.valueOf(nValue);
    		
    		// Posibles valores de resultado
			Integer k1;
			Integer k2;
    		k1 = nValueValid - nValueValid % xValueValid + yValueValid;
    		k2 = nValueValid - nValueValid % xValueValid + (yValueValid- xValueValid);
    			
    		if(k1 <= nValue) {
    				
    			results.add(k1);
    				
    		}else {
    				
    			results.add(k2);
    				
    		}
				
			log.info(REMAINDER_SUCCESS);
			
			response.put("Remainder Result",results);
			
			// Verificar si se ha actualizado el token en la solicitud
			ControllerUtils.build().secured(response,request);
				
			return new ResponseEntity<>(response, HttpStatus.OK);
			

    	} catch (Exception error) {
			throw error; // manejo con clase GlobalExceptionHandlerRemainder
		}
    	
    }
    
}
