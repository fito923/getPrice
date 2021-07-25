package com.java.price.checking.exception.control;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.java.price.checking.controller.PricesCheckingController;
import com.java.price.checking.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionGeneralResponse {
	Response result;

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Response> runtimeException(RuntimeException e) {
		result = new Response(new Timestamp(System.currentTimeMillis()), null, 500, e.getMessage(),"Error");
		log.error("Error RuntimeException", e);		
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exception(Exception e) {
		result = new Response(new Timestamp(System.currentTimeMillis()), null, 500,e.getMessage(), "Error");
		log.error("ExceptionHandler Exception", e);
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
