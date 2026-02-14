# 🏥 SUS Triage API - Sistema Inteligente de Triagem e Alocação

> **Tech Challenge 5 - Hackathon FIAP 2026** > Pós-Graduação em Arquitetura e Desenvolvimento Java

---

## 📑 Índice

* [Sobre o Projeto](https://www.google.com/search?q=%23-sobre-o-projeto)
* [Diferenciais Técnicos](https://www.google.com/search?q=%23-diferenciais-t%C3%A9cnicos)
* [Tecnologias Utilizadas](https://www.google.com/search?q=%23-tecnologias-utilizadas)
* [Arquitetura](https://www.google.com/search?q=%23-arquitetura)
* [Fluxo de Processamento](https://www.google.com/search?q=%23-fluxo-de-processamento)
* [Pré-requisitos](https://www.google.com/search?q=%23-pr%C3%A9-requisitos)
* [Como Executar](https://www.google.com/search?q=%23-como-executar)
* [Endpoints da API](https://www.google.com/search?q=%23-endpoints-da-api)
* [Demonstração](https://www.google.com/search?q=%23-demonstra%C3%A7%C3%A3o)
* [Monitoramento](https://www.google.com/search?q=%23-monitoramento)
* [Equipe](https://www.google.com/search?q=%23-equipe)

---

## Resumo Executivo

O SUS Triage API é uma solução de backend de alta performance projetada para modernizar a porta de entrada das unidades de saúde.
Utilizando o Protocolo de Manchester automatizado e algoritmos de geolocalização, o sistema elimina a subjetividade na triagem e
direciona pacientes para unidades com capacidade real de atendimento em milissegundos. A solução resolve o gargalo da superlotação
através de uma arquitetura assíncrona (Event-Driven), garantindo que o sistema nunca saia do ar, mesmo em situações de catástrofe
ou alta demanda.

---

## 📋 Sobre o Projeto

Sistema backend desenvolvido para o **Hackathon Tech Challenge 5** que implementa **triagem inteligente** e **alocação automatizada** de pacientes em unidades de saúde do SUS.

### 🎯 Problema Abordado

No cenário atual do SUS, a triagem manual e a falta de integração entre unidades geram três problemas críticos:

* **Subjetividade e Erro Humano:** A classificação de risco depende do cansaço e da interpretação momentânea do profissional.
* **Ineficiência Logística:** Pacientes graves são frequentemente levados a unidades (UPAs/Hospitais) que já estão lotadas, perdendo tempo precioso de deslocamento (a "hora de ouro").
* **Falta de Visibilidade:** Não há uma visão em tempo real da demanda versus capacidade da rede.

Sendo assim, pensamos em otimizar o processo de **triagem e alocação** nas unidades de saúde através de:

* ✅ **Classificação automática de risco** (Protocolo de Manchester)
* ✅ **Alocação inteligente** por proximidade e disponibilidade
* ✅ **Processamento assíncrono** para alta performance
* ✅ **Priorização de emergências** via filas de mensageria
* ✅ **Redução de superlotação** através de direcionamento eficiente
* ✅ **Transparência** no fluxo de atendimento

### 💡 O Impacto da Solução

Nossa solução ataca diretamente a mortalidade evitável e a eficiência operacional:

* **Para o Paciente:** Redução drástica no tempo de espera e garantia de atendimento na unidade correta (geolocalizada).
* **Para o Profissional:** Ferramenta de apoio à decisão que automatiza a burocracia, permitindo foco no atendimento clínico.
* **Para a Gestão Pública:** Dados auditáveis e capacidade de prever surtos de demanda baseados nos logs de triagem em tempo real.

### 💡 Solução Implementada

#### 🚀 **Arquitetura Event-Driven com RabbitMQ**

* Response instantâneo (< 100ms) para triagem
* Alocação de unidades processada em background
* Escalabilidade horizontal para milhares de triagens simultâneas
* Resiliência a falhas com mensageria persistente

#### 🎯 **Classificação Inteligente**

* 5 níveis de risco (VERMELHO, LARANJA, AMARELO, VERDE, AZUL)
* Análise de sinais vitais em tempo real
* Protocolo de Manchester adaptado

#### 📍 **Alocação Geolocalizada**

* Cálculo de distância paciente-unidade
* Seleção automática da unidade mais próxima com vaga
* Filtro por tipo adequado ao risco (HOSPITAL, UPA, UBS)

---

## 🌟 Diferenciais Técnicos

### 1️⃣ **Arquitetura Assíncrona de Alta Performance**

```
┌──────────────────────────────────────────────────────┐
│  Response Instantâneo (< 100ms)                      │
│  +                                                    │
│  Processamento em Background (200-500ms)             │
│  =                                                    │
│  Experiência do Usuário Otimizada                    │
└──────────────────────────────────────────────────────┘

```

**Benefícios:**

* ✅ Desacoplamento entre triagem e alocação
* ✅ Throughput elevado (milhares de triagens/segundo)
* ✅ Processamento paralelo via RabbitMQ
* ✅ Cliente não espera processamento pesado

### 2️⃣ **Garantia de Idempotência e Consistência**

* ✅ **Idempotent Consumer:** O sistema detecta se uma mensagem do RabbitMQ já foi processada (através do status da Triagem no BD). Isso evita que falhas de rede causem "double-spending" de vagas em hospitais.
* ✅ **Integridade de Dados:** Garantia de que a ocupação da unidade de saúde só é incrementada uma única vez por paciente, mesmo em casos de retentativas automáticas da fila.

### 3️⃣ **Estratégia de Cache Distribuído (Redis)**

* ✅ **Cache de Unidades:** Redução drástica de acessos ao PostgreSQL através do cache de informações estáticas e geográficas das unidades de saúde no Redis.
* ✅ **Performance de Consulta:** Respostas ultrarrápidas na busca por unidades compatíveis durante o pico de demanda.

### 4️⃣ **Escalabilidade Comprovada**

* ✅ Múltiplos consumers para processamento paralelo
* ✅ Filas persistentes com dead-letter queue
* ✅ Retry automático em caso de falha
* ✅ Preparado para ambiente de produção

**Como Funciona o Processamento Paralelo:**

A aplicação está configurada para processar múltiplas mensagens simultaneamente através de:

1. **Múltiplos Threads na Mesma Instância**
* **Dev:** 3 a 10 consumers simultâneos
* **Prod:** 5 a 20 consumers simultâneos
* Spring AMQP ajusta dinamicamente conforme a carga

2. **Múltiplas Instâncias (Horizontal Scaling)**
```bash
# Escalar para 3 instâncias no Docker Compose
docker compose up --scale sus-triage-api=3 -d

```

* RabbitMQ distribui mensagens entre todas as instâncias
* Cada instância pode ter até 20 consumers (em prod)
* **Capacidade total:** 3 instâncias × 20 consumers = **60 triagens simultâneas**

3. **Combinação (Máxima Escalabilidade)**
* Escalar horizontalmente (mais containers)
* Cada container com múltiplos threads
* Tolerância a falhas: se 1 container cair, os outros continuam

**Configuração Atual:**

```yaml
# application-dev.yml
concurrency: 3        # Mínimo de 3 threads
max-concurrency: 10   # Até 10 threads sob carga

# application-prod.yml
concurrency: 5        # Mínimo de 5 threads
max-concurrency: 20   # Até 20 threads sob carga

```

### 5️⃣ **Observabilidade Completa**

* ✅ Logs estruturados em cada etapa do processo
* ✅ Spring Boot Actuator para métricas de saúde
* ✅ RabbitMQ Management UI para visualização de filas
* ✅ Rastreamento end-to-end de requisições

### 6️⃣ **Aderência ao Mundo Real**

- ✅ Reflete processos reais de triagem do SUS
- ✅ Separação entre triagem e alocação (como no SUS real)
- ✅ Filas de espera com priorização
- ✅ Protocolos médicos reconhecidos (Manchester)

---

## 🛠️ Tecnologias Utilizadas

### Backend

* ☕ **Java 21** (LTS)
* 🍃 **Spring Boot 4.0.2**
* Spring Web (REST APIs)
* Spring Data JPA (Persistência)
* Spring AMQP (RabbitMQ)
* Spring Validation (Bean Validation)
* Spring Boot Actuator (Monitoramento)
* Spring Cache (Integração com Redis)

### Persistência & Cache

* 🐘 **PostgreSQL** (Banco de dados relacional para persistência de triagens e unidades)
* 💾 **H2 Database** (Utilizado em ambiente de Desenvolvimento e Testes)
* 🔴 **Redis** (Cache distribuído para otimizar a busca de unidades de saúde e reduzir latência)

### Mensageria

* 🐰 **RabbitMQ 3.13**
* DirectExchange para roteamento
* Filas persistentes com durabilidade
* Dead-letter queue para tratamento de erros fatais

### Ferramentas & DevOps

* 📝 **Swagger/OpenAPI 3** (Documentação interativa)
* 🐳 **Docker & Docker Compose** (Containerização de toda a stack)
* 🤖 **GitHub Actions** (Automação de PRs e Pipeline de CI/CD)
* 🔧 **Maven** (Gerenciamento de build e dependências)
* 🧪 **JUnit 5** (Testes automatizados)

---

## 🧠 Processo de Desenvolvimento & Design Thinking

Para conceber esta solução, a equipe seguiu um fluxo estruturado de ideação e validação:

### 1. Mapeamento e Empatia (Personas)
Criamos personas para entender a dor real:
Dra. Helena (Plantonista): Sofre com a pressão de classificar centenas de pacientes e a falta de saber para onde encaminhar os casos graves.
Sr. João (Paciente): Hipertenso, chega à UPA e espera horas sem saber que sua condição é grave.

### 2. Brainstorming e Ideação
Utilizamos a técnica de "Crazy 8" para gerar ideias. Descartamos a ideia de um "app para o paciente" (barreira tecnológica) e focamos em uma API robusta de integração que possa ser consumida por totens, tablets de enfermeiros ou sistemas legados do SUS.

### 3. Definição da Arquitetura (A Escolha pela Inovação)
Decidimos não fazer apenas um CRUD. Para resolver o problema de escala (milhares de acessos simultâneos), optamos por uma Arquitetura Orientada a Eventos (EDA).

Por que RabbitMQ? Para garantir que a triagem seja salva instantaneamente (<50ms), mesmo que o algoritmo de alocação leve mais tempo. Isso salva vidas em sistemas críticos.

---

## 🏗️ Arquitetura

### 📐 Visão Geral

```
┌─────────────────────────────────────────────────────┐
│                 CAMADA DE API (REST)                 │
│               Controllers + DTOs + Docs              │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────┴────────────────────────────────┐
│              CAMADA DE APLICAÇÃO                     │
│          Services + Producers + Consumers            │
└────────┬────────────────────────────┬────────────────┘
         │                            │
         ↓                            ↓
┌─────────────────────┐    ┌──────────────────────────┐
│  CAMADA DE DOMÍNIO  │    │  CAMADA DE MENSAGERIA    │
│  Entities + VOs +   │    │  RabbitMQ + Events       │
│  Strategies         │    │  (Event-Driven)          │
└──────────┬──────────┘    └───────────┬──────────────┘
           │                           │
           ↓                           ↓
┌──────────────────────┐    ┌─────────────────────────┐
│ CAMADA DE PERSIST.   │    │ CAMADA DE ALOCAÇÃO      │
│ Repositories + BD    │    │ Async Processing        │
└──────────────────────┘    └─────────────────────────┘
```

### 🔄 Fluxo Assíncrono com Idempotência e Cache (Event-Driven Architecture)

```
┌──────────┐    ① POST /api/triagem      ┌─────────────┐
│          │ ──────────────────────────→  │             │
│ Cliente  │                              │  Controller │
│          │  ⑧ GET /api/triagem/{id}     │             │
│          │ ←─────────────────────────── │             │
└──────────┘                              └──────┬──────┘
                                                 │
                                                 │ ② Salvar Triagem
                                                 ↓
                                         ┌───────────────┐
                                         │  PostgreSQL   │
                                         │  (PENDENTE)   │
                                         └───────┬───────┘
                                                 │
                                                 │ ③ Enviar Evento
                                                 ↓
                                         ┌───────────────┐
                                         │   RabbitMQ    │
                                         │   Exchange    │
                                         └───────┬───────┘
                                                 │
                                                 │ ④ Rotear
                                                 ↓
                                         ┌───────────────┐
                                         │ Fila:         │
                                         │ triagem.      │
                                         │ pendente      │
                                         └───────┬───────┘
                                                 │
                                                 │ ⑤ Consumir
                                                 ↓
                                         ┌───────────────┐
                                         │   Consumer    │
                                         │   (Async)     │
                                         └───────┬───────┘
                                                 │
                                      ⑥ Processar │
                                         Alocação│
                                                 ↓
                                ┌────────────────────────┐
                                │ - Buscar unidades      │
                                │ - Calcular distância   │
                                │ - Alocar mais próxima  │
                                └────────┬───────────────┘
                                         │
                                         │ ⑦ Atualizar
                                         ↓
                                ┌────────────────────────┐
                                │    PostgreSQL          │
                                │    (ALOCADA)           │
                                └────────────────────────┘
                                
1. **Ingestão:** Paciente envia dados via `POST /api/triagem`.
2. **Persistência Inicial:** Sistema salva no PostgreSQL com status `PENDENTE_ALOCACAO`.
3. **Evento:** Evento é disparado para o RabbitMQ.
4. **Consumo:** O `TriagemConsumer` recebe a mensagem.
5. **Verificação (Idempotência):** O Consumer checa no banco se a triagem já está `ALOCADA`. Se sim, ignora o reprocessamento.
6. **Busca Otimizada (Redis):** O sistema busca unidades compatíveis, preferencialmente via Cache.
7. **Alocação:** Calcula distância e atualiza o banco de dados para `ALOCADA`.
```

### ⏱️ Timeline de Processamento

```
T=0ms      │ Cliente envia POST /api/triagem
           │
T=50ms     │ ✅ Response imediato: status=PENDENTE_ALOCACAO
           │    Cliente recebe ID da triagem + mensagem explicativa
           │
           │ ⚡ Background: Evento enviado ao RabbitMQ
           │
T=100ms    │ Consumer consome evento da fila
           │
T=150ms    │ Busca unidades disponíveis (filtro por risco)
           │
T=200ms    │ Calcula distância geográfica
           │
T=250ms    │ Aloca unidade mais próxima + atualiza BD
           │
T=300ms    │ ✅ Processamento concluído: status=ALOCADA
           │
T=2000ms   │ Cliente faz GET /api/triagem/{id}
           │
T=2050ms   │ ✅ Response com unidadeDestino preenchido
```

**Ganho:** Cliente não espera 300ms. Response em 50ms!

---

## 🔄 Fluxo de Processamento

### Passo 1: Triagem (Síncrono)

**Request:**
```http
POST http://localhost:8081/api/triagem
Content-Type: application/json

{
  "nomePaciente": "João Silva",
  "cpfPaciente": "12345678901",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "frequenciaCardiaca": 110,
  "frequenciaRespiratoria": 19,
  "saturacaoOxigenio": 92,
  "temperatura": 39.5,
  "sintomas": "Febre alta"
}
```

**Response Imediato (< 100ms):**
```json
{
  "id": 1,
  "nomePaciente": "João Silva",
  "cpfPaciente": "12345678901",
  "risco": "LARANJA",
  "status": "PENDENTE_ALOCACAO",
  "dataHora": "2026-02-11T10:30:00",
  "mensagem": "Triagem registrada com sucesso. A alocação da unidade de saúde está sendo processada",
  "urlConsulta": "/api/triagem/1"
}
```

### Passo 2: Alocação (Assíncrono em Background)

**Logs do Servidor:**
```
[CONSUMER] Processando Alocação Inteligente. ID: 1 | Risco: LARANJA
Tipos de unidade adequados: [HOSPITAL]
Unidades disponíveis: 1
Unidade selecionada: HOSPITAL - Hospital Central H2
[CONSUMER] SUCESSO: Paciente João Silva encaminhado para HOSPITAL Hospital Central H2
```

### Passo 3: Consulta do Resultado

**Request:**
```http
GET http://localhost:8081/api/triagem/1
```

**Response (após processamento):**
```json
{
  "id": 1,
  "nomePaciente": "João Silva",
  "cpfPaciente": "12345678901",
  "risco": "LARANJA",
  "status": "ALOCADA",
  "dataHora": "2026-02-11T10:30:00",
  "mensagem": "Triagem processada com sucesso.",
  "urlConsulta": "/api/triagem/1",
  "unidadeDestino": {
    "id": 1,
    "nome": "Hospital Central H2",
    "tipo": "HOSPITAL",
    "latitude": -23.56,
    "longitude": -46.65,
    "capacidadeTotal": 10,
    "ocupacaoAtual": 4
  }
}
```

---

## 📋 Pré-requisitos

- ☕ **Java 21** ou superior
- 🐋 **Docker** e **Docker Compose**
- 🔧 **Maven 3.8+**
- 💻 **Git**

---

## 🚀 Como Executar

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/seu-usuario/sus-triage-api.git
cd sus-triage-api
```

### 2️⃣ Inicie os Serviços (Docker Compose)

```bash
# Build da imagem e subida dos containers
# (recomendado quando houver alterações no código)
docker compose up --build -d
```

**Serviços iniciados:**
- 🐰 RabbitMQ (porta 5672, Management UI: 15672)
- 🐘 PostgreSQL (porta 5432)
- 🔴 Redis (porta 6379)

### 3️⃣ Logs da Aplicação (Docker)

```bash
# Logs da aplicação pelo Docker Compose
docker compose logs -f sus-triage-api
```

```bash
# Logs direto no container (ajuste o nome se necessário)
docker logs -f sus_api
```

### 4️⃣ Compile e Execute a Aplicação

**Opção A: Com Maven**
```bash
mvn clean package -DskipTests
java -jar target/sus-triage-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev --server.port=8081
```

**Opção B: Desenvolvimento (IDE)**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 5️⃣ Verifique a Aplicação

```bash
# Health Check
curl http://localhost:8081/actuator/health

# Swagger UI
open http://localhost:8081/swagger-ui/index.html
```

---

## 📡 Endpoints da API

### 🏥 Triagem de Pacientes

#### **POST** `/api/triagem` - Criar Triagem
Registra triagem de paciente e inicia alocação assíncrona.

**Request:**
```json
{
  "nomePaciente": "Maria Santos",
  "cpfPaciente": "98765432100",
  "latitude": -23.5505,
  "longitude": -46.6333,
  "frequenciaCardiaca": 85,
  "frequenciaRespiratoria": 18,
  "saturacaoOxigenio": 96,
  "temperatura": 37.0,
  "sintomas": "Dor no peito"
}
```

**Response:** `200 OK`
```json
{
  "id": 2,
  "nomePaciente": "Maria Santos",
  "cpfPaciente": "98765432100",
  "risco": "AMARELO",
  "status": "PENDENTE_ALOCACAO",
  "dataHora": "2026-02-11T10:30:00",
  "mensagem": "Triagem registrada com sucesso. A alocação da unidade de saúde está sendo processada",
  "urlConsulta": "/api/triagem/2"
}
```

#### **GET** `/api/triagem/{id}` - Consultar Triagem
Consulta resultado da triagem (incluindo unidade alocada).

**Response:** `200 OK`
```json
{
  "id": 2,
  "nomePaciente": "Maria Santos",
  "cpfPaciente": "98765432100",
  "risco": "AMARELO",
  "status": "ALOCADA",
  "dataHora": "2026-02-11T10:30:00",
  "mensagem": "Triagem processada com sucesso.",
  "urlConsulta": "/api/triagem/2",
  "unidadeDestino": {
    "id": 2,
    "nome": "UPA Zona Sul",
    "tipo": "UPA",
    "latitude": -23.55,
    "longitude": -46.63,
    "capacidadeTotal": 15,
    "ocupacaoAtual": 7
  }
}
```

### 👤 Gestão de Pacientes

#### **POST** `/api/pacientes` - Cadastrar Paciente
#### **GET** `/api/pacientes/{id}` - Buscar por ID
#### **GET** `/api/pacientes/cpf/{cpf}` - Buscar por CPF
#### **PUT** `/api/pacientes/{id}` - Atualizar Paciente

---

## 🎬 Demonstração

### Roteiro de Demo (3 minutos)

#### 1. Contexto (30s)
> "No SUS real, triagem e alocação são processos separados. Nossa solução replica isso com arquitetura assíncrona, garantindo performance e escalabilidade."

#### 2. Demo ao Vivo (90s)

**a) Mostrar POST:**
```bash
curl -X POST http://localhost:8081/api/triagem \
  -H "Content-Type: application/json" \
  -d '{"nomePaciente":"Demo User","cpfPaciente":"11111111111",...}'
```
→ Response instantâneo com `status: PENDENTE_ALOCACAO`

**b) Mostrar Logs do Consumer:**
```
[CONSUMER] Processando Alocação Inteligente. ID: X | Risco: LARANJA
[CONSUMER] SUCESSO: Paciente Demo User encaminhado para HOSPITAL
```

**c) Mostrar GET:**
```bash
curl http://localhost:8081/api/triagem/X
```
→ Response com `status: ALOCADA` e mensagem de conclusão

#### 3. Destacar Benefícios (60s)
- ✅ Response instantâneo mesmo com processamento complexo
- ✅ Se alocação falhar, triagem está salva (resiliência)
- ✅ Podemos priorizar casos VERMELHOS em fila separada
- ✅ Arquitetura escalável e moderna (Event-Driven)

---

## 📊 Monitoramento

### RabbitMQ Management UI
```
URL: http://localhost:15672
Login: guest / guest
```

**Verificações:**
- Filas: `triagem.pendente`, `triagem.espera.critica`
- Consumers ativos
- Taxa de mensagens processadas

### ✅ DLQ (Dead Letter Queue)

**Quando a DLQ é acionada:**
- Se o consumer lançar exceção e o retry estourar o limite (3 tentativas), a mensagem é rejeitada sem requeue e vai para `triagem.dlq`.
- A fila `triagem.espera.critica` não é DLQ; ela é usada manualmente quando não há vagas.

**Como simular a DLQ (mensagem inválida):**
```bash
# Descubra o container do RabbitMQ
# (procure pelo serviço do RabbitMQ)
docker compose ps
```

```bash
# Publique uma mensagem inválida direto na fila
# Substitua NOME_CONTAINER_RABBIT pelo container correto
docker exec -it NOME_CONTAINER_RABBIT rabbitmqadmin publish routing_key=triagem.pendente payload="isso-nao-e-json"
```

```bash
# Verifique os logs da aplicação (3 tentativas + erro)
docker compose logs -f sus-triage-api
```

```bash
# Confirme a mensagem na DLQ
docker exec -it NOME_CONTAINER_RABBIT rabbitmqadmin list queues name messages
```

### 🚨 Fila `triagem.espera.critica` (Sem Vagas)

**Quando é utilizada:**
- Se não houver unidades disponíveis para o risco calculado, o evento é enviado para `triagem.espera.critica`.
- Isso representa um caso crítico de negócio (não é erro técnico e não usa a DLQ).

**Exemplo de cenário:**
- Um paciente classificado como `VERMELHO` chega, mas todas as unidades do tipo `HOSPITAL` estão com capacidade máxima.
- O consumer não consegue alocar e envia o evento para `triagem.espera.critica` para acompanhamento.

### 🔄 Verificando Múltiplos Consumers

O Docker Compose já está configurado para **produção** com **5 a 20 consumers simultâneos** por instância.

**Ver Consumers Ativos:**
```bash
# Via RabbitMQ Management UI (Recomendado)
# Acesse: http://localhost:15672 (guest/guest)
# Vá em: Queues → triagem.pendente → Consumers

# Via Linha de Comando
docker exec -it sus_rabbitmq rabbitmqadmin list queues name consumers
```

**Exemplo de Output (1 instância em prod):**
```
+------------------+-----------+
| name             | consumers |
+------------------+-----------+
| triagem.pendente | 5         |
| triagem.dlq      | 0         |
+------------------+-----------+
```
→ **5 consumers** = configuração mínima de prod

**Escalar Horizontalmente (Opcional):**
```bash
# Escalar para 3 instâncias (15-60 consumers no total)
docker compose up --build --scale sus-triage-api=3 -d

# Verificar consumers (deve mostrar 15-60)
docker exec -it sus_rabbitmq rabbitmqadmin list queues name consumers
```

**Monitorar em Tempo Real:**
```bash
# Logs da aplicação
docker compose logs -f sus-triage-api

# Estatísticas das filas
docker exec -it sus_rabbitmq rabbitmqadmin list queues name messages consumers
```

### Spring Boot Actuator
```
URL: http://localhost:8081/actuator
```

**Endpoints:**
- `/actuator/health` - Status da aplicação
- `/actuator/metrics` - Métricas de desempenho

### Swagger UI
```
URL: http://localhost:8081/swagger-ui/index.html
```

Documentação interativa completa da API.

---

## 🧪 Testando Localmente

### Testar Fluxo Completo (PowerShell)

```powershell
# 1. Criar Triagem
$body = @{
    nomePaciente = "Teste Final"
    cpfPaciente = "12312312312"
    latitude = -23.5505
    longitude = -46.6333
    frequenciaCardiaca = 110
    frequenciaRespiratoria = 19
    saturacaoOxigenio = 92
    temperatura = 39.5
    sintomas = "Febre alta"
} | ConvertTo-Json

$response = Invoke-WebRequest -Uri "http://localhost:8081/api/triagem" `
    -Method POST `
    -Body $body `
    -ContentType "application/json"

$triagem = $response.Content | ConvertFrom-Json
Write-Host "Triagem criada: ID=$($triagem.id), Status=$($triagem.status)"

# 2. Aguardar processamento
Start-Sleep -Seconds 2

# 3. Consultar Resultado
$result = Invoke-WebRequest -Uri "http://localhost:8081/api/triagem/$($triagem.id)"
$triagemFinal = $result.Content | ConvertFrom-Json
Write-Host "Status final: $($triagemFinal.status)"
Write-Host "Unidade: $($triagemFinal.unidadeDestino.nome)"
```

---

## 📈 Níveis de Risco (Protocolo de Manchester)

| Cor | Risco | Tempo Máximo | Tipo de Unidade | Prioridade |
|-----|-------|--------------|-----------------|------------|
| 🔴 **VERMELHO** | Emergência | Imediato | HOSPITAL | 1 |
| 🟠 **LARANJA** | Muito Urgente | 10 min | HOSPITAL | 2 |
| 🟡 **AMARELO** | Urgente | 60 min | UPA, HOSPITAL | 3 |
| 🟢 **VERDE** | Pouco Urgente | 120 min | UBS, UPA | 4 |
| 🔵 **AZUL** | Não Urgente | 240 min | UBS | 5 |

---

## 🎯 Padrões e Boas Práticas

### Padrões de Projeto Implementados

- ✅ **Strategy Pattern** - Algoritmo de classificação de risco
- ✅ **Repository Pattern** - Abstração de acesso a dados
- ✅ **DTO Pattern** - Transferência de dados entre camadas
- ✅ **Producer-Consumer** - Mensageria assíncrona
- ✅ **Event-Driven Architecture** - Desacoplamento via eventos
- ✅ **Transactional Outbox** - Consistência eventual

### Princípios SOLID

- ✅ **SRP** - Classes com responsabilidade única
- ✅ **OCP** - Aberto para extensão, fechado para modificação
- ✅ **LSP** - Substituição de implementações (Strategy)
- ✅ **ISP** - Interfaces segregadas por função
- ✅ **DIP** - Dependência de abstrações (Repositories)

---

## 🔐 Segurança e Qualidade

### Validações Implementadas

- ✅ Bean Validation nos DTOs
- ✅ Validação de CPF
- ✅ Validação de sinais vitais (ranges aceitáveis)
- ✅ Tratamento centralizado de exceções

### Testes

- ✅ Testes unitários de serviços
- ✅ Testes de controllers (MockMvc)
- ✅ Testes de repositórios (DataJpaTest)

---

## 📚 Referências Técnicas

- [Spring AMQP Documentation](https://docs.spring.io/spring-amqp/reference/)
- [RabbitMQ Tutorials](https://www.rabbitmq.com/getstarted.html)
- [Microservices Patterns - Chris Richardson](https://microservices.io/patterns/)
- [Protocolo de Manchester](https://www.gpicnorthwales.org.uk/manchester-triage-system/)
- [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/)

---

## 🚀 Aprendizados e Próximos Passos

### O que aprendemos:

- **Complexidade Distribuída:** Lidar com consistência eventual (o delay entre a triagem e a alocação) exigiu tratamento robusto de erros e Dead Letter Queues.
- **Importância do Protocolo:** Traduzir regras médicas (Manchester) para Strategy Pattern em Java mostrou como o código limpo pode salvar vidas ao evitar bugs lógicos.

### Roadmap (Futuro):

- **Integração com Google Maps API:** Para considerar o trânsito em tempo real no cálculo de deslocamento, não apenas a distância linear (Haversine).
- **Machine Learning:** Implementar um modelo preditivo que aprenda com o histórico de triagens para prever lotação antes que ela aconteça.
- **App do Paciente:** Permitir que o paciente faça uma pré-triagem informativa antes de sair de casa.

---

## 👥 Equipe

Desenvolvido com dedicação por alunos da **Pós-Graduação em Arquitetura e Desenvolvimento Java — FIAP**.

| Nome | RM | GitHub                                                                                 |
| --- | --- |----------------------------------------------------------------------------------------|
| **Leonardo Felipe Ventura Ferreira** | 363339 | [BL7Ki](https://www.google.com/search?q=https://github.com/BL7Ki)                      |
| **Wagner de Lima Braga Silva** | 364223 | [wagner](https://github.com/wagnersistemalima)                                         |
| **Everton Cristiano de Souza Teixeira** | 362065 | [evertonteixeira](https://www.google.com/search?q=https://github.com/evertoncsteixeira) |

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte do Tech Challenge 5.

---

## 🚀 Conclusão

Esta solução demonstra:

- ✅ **Arquitetura moderna** com processamento assíncrono
- ✅ **Escalabilidade** para ambientes de produção
- ✅ **Resiliência** a falhas e alta carga
- ✅ **Aderência** ao contexto real do SUS
- ✅ **Qualidade técnica** e boas práticas

**Diferencial competitivo:** Sistema pronto para produção que reflete processos reais do SUS com arquitetura Event-Driven de alto desempenho.

---

⭐ **Desenvolvido com dedicação para o Tech Challenge 5 - FIAP 2026**
