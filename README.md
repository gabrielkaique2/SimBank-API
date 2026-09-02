# SimBank API

API REST para simulação de operações bancárias em Java com Spring Boot.

## Visão geral

O projeto expõe endpoints para gerenciamento de usuários, contas bancárias e registros de transações. A estrutura segue o padrão MVC com camadas de controller, service, repository e entidade JPA.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Maven

## Estrutura do projeto

```text
SimBank-API/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/rodrigues/simbank/SimBank_API/
│   │   │   ├── Controllers/
│   │   │   │   ├── ContaController.java
│   │   │   │   ├── TransacaoController.java
│   │   │   │   └── UsuarioController.java
│   │   │   ├── DTO/
│   │   │   │   ├── ContaRequestDTO.java
│   │   │   │   ├── ContaResponseDTO.java
│   │   │   │   ├── DepositoRequestDTO.java
│   │   │   │   ├── SaqueRequestDTO.java
│   │   │   │   ├── TransacaoRequestDTO.java
│   │   │   │   ├── TransacaoResponseDTO.java
│   │   │   │   ├── TransferenciaRequestDTO.java
│   │   │   │   ├── UsuarioRequestDTO.java
│   │   │   │   └── UsuarioResponseDTO.java
│   │   │   ├── Entities/
│   │   │   │   ├── Conta.java
│   │   │   │   ├── Transacao.java
│   │   │   │   └── Usuario.java
│   │   │   ├── Exception/
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── Repositories/
│   │   │   │   ├── ContaRepository.java
│   │   │   │   ├── TransacaoRepository.java
│   │   │   │   └── UsuarioRepository.java
│   │   │   ├── Services/
│   │   │   │   ├── ContaService.java
│   │   │   │   ├── TransacaoService.java
│   │   │   │   └── UsuarioService.java
│   │   │   ├── Utilidades/
│   │   │   │   ├── TipoConta.java
│   │   │   │   └── TipoTransacao.java
│   │   │   ├── SimBankApiApplication.java
│   │   │   └── ...
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/rodrigues/simbank/SimBank_API/SimBankApiApplicationTests.java
├── HELP.md
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── .gitattributes
└── README.md
```

## Funcionalidades

- Cadastro, listagem, busca, atualização e remoção de usuários
- Cadastro, listagem, busca, atualização e remoção de contas
- Depósito em conta
- Saque com validação de saldo
- Registro de transações com tipo, valor e timestamp

## Endpoints principais

### Usuários

- `POST /api/usuarios`
- `GET /api/usuarios`
- `GET /api/usuarios/{rowid}`
- `PUT /api/usuarios/{rowid}`
- `DELETE /api/usuarios/{rowid}`

### Contas

- `POST /api/contas`
- `GET /api/contas`
- `GET /api/contas/{rowid}`
- `PUT /api/contas/{rowid}`
- `DELETE /api/contas/{rowid}`

### Transações

- `POST /api/transacoes/deposito`
- `POST /api/transacoes/saque`

## Banco de dados

A aplicação usa PostgreSQL e a configuração padrão esperada está em `src/main/resources/application.properties`.

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bank
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

Antes de iniciar a aplicação, certifique-se de que um banco PostgreSQL chamado `bank` exista e que as credenciais estejam corretas.

## Como executar

1. Clone o repositório
2. Configure o PostgreSQL local
3. Ajuste as credenciais no arquivo `application.properties`
4. Execute:

```bash
./mvnw spring-boot:run
```

Ou no Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação iniciará em:

```text
http://localhost:8080
```

## Observações

- O projeto está organizado por responsabilidades, separando entidades, serviços, controladores e DTOs.
- A camada de exceções já inclui `ResourceNotFoundException` para cenários de registro inexistente.
- O projeto é um bom ponto de partida para evoluir com transferências, autenticação, validações e testes automatizados.

## Autor

Projeto desenvolvido em Java/Spring Boot para simulação de operações bancárias.
