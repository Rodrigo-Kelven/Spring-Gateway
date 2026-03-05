# Project Gateway - API Gateway com Spring Cloud

Sistema de API Gateway utilizando Spring Cloud Gateway para rotear requisições entre microserviços de Produtos e Pedidos.

---

## Sobre o Projeto

Este projeto demonstra a implementação do padrão **API Gateway** utilizando Spring Cloud Gateway. O sistema é composto por três aplicações Spring Boot que trabalham em conjunto:

- **Gateway** (porta 8080): Ponto de entrada único que roteia requisições para os microserviços
- **Products** (porta 8081): Microserviço responsável pelo gerenciamento de produtos
- **Pedidos** (porta 8082): Microserviço responsável pelo gerenciamento de pedidos

O API Gateway atua como um proxy inteligente que recebe todas as requisições dos clientes e as encaminha para o microserviço apropriado baseado no path da URL, fornecendo um ponto de entrada centralizado para a arquitetura de microserviços.

---

## Arquitetura

O diagrama abaixo ilustra o fluxo de requisições através do sistema:

```
┌─────────────────┐
│     Cliente     │
│  (Navegador/    │
│      App)       │
└────────┬────────┘
         │
         │ HTTP Request
         │
         v
┌──────────────────────────────────────────────┐
│       API Gateway (Port 8080)                │
│     Spring Cloud Gateway (Reactive)          │
│                                              │
│  Rotas:                                      │
│  • /products/** → http://localhost:8081     │
│  • /pedidos/**  → http://localhost:8082     │
└────────┬─────────────────────┬───────────────┘
         │                     │
         │ /products/**        │ /pedidos/**
         v                     v
┌────────────────────┐   ┌─────────────────────┐
│    Products        │   │      Pedidos        │
│   Port 8081        │   │     Port 8082       │
│  Spring Boot MVC   │   │   Spring Boot MVC   │
│                    │   │                     │
│  GET /products/{id}│   │  GET /pedidos/{id}  │
└────────────────────┘   └─────────────────────┘
```

### Roteamento

O Gateway utiliza predicates baseados em path para direcionar as requisições:

- **Requisições para `/products/**`**: Encaminhadas para o microserviço Products na porta 8081
- **Requisições para `/pedidos/**`**: Encaminhadas para o microserviço Pedidos na porta 8082

Este padrão permite:
- Centralização de cross-cutting concerns (autenticação, logging, rate limiting)
- Desacoplamento entre clientes e microserviços
- Facilidade para adicionar novos microserviços sem impactar clientes

---

## Tecnologias Utilizadas

### Linguagem e Framework

- **Java**: 21
- **Spring Boot**:
  - Gateway: 3.2.5
  - Products: 4.0.3
  - Pedidos: 4.0.3
- **Spring Cloud Gateway**: 2023.0.1 (stack reativo)
- **Spring Boot WebMVC**: Para os microserviços

### Build e Ferramentas

- **Maven**: 3.9.12 (Maven Wrapper incluído)
- **Arquitetura**: Maven Multi-Module Project
- **Empacotamento**: JAR executável

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Java Development Kit (JDK)** 21 ou superior
  - Verificar: `java -version`
- **Maven** 3.6+ (ou utilizar o Maven Wrapper incluído no projeto)
  - Verificar: `mvn -version`
- **Git** (para clonar o repositório)
  - Verificar: `git --version`
- **Terminal/Console** (Bash, PowerShell, CMD, etc.)
- **Navegador web** ou ferramenta para testar APIs (curl, Postman, Insomnia)

---

## Estrutura do Projeto

O projeto utiliza a estrutura Maven Multi-Module:

