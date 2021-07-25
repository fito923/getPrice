package com.java.price.checking.controller;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.java.price.checking.config.Constants;
import com.java.price.checking.repositories.PricesRepository;
import com.java.price.checking.request.Request;
import com.java.price.checking.response.Response;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/test")
public class PricesCheckingController {
	
	
	@Autowired
	PricesRepository priceRepository;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	Constants constantes; 
	
	 @ApiParam
	@ApiResponses(value = {
	 @ApiResponse(code = 200, message = "OK"),
	 @ApiResponse(code = 400, message = "Incorrect format Date"),	 
	 @ApiResponse(code = 404, message = "Not results"),
	 @ApiResponse(code = 500, message = "Error")
	 })
	 
 
	 @PostMapping({"/v1.0.0/getPricesByBrandOrDateOrID", "/v2.0.0/getPricesByBrandOrDateOrID"})
	 public ResponseEntity<Response> create(@RequestBody 
			 @ApiParam(value = "Combinacion de marca, id producto y fecha (\"yyyy-MM-dd HH:mm:ss\")  por las que realizar la búsqueda.\n"
				 		+ "La busqueda puede ser realizada por cualquier combinacion de los criterios.\n"
				 		+ "v 1.0.0 Seguridad mediante Basic http. \n"
				 		+ "v 2.0.0 Seguridad mediante token (Header Authorization), obtenido en llamada previa a user. ",  required = true)
			 Request request
			 ) 
	     throws URISyntaxException {

		 
		 final HashMap<String, Object> data=new HashMap<>();
		 
		 final Long idPeticion=new Date().getTime();
		 
		 log.info("request\t\t idPeticion: {} ; id_producto  {}; marca {} ;fecha {} ", idPeticion, request.getId(), request.getMarca(), request.getFecha());
		
		Optional.ofNullable(request)
	    .map(Request::getId)
	    .ifPresent(code -> data.put(constantes.getId(),request.getId() ));
		
		
		Optional.ofNullable(request)
	    .map(Request::getMarca)
	    .ifPresent(code -> data.put(constantes.getMarca(),request.getMarca() ));
		
		
		Optional.ofNullable(request)
	    .map(Request::getFecha)
	    .ifPresent(code -> data.put(constantes.getFecha(),request.getFecha() ));
		
		
		 
		final Response result = new Response(new Timestamp(System.currentTimeMillis()), priceRepository.getData(data), 200, null,"Success");
		
		log.info("Response \t idPeticion: {}; tiempo respuesta : {} ms; respuesta: {}", idPeticion,( new Date().getTime()-idPeticion),result);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	 }
	
	 
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<Response> onversionFailedException(InvalidFormatException e) {
		final Response  result = new Response(new Timestamp(System.currentTimeMillis()),
				new ArrayList(), 400, "Incorrect format Date, correct is yyyy-MM-dd HH:mm:ss","Error");
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
			
		
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<Response> indexOutOfBoundsException(IndexOutOfBoundsException e) {		
		
		final Response  result = new Response(new Timestamp(System.currentTimeMillis()),
				new ArrayList(), 404, "Not results","Success");
		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
	}
	
}
