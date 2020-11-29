package com.uxpsystems.assignment.exception;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The exceptions below could be raised by any controller and they would be
 * handled here, if not handled in the controller already.
 * 
 * @author Umesh.Chavan
 */

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex) {
		LOGGER.error("User not found: {}", ex.getMessage());
		LOGGER.trace("User not found: {}", ex);
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "User doesn't exist",
				ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserException.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handleUserException(Exception ex) {
		LOGGER.error("Failed to perform User operation: {}", ex.getMessage());
		LOGGER.trace("Failed to perform User operation: {}", ex);
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "Failed to perform User operation",
				ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.error("Invalid Method arguments: {}", ex.getMessage());
		LOGGER.trace("Invalid Method arguments: {}", ex);
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "Input validation failed.",
				ex.getBindingResult().toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class, HibernateException.class })
	@ResponseBody
	ResponseEntity<ErrorResponse> handleDatabase(Exception ex) {
		LOGGER.error("Database access exception: {}", ex.getMessage());
		LOGGER.trace("Database access exception: {}", ex);
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				"Database access error.", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	ResponseEntity<ErrorResponse> handleException(Exception ex) {
		LOGGER.error("Unknown exception: {}", ex.getMessage());
		LOGGER.trace("Unknown exception: {}", ex);
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				"Unknown Exception occured.", ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