```
Project Gateway/
│
├── pom.xml                              # POM pai (agregador dos módulos)
├── mvnw / mvnw.cmd                      # Maven Wrapper scripts
├── .gitignore                           # Configuração Git
│
├── gateway/                             # Módulo API Gateway
│   ├── pom.xml                          # Dependências do Gateway
│   ├── HELP.md                          # Documentação auto-gerada
│   └── src/
│       ├── main/
│       │   ├── java/com/example/gateway/
│       │   │   └── GatewayApplication.java      # Classe principal
│       │   └── resources/
│       │       └── application.yaml             # Configuração de rotas
│       └── test/
│           └── java/com/example/gateway/
│               └── GatewayApplicationTests.java # Testes
│
├── products/                            # Módulo de Produtos
│   ├── pom.xml                          # Dependências do Products
│   ├── HELP.md                          # Documentação auto-gerada
│   └── src/
│       ├── main/
│       │   ├── java/com/example/products/
│       │   │   ├── ProductsApplication.java     # Classe principal
│       │   │   └── controller/
│       │   │       └── ProdutoController.java   # REST Controller
│       │   └── resources/
│       │       └── application.yaml             # Configuração do serviço
│       └── test/
│           └── java/com/example/products/
│               └── ProductsApplicationTests.java # Testes
│
└── pedidos/                             # Módulo de Pedidos
    ├── pom.xml                          # Dependências do Pedidos
    ├── HELP.md                          # Documentação auto-gerada
    └── src/
        ├── main/
        │   ├── java/com/example/pedidos/
        │   │   ├── PedidosApplication.java      # Classe principal
        │   │   └── controller/
        │   │       └── PedidoController.java    # REST Controller
        │   └── resources/
        │       └── application.yaml             # Configuração do serviço
        └── test/
            └── java/com/example/pedidos/
                └── PedidosApplicationTests.java # Testes
```

### Descrição dos Módulos

