package com.barbershop.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsuarioExistenteException extends RuntimeException {
  public UsuarioExistenteException(String message) {
    super(message);
  }
}
