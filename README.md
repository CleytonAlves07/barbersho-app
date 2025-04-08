# Barber API

Este projeto é uma API desenvolvida em Java para gerenciamento de agendamentos em uma barbearia.

## Pré-requisitos

- Docker
- Java 17+
- Maven

## Instruções para subir o projeto

1. **Subir o banco de dados PostgreSQL com Docker:**
    # docker compose up -d

## Acessar o Swagger
[Acesse o Swagger](http://localhost:8080/swagger-ui/index.html#/)

```mermaid
erDiagram
    USUARIO ||--o{ AGENDA : "tem"
    USUARIO ||--o{ AGENDAMENTO : "realiza"
    AGENDA ||--o{ AGENDAMENTO : "possui"

    USUARIO {
        UUID id PK
        string username
        string password
        string nome
        string email
        string telefone
        enum role
    }

    AGENDA {
        UUID id PK
        DateTime horarioInicio
        DateTime horarioFim
        UUID barbeiro_id FK
    }

    AGENDAMENTO {
        UUID id PK
        DateTime dataHora
        string status
        UUID cliente_id FK
        UUID agenda_id FK
    }
```