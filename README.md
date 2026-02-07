# sus-triage-api
Tech Challenge 5 - Hackaton

---

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como parte do **Tech Challenge 5 - Hackathon** da pós-graduação em **Arquitetura e Desenvolvimento Java**, com foco em **inovação para otimização de atendimento no SUS**.

### 🎯 Problema Abordado

O sistema visa resolver o problema de **triagem e acolhimento inteligente** nas unidades de saúde do SUS, auxiliando na:

- ✅ **Priorização de atendimentos** com base na gravidade dos sintomas
- ✅ **Redução da superlotação** através de direcionamento eficiente
- ✅ **Atendimento rápido** para pacientes em situação de emergência
- ✅ **Melhoria da eficiência operacional** dos profissionais de saúde
- ✅ **Transparência** no processo de triagem e atendimento
- ✅ **Melhor experiência** para pacientes e colaboradores do SUS

### 💡 Solução Proposta

Sistema backend robusto que implementa:
- Classificação automática de risco baseada em sintomas e sinais vitais
- Priorização inteligente de atendimentos seguindo protocolos médicos
- API RESTful para integração com diferentes front-ends

### 📁 Estrutura de Pastas

### 🔄 Fluxo de Dados

1. **Cliente** → Faz requisição HTTP para a API
2. **Controller** → Recebe e valida a requisição
3. **Service** → Processa a lógica de negócio (classificação de risco)
4. **Repository** → Persiste ou recupera dados
5. **Response** → Retorna resultado ao cliente

---

## 📦 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

### Obrigatórios
- ☕ **Java 21** ou superior ([Download](https://www.oracle.com/java/technologies/downloads/))
- 📦 **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))

### Opcionais (mas recomendados)
- 🐳 **Docker** ([Download](https://www.docker.com/get-started))
- 🐋 **Docker Compose** (incluído no Docker Desktop)
- 📝 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Verificar instalação

```bash
# Verificar versão do Java
java -version

# Verificar versão do Maven
mvn -version

# Verificar versão do Docker
docker --version
docker-compose --version

```
---## 🚀 Como Executar o Projeto

# 1. Clone o repositório
git clone https://github.com/wagnersistemalima/sus-triage-api.git
cd sus-triage-api

# 2. Compile o projeto
mvn clean install

# 3. Execute a aplicação
mvn spring-boot:run

# Ou execute o JAR gerado
java -jar target/sus-triage-api-0.0.1-SNAPSHOT.jar

# Opção 2: Com Docker

# 1. Build da imagem Docker
docker build -t sus-triage-api:latest .

# 2. Executar container
docker run -p 8080:8080 sus-triage-api:latest

# 3. Verificar container em execução
docker ps

# Opção 3: Com Docker Compose (Recomendado para Produção)

# 1. Subir todos os serviços
docker-compose up -d

# 2. Ver logs em tempo real
docker-compose logs -f

# 3. Verificar status dos serviços
docker-compose ps

# 4. Parar serviços
docker-compose down

# 5. Rebuild e restart
docker-compose up -d --build

# A aplicação estará disponível em: http://localhost:8080

## 📡 Endpoints da API

# 🏥 Health Check

GET /actuator/health

Resposta:
````
{
"status": "UP"
}

````

## criar um novo paciente:
POST /api/pacientes
Request Body:
````
{
"nome": "João Silva",
"cpf": "123.456.789-00",
"latitude": -23.5505,
"longitude": -46.6333
}

Resposta:
````
{
"id": 1,
"nome": "João Silva",
"cpf": "123.456.789-00",
"latitude": -23.5505,
"longitude": -46.6333
}
````
