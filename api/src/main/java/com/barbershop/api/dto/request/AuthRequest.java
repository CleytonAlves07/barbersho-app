package com.barbershop.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
  @Email(message = "Email deve ser válido")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  private String password;
}