- **gateway/**: Contém a configuração do Spring Cloud Gateway com roteamento reativo
- **products/**: Microserviço REST para operações relacionadas a produtos
- **pedidos/**: Microserviço REST para operações relacionadas a pedidos

---

## Como Executar

### Opção 1: Usando Maven Wrapper (Recomendado)

Esta é a forma mais simples, pois o Maven Wrapper já está incluído no projeto.

```bash
# 1. Navegue até o diretório raiz do projeto
cd "Project Gateway"

# 2. Compile todos os módulos
./mvnw clean install

# 3. Abra 3 terminais separados e execute cada aplicação

# Terminal 1 - Products Service
cd products
../mvnw spring-boot:run

# Terminal 2 - Pedidos Service
cd pedidos
../mvnw spring-boot:run

# Terminal 3 - Gateway
cd gateway
../mvnw spring-boot:run
```

> **Nota**: No Windows, utilize `mvnw.cmd` ao invés de `./mvnw`

### Opção 2: Usando Maven Instalado

Se você já tem o Maven instalado no sistema:

```bash
# Compile todos os módulos
mvn clean install

# Execute cada aplicação em terminais separados
cd products && mvn spring-boot:run
cd pedidos && mvn spring-boot:run
cd gateway && mvn spring-boot:run
```

### Opção 3: Executando os JARs

```bash
# 1. Compile e empacote todas as aplicações
./mvnw clean package

# 2. Execute os JARs gerados (em terminais separados)
java -jar products/target/products-0.0.1-SNAPSHOT.jar
java -jar pedidos/target/pedidos-0.0.1-SNAPSHOT.jar
java -jar gateway/target/gateway-0.0.1-SNAPSHOT.jar
```

### Ordem de Inicialização

**Importante**: Inicie os microserviços (Products e Pedidos) **antes** do Gateway para evitar erros de roteamento durante a inicialização.

1. Products Service (porta 8081)
2. Pedidos Service (porta 8082)
3. Gateway (porta 8080)

### Verificando se as Aplicações Estão Rodando

```bash
# Verificar Products Service diretamente
curl http://localhost:8081/products/1
# Resposta esperada: Produto 1

# Verificar Pedidos Service diretamente
curl http://localhost:8082/pedidos/1
# Resposta esperada: Pedido 1

# Verificar roteamento através do Gateway
curl http://localhost:8080/products/1
# Resposta esperada: Produto 1

curl http://localhost:8080/pedidos/1
# Resposta esperada: Pedido 1
```

Se todos os comandos acima retornarem as respostas esperadas, o sistema está funcionando corretamente.

---

## Endpoints da API

### Acesso via Gateway (Porta 8080) - Recomendado

Esta é a forma recomendada de acessar os microserviços em produção.

#### Products API

**Endpoint**: `GET /products/{id}`

**Descrição**: Retorna informações sobre um produto específico.

**Exemplo de requisição**:
```bash
curl http://localhost:8080/products/123
```

**Resposta**:
```
Produto 123
```

**Exemplo com HTTP detalhado**:
```bash
curl -i http://localhost:8080/products/123
```

#### Pedidos API

**Endpoint**: `GET /pedidos/{id}`

**Descrição**: Retorna informações sobre um pedido específico.

**Exemplo de requisição**:
```bash
curl http://localhost:8080/pedidos/456
```

**Resposta**:
```
Pedido 456
```

**Exemplo com HTTP detalhado**:
```bash
curl -i http://localhost:8080/pedidos/456
```

### Acesso Direto aos Microserviços (Desenvolvimento)

Durante o desenvolvimento, você pode acessar os microserviços diretamente:

#### Products Service (Porta 8081)

```bash
curl http://localhost:8081/products/123
```

#### Pedidos Service (Porta 8082)

```bash
curl http://localhost:8082/pedidos/456
```

> **Nota Importante**: Em um ambiente de produção, apenas o Gateway (porta 8080) deve ser exposto publicamente. Os microserviços (portas 8081 e 8082) devem ser acessíveis apenas internamente na rede privada.

---

## Configuração

### Gateway Configuration

**Arquivo**: `gateway/src/main/resources/application.yaml`

```yaml
server:
  port: 8080

spring:
  main:
    web-application-type: reactive  # OBRIGATÓRIO para Spring Cloud Gateway

  application:
    name: gateway

  cloud:
    gateway:
      routes:
        - id: produto-route
          uri: http://localhost:8081
          predicates:
            - Path=/products/**

        - id: pedido-route
          uri: http://localhost:8082
          predicates:
            - Path=/pedidos/**

logging:
  level:
    org.springframework.cloud.gateway: DEBUG
```

#### Detalhes da Configuração

- **`web-application-type: reactive`**: Define que a aplicação usará o stack reativo (WebFlux), necessário para o Spring Cloud Gateway funcionar.
- **`routes`**: Lista de rotas que o Gateway irá gerenciar.
  - **`id`**: Identificador único da rota.
  - **`uri`**: URL de destino para onde as requisições serão encaminhadas.
  - **`predicates`**: Condições que determinam quando a rota será ativada.
    - **`Path=/products/**`**: Qualquer requisição que comece com `/products/` será encaminhada para o URI definido.
- **`logging.level.org.springframework.cloud.gateway: DEBUG`**: Ativa logs detalhados do Gateway, útil para troubleshooting.

### Products Configuration

**Arquivo**: `products/src/main/resources/application.yaml`

```yaml
spring:
  application:
    name: products

server:
  port: 8081
```

- **`application.name: products`**: Nome da aplicação, usado para identificação e logs.
- **`server.port: 8081`**: Porta onde o serviço de produtos será executado.

### Pedidos Configuration

**Arquivo**: `pedidos/src/main/resources/application.yaml`

```yaml
spring:
  application:
    name: pedidos

server:
  port: 8082
```

- **`application.name: pedidos`**: Nome da aplicação.
- **`server.port: 8082`**: Porta onde o serviço de pedidos será executado.

---

## Testes

### Executar Testes Automatizados

```bash
# Executar testes de todos os módulos
./mvnw test

# Executar testes de um módulo específico
cd gateway
../mvnw test

cd products
../mvnw test

cd pedidos
../mvnw test
```

### Cobertura Atual de Testes

Cada módulo possui testes básicos de carregamento de contexto:

- **GatewayApplicationTests.java**: Verifica se o contexto do Gateway carrega corretamente
- **ProductsApplicationTests.java**: Verifica se o contexto do Products carrega corretamente
- **PedidosApplicationTests.java**: Verifica se o contexto do Pedidos carrega corretamente

### Testes de Integração Manual

Para testar o sistema completo:

```bash
# 1. Certifique-se de que todas as aplicações estão rodando

# 2. Teste o roteamento de produtos via Gateway
curl -i http://localhost:8080/products/100
# Deve retornar: HTTP 200 OK com "Produto 100"

# 3. Teste o roteamento de pedidos via Gateway
curl -i http://localhost:8080/pedidos/200
# Deve retornar: HTTP 200 OK com "Pedido 200"

# 4. Teste uma rota inválida
curl -i http://localhost:8080/invalid/path
# Deve retornar: HTTP 404 Not Found

# 5. Teste acesso direto aos microserviços
curl http://localhost:8081/products/300
curl http://localhost:8082/pedidos/400
```

### Testes com Postman/Insomnia

Você também pode importar as seguintes requisições em ferramentas como Postman ou Insomnia:

**Collection de Exemplo**:
```
GET http://localhost:8080/products/1
GET http://localhost:8080/products/999
GET http://localhost:8080/pedidos/1
GET http://localhost:8080/pedidos/999
```

---

## Detalhes Técnicos

### Spring Cloud Gateway (Arquitetura Reativa)

O Spring Cloud Gateway é construído sobre:

- **Project Reactor**: Framework de programação reativa
- **Spring WebFlux**: Stack web reativo do Spring Framework
- **Netty**: Servidor web não-bloqueante

**Vantagens da arquitetura reativa**:
- Processamento assíncrono e não-bloqueante
- Melhor performance em cenários de alta concorrência
- Uso eficiente de recursos (threads e memória)
- Backpressure handling nativo

**Diferenças do Spring MVC tradicional**:
- Não usa Servlets (não roda em Tomcat tradicional)
- Usa modelo de programação funcional e reativo
- APIs retornam `Mono` e `Flux` ao invés de objetos diretamente

### Maven Multi-Module Project

O projeto utiliza estrutura multi-módulo para:

- **Gerenciamento centralizado**: O POM pai define versões e plugins comuns
- **Build coordenado**: Um único comando compila todos os módulos
- **Reutilização**: Módulos podem compartilhar dependências e configurações
- **Modularidade**: Cada serviço pode ser desenvolvido, testado e implantado independentemente

### Estratégia de Portas

| Serviço | Porta | Exposição |
|---------|-------|-----------|
| Gateway | 8080  | Pública (clientes externos) |
| Products | 8081  | Interna (apenas rede privada) |
| Pedidos | 8082  | Interna (apenas rede privada) |

Em produção, recomenda-se:
- Expor apenas a porta 8080 (Gateway) através de firewall/load balancer
- Manter as portas 8081 e 8082 acessíveis apenas na rede interna
- Usar service discovery (Eureka, Consul) ao invés de URLs hardcoded

---

## Melhorias Futuras / Roadmap

### Funcionalidades

- [ ] **Autenticação e Autorização**: Implementar Spring Security com JWT
- [ ] **Rate Limiting**: Limitar número de requisições por cliente
- [ ] **Circuit Breaker**: Implementar Resilience4j para tolerância a falhas
- [ ] **Service Discovery**: Integrar com Eureka ou Consul
- [ ] **Observabilidade**: Adicionar Micrometer + Prometheus para métricas
- [ ] **API Versioning**: Suporte para múltiplas versões de API
- [ ] **CORS Configuration**: Configurar CORS para acesso de diferentes origens
- [ ] **Distributed Tracing**: Implementar Spring Cloud Sleuth + Zipkin
- [ ] **Request/Response Logging**: Logs estruturados de todas as requisições
- [ ] **Global Error Handling**: Tratamento centralizado de erros no Gateway

### Infraestrutura

- [ ] **Dockerização**: Criar Dockerfile para cada aplicação
- [ ] **Docker Compose**: Ambiente local completo com um único comando
- [ ] **CI/CD Pipeline**: Integração contínua com GitHub Actions ou Jenkins
- [ ] **Health Checks**: Endpoints `/actuator/health` para monitoramento
- [ ] **Readiness/Liveness Probes**: Para Kubernetes
- [ ] **Spring Cloud Config**: Configuração externalizada e centralizada
- [ ] **Load Balancing**: Múltiplas instâncias de microserviços
- [ ] **API Documentation**: Swagger/OpenAPI para documentação interativa
- [ ] **Kubernetes Deployment**: Manifestos YAML para deploy em K8s
- [ ] **Ambiente Multi-Stage**: Dev, Staging, Production

### Qualidade de Código

- [ ] **Testes de Integração**: Testes end-to-end automatizados
- [ ] **Testes de Contrato**: Spring Cloud Contract entre serviços
- [ ] **Validação de Request/Response**: Bean Validation (JSR-303)
- [ ] **Code Quality Tools**: Integração com SonarQube
- [ ] **Static Code Analysis**: SpotBugs, Checkstyle, PMD
- [ ] **Code Coverage**: JaCoCo para medir cobertura de testes
- [ ] **Performance Testing**: JMeter ou Gatling

### Evolução dos Microserviços

- [ ] **Camada de Persistência**: Integrar JPA + PostgreSQL/MySQL
- [ ] **Mensageria Assíncrona**: RabbitMQ ou Apache Kafka
- [ ] **DTOs e Validações**: Separar entidades de domain e DTOs
- [ ] **Exception Handling**: `@ControllerAdvice` para tratamento global
- [ ] **Paginação e Filtros**: Suporte a query parameters
- [ ] **CRUD Completo**: Implementar POST, PUT, DELETE
- [ ] **Business Logic**: Camadas de Service e Repository
- [ ] **Database Migration**: Flyway ou Liquibase
- [ ] **Caching**: Redis ou Caffeine para cache distribuído
- [ ] **Search Functionality**: Elasticsearch para buscas avançadas

---

## Troubleshooting

### Problema: "Port already in use" (Porta já em uso)

**Sintoma**:
```
Web server failed to start. Port 8080 was already in use.
```

**Solução**:

**Linux/Mac**:
```bash
# Verificar qual processo está usando a porta
lsof -i :8080

# Matar o processo (substitua PID pelo número retornado)
kill -9 PID
```

**Windows**:
```cmd
# Verificar qual processo está usando a porta
netstat -ano | findstr :8080

# Matar o processo (substitua PID pelo número retornado)
taskkill /PID numero_pid /F
```

**Alternativa**: Alterar a porta no arquivo `application.yaml`:
```yaml
server:
  port: 8090  # Use uma porta diferente
```

---

### Problema: "404 Not Found" ao acessar via Gateway

**Sintoma**:
```bash
curl http://localhost:8080/products/1
# Retorna 404 Not Found
```

**Soluções**:

1. **Verificar se os microserviços estão rodando**:
```bash
curl http://localhost:8081/products/1  # Deve funcionar
curl http://localhost:8082/pedidos/1   # Deve funcionar
```

2. **Verificar logs do Gateway** (procure por erros de roteamento):
```
DEBUG org.springframework.cloud.gateway - Route matched: produto-route
```

3. **Confirmar o path está correto**:
   - Deve começar com `/products/` ou `/pedidos/`
   - O path é case-sensitive

4. **Reiniciar o Gateway** após iniciar os microserviços

---

### Problema: Gateway não inicia

**Sintoma**:
```
Parameter 0 of method modifyRequestBodyGatewayFilterFactory required a bean of type...
```

**Solução**:

Verifique se `web-application-type: reactive` está configurado no `application.yaml`:

```yaml
spring:
  main:
    web-application-type: reactive
```

---

### Problema: "Cannot resolve symbol SpringBootApplication"

**Sintoma**: Erros de compilação no IDE indicando que as dependências do Spring não foram encontradas.

**Solução**:

```bash
# Execute na raiz do projeto
./mvnw clean install

# No IntelliJ IDEA
# File > Invalidate Caches / Restart > Invalidate and Restart

# No Eclipse
# Right-click on project > Maven > Update Project
```

---

### Problema: Dependências não são baixadas

**Sintoma**:
```
Could not resolve dependencies for project...
```

**Soluções**:

1. **Verificar conexão com a internet**

2. **Limpar cache do Maven**:
```bash
./mvnw dependency:purge-local-repository
```

3. **Deletar pasta `.m2/repository` e rebuildar**:
```bash
rm -rf ~/.m2/repository
./mvnw clean install
```

4. **Verificar configurações de proxy** (se aplicável) no arquivo `~/.m2/settings.xml`

---

### Problema: Logs muito verbosos

**Solução**:

Ajustar nível de logging no `application.yaml`:

```yaml
logging:
  level:
    root: INFO
    org.springframework.cloud.gateway: WARN  # Reduzir de DEBUG para WARN
```

---

## Contribuindo

Contribuições são bem-vindas! Para contribuir com este projeto.