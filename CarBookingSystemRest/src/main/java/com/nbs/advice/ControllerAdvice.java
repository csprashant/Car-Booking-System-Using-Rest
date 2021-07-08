package com.nbs.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nbs.exception.UserNotFoundException;
import com.nbs.exception.VehicleNotFoundException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
		
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handlingNullPointerException(NullPointerException nep){
		return new ResponseEntity<>("you dont have privilege for this URL",HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handlingUserNotFoundException(UserNotFoundException unfe){
		return new ResponseEntity<>("No User with this id present in the Database",HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(VehicleNotFoundException.class)
	public ResponseEntity<String> handlingVehicleNotFoundException(VehicleNotFoundException vnfe){
		return new ResponseEntity<>("No vehicle with this id present in the Database",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException nee){
		return new ResponseEntity<>("No value present in Database",HttpStatus.NOT_FOUND);
		}
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>("Please check  http method",HttpStatus.NOT_FOUND);
	}
		
}
