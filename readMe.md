# Sales Microservices API

Este projeto contém a arquitetura de microserviços para um sistema de vendas, incluindo gerenciamento de produtos, pedidos, gateway e serviço de registro (Eureka). Todos os serviços foram configurados para rodar via Docker e também podem ser executados localmente via Maven multi-module (fazendo alguns ajustes nas configurações do projeto).

---

## Tecnologias

![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green?logo=spring&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.0.0-blue?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-20.10-blue?logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker_Compose-1.29-blue?logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-multi--module-red?logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-red?logo=java&logoColor=white)
![JPA/Hibernate](https://img.shields.io/badge/JPA--Hibernate-orange?logo=hibernate&logoColor=white)



---

## Estrutura do Projeto

```arduino
sales-ms-api/
├── docker-compose.yml
├── init-db/
├── serviceregistry/  # Eureka Server
├── gateway/          # API Gateway
├── ms-product/       # Microserviço de Produtos
├── ms-order/         # Microserviço de Pedidos
└── pom.xml

```
---

## Rodando via Docker

1. Inicialize os containers com:

```bash
docker compose up --build
```

2. Serviços e portas configuradas:
   - PostgreSQL: 5433 (usuários ``postgres`` e ``user``)
   - Service Registry: 8761 (Eureka Server)
   - Gateway: 8080
   - MS Product: 8081
   - MS Order: 8082

3. Teste os serviços no Eureka acessando:
```arduino
http://localhost:8761/
```
Você deverá ver `ms-product` e `ms-order` registrados.

---

## Configurações importantes

### Banco de Dados

- Scripts de inicialização em `init-db/` criam os bancos `pedidos_db` e `vendas_db` e o usuário `user`.
- Docker Compose cria um volume próprio chamado `sales_ms_pgdata`.

### Eureka Server (serviceregistry)

- Porta: 8761
- Configuração: não registra a si mesmo, apenas atua como registry.

### Microserviços (ms-product, ms-order)

- Configuração de banco via Docker Compose.
- Eureka Client aponta para o Service Registry dentro do Docker.
- Cada serviço possui porta própria:
    - `ms-product`: 8081
    - `ms-order`: 8082
