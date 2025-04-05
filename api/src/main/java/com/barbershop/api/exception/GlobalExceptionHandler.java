package com.barbershop.api.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage()));

    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      String fieldName = violation.getPropertyPath().toString();
      errors.put(fieldName, violation.getMessage());
    });

    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", errors);
  }

  @ExceptionHandler(UsuarioExistenteException.class)
  public ResponseEntity<Map<String, Object>> handleUsuarioExistenteException(UsuarioExistenteException ex) {
    return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado", null);
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, Map<String, String> errors) {
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", LocalDateTime.now());
    response.put("status", status.value());
    response.put("error", status.getReasonPhrase());
    response.put("message", message);

    if (errors != null && !errors.isEmpty()) {
      response.put("errors", errors);
    }

    return ResponseEntity.status(status).body(response);
  }
}
