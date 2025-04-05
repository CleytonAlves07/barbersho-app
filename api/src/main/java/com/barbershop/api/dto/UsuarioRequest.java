package com.barbershop.api.dto;

import com.barbershop.api.domain.UsuarioEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

  @Schema(example = "João da Silva")
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Schema(example = "joaosilva")
  @NotBlank(message = "Username é obrigatório")
  private String username;

  @Schema(example = "senhaSegura123")
  @NotBlank(message = "Senha é obrigatória")
  private String password;

  @Schema(example = "joao.silva@email.com")
  @Email(message = "Email deve ser válido")
  @NotBlank(message = "Email é obrigatório")
  private String email;

  @Schema(example = "(84) 8318-7778")
  @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$",
      message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
  @NotBlank(message = "Telefone é obrigatório")
  private String telefone;

  @Schema(example = "CLIENTE", description = "Pode ser ADMIN, CLIENTE ou BARBEIRO")
  @NotNull(message = "Role é obrigatória")
  private UsuarioEntity.UsuarioRole role;
}
