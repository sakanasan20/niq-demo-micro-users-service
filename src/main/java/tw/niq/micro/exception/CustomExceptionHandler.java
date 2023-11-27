package tw.niq.micro.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tw.niq.micro.model.ErrorModel;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString();
		ErrorModel errorModel = new ErrorModel(new Date(), message);
		return new ResponseEntity<>(errorModel, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString();
		ErrorModel errorModel = new ErrorModel(new Date(), message);
		return new ResponseEntity<>(errorModel, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
