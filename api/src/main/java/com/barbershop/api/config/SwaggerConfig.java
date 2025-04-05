package com.barbershop.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI barberShopOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("BarberShop API")
            .description("API para sistema de agendamento de barbearia")
            .version("v1.0")
            .contact(new Contact()
                .name("Equipe BarberShop")
                .email("contato@barbershop.com"))
            .license(new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0")));
  }
}
