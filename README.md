```markdown
# 🏥 SUS Triage API - Sistema Inteligente de Triagem e Alocação

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-orange)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![Architecture](https://img.shields.io/badge/Architecture-Event--Driven-purple)

> **Tech Challenge 5 - Hackathon FIAP 2026** > Pós-Graduação em Arquitetura e Desenvolvimento Java

---

## 📑 Índice
1. [Resumo Executivo](#-resumo-executivo)
2. [O Problema e Impacto](#-o-problema-e-impacto)
3. [Processo de Desenvolvimento](#-processo-de-desenvolvimento)
4. [A Solução Inovadora](#-a-solução-inovadora)
5. [Arquitetura Técnica](#%EF%B8%8F-arquitetura-técnica)
6. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
7. [Como Executar (MVP)](#-como-executar)
8. [Endpoints da API](#-endpoints-da-api)
9. [Monitoramento e Observabilidade](#-monitoramento-e-observabilidade)
10. [Aprendizados e Futuro](#-aprendizados-e-futuro)
11. [Equipe](#-equipe)

---

## 📋 Resumo Executivo

O **SUS Triage API** é uma solução de backend de alta performance projetada para modernizar a porta de entrada das unidades de saúde pública. Utilizando o **Protocolo de Manchester** automatizado e algoritmos de geolocalização, o sistema elimina a subjetividade na triagem e direciona pacientes para unidades com **capacidade real de atendimento** em milissegundos.

A solução resolve o gargalo da superlotação através de uma arquitetura assíncrona (**Event-Driven**) resiliente, garantindo que o sistema nunca saia do ar, mesmo em situações de catástrofe ou alta demanda, salvando a "hora de ouro" do atendimento médico.

---

## 🎯 O Problema e Impacto

### O Desafio: O "Gargalo da Decisão"
No cenário atual do SUS, a triagem manual e a falta de integração entre unidades geram três problemas críticos:
1.  **Subjetividade e Erro Humano:** A classificação de risco depende do cansaço e da interpretação momentânea do profissional.
2.  **Ineficiência Logística:** Pacientes graves são frequentemente levados a unidades (UPAs/Hospitais) que já estão lotadas ou sem recursos específicos.
3.  **Falta de Visibilidade:** Não há uma visão em tempo real da demanda versus capacidade da rede, impedindo ações preventivas de gestão.

### 💡 O Impacto da Solução (Valor de Negócio)
Nossa API ataca diretamente a **mortalidade evitável** e a **eficiência operacional**:

* **✅ Para o Paciente:** Redução drástica no tempo de espera e garantia de ser atendido na unidade correta (geolocalizada) e com vaga.
* **✅ Para o Profissional:** Ferramenta de apoio à decisão que automatiza a burocracia, permitindo foco no atendimento clínico humanizado.
* **✅ Para a Gestão Pública:** Dados auditáveis e capacidade de prever surtos de demanda baseados nos logs de triagem em tempo real.
* **✅ Resiliência:** O sistema continua operando e enfileirando triagens mesmo se o serviço de alocação estiver sobrecarregado.

---

## 🧠 Processo de Desenvolvimento

Para conceber esta solução, a equipe seguiu um fluxo estruturado de **Design Thinking**:

### 1. Mapeamento e Empatia (Personas)
Criamos personas para entender a dor real dos usuários:
* **Dra. Helena (Plantonista):** Sofre com a pressão de classificar centenas de pacientes e a angústia de não saber para onde encaminhar os casos graves.
* **Sr. João (Paciente Cardíaco):** Chega à UPA com dor no peito e espera horas na fila comum, sem saber que sua condição é crítica.

### 2. Brainstorming e Ideação
Utilizamos a técnica de "Crazy 8" para gerar ideias.
* *Ideia descartada:* App para o paciente (barreira tecnológica/digital para idosos).
* *Ideia selecionada:* **API de Infraestrutura Robusta**. Uma solução "invisível" que integra totens de autoatendimento, tablets de enfermeiros e sistemas legados do SUS.

### 3. Definição da Arquitetura (A Escolha pela Inovação)
Decidimos não fazer apenas um CRUD. Para resolver o problema de escala (milhares de acessos simultâneos em uma pandemia, por exemplo), optamos por uma **Arquitetura Orientada a Eventos (EDA)** com RabbitMQ.

---

## 🚀 A Solução Inovadora

### 1. Classificação Inteligente (Protocolo de Manchester)
Implementamos o **Strategy Pattern** para tornar o algoritmo de triagem extensível. Atualmente, o sistema avalia 5 sinais vitais e classifica em:

| Cor | Risco | Tempo Máximo | Tipo de Unidade | Prioridade |
| :--- | :--- | :--- | :--- | :--- |
| 🔴 | **VERMELHO** (Emergência) | Imediato | HOSPITAL | 1 |
| 🟠 | **LARANJA** (Muito Urgente) | 10 min | HOSPITAL | 2 |
| 🟡 | **AMARELO** (Urgente) | 60 min | UPA, HOSPITAL | 3 |
| 🟢 | **VERDE** (Pouco Urgente) | 120 min | UBS, UPA | 4 |
| 🔵 | **AZUL** (Não Urgente) | 240 min | UBS | 5 |

### 2. Alocação Geolocalizada (Haversine)
O sistema calcula a distância entre as coordenadas do paciente e todas as unidades de saúde disponíveis. O algoritmo seleciona a unidade que atende a dois critérios:
1.  **Compatibilidade:** A unidade aceita o nível de risco do paciente?
2.  **Disponibilidade:** A unidade tem vaga real?
3.  **Proximidade:** Qual é a mais próxima (cálculo geodésico)?

---

## 🏗️ Arquitetura Técnica

### Diferencial: Performance Assíncrona
A separação entre a **Triagem (Síncrona)** e a **Alocação (Assíncrona)** é o coração da nossa inovação.

```mermaid
graph TD
    User((Paciente/Totem)) -->|POST /triagem| API[API Gateway / Controller]
    API -->|Salva PENDENTE| DB[(PostgreSQL)]
    API -->|Publish Event| MQ{RabbitMQ Exchange}
    API -.->|200 OK (Imediato)| User
    
    MQ -->|Route| Q1[Fila: triagem.pendente]
    
    subgraph "Background Workers"
        Worker[Consumer Service]
        Worker -->|Consume| Q1
        Worker -->|Strategy| Risco[Calc. Risco Manchester]
        Worker -->|Haversine| Geo[Calc. Distância]
        Worker -->|Update ALOCADO| DB
    end
    
    MQ -->|Erro/Retry Exceeded| DLQ[Dead Letter Queue]

```

### Fluxo de Processamento (Timeline)

* **T=0ms:** Cliente envia `POST /api/triagem`.
* **T=50ms:** ✅ **Response Imediato:** Cliente recebe ID. O sistema não trava esperando alocação.
* **T=100ms:** RabbitMQ entrega a mensagem ao Consumer.
* **T=200ms:** Worker calcula risco e encontra o hospital mais próximo.
* **T=300ms:** Banco de dados atualizado com a unidade de destino.

---

## 🛠 Tecnologias Utilizadas

* **Linguagem:** Java 21 (LTS)
* **Framework:** Spring Boot 4.0.2 (Web, Data JPA, AMQP, Validation, Actuator)
* **Mensageria:** RabbitMQ 3.13 (Exchanges, Queues, DLQ)
* **Banco de Dados:** H2 (Dev/Test) / PostgreSQL (Prod)
* **Documentação:** Swagger/OpenAPI 3
* **Containerização:** Docker & Docker Compose

---

## 🚀 Como Executar

### Pré-requisitos

* Docker e Docker Compose instalados.
* Portas 8081, 5672, 15672 e 5432 livres.

### Passo a Passo

1. **Clone o Repositório:**
```bash
git clone [https://github.com/seu-usuario/sus-triage-api.git](https://github.com/seu-usuario/sus-triage-api.git)
cd sus-triage-api

```


2. **Inicie a Infraestrutura (Docker):**
```bash
docker compose up --build -d

```


*Isso subirá o RabbitMQ, PostgreSQL e a Aplicação.*
3. **Acesse a Documentação (Swagger):**
* Abra: [http://localhost:8081/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8081/swagger-ui/index.html)


4. **Acompanhe o RabbitMQ:**
* Abra: [http://localhost:15672](https://www.google.com/search?q=http://localhost:15672) (Login: `guest` / `guest`)



---

## 📡 Endpoints da API

### 1. Criar Triagem (Cenário Real)

Envie uma triagem com sinais vitais alterados para ver o algoritmo funcionar.

`POST /api/triagem`

```json
{
  "nomePaciente": "Marcio Alencar",
  "cpfPaciente": "52998224725",
  "latitude": -23.5600,
  "longitude": -46.6500,
  "sintomas": "Dor aguda no peito e dificuldade respiratória",
  "pressaoSistolica": 190,
  "pressaoDiastolica": 110,
  "temperatura": 38.5,
  "batimentos": 120,
  "saturacao": 85
}

```

### 2. Consultar Resultado

Use o ID retornado para ver para onde o paciente foi alocado.

`GET /api/triagem/{id}`

```json
{
  "id": 1,
  "risco": "VERMELHO",
  "status": "ALOCADO",
  "unidadeDestino": {
    "nome": "Hospital das Clínicas",
    "distanciaKm": 1.2
  }
}

```

---

## 📊 Monitoramento e Observabilidade

O sistema implementa padrões de resiliência para produção:

1. **Dead Letter Queue (DLQ):** Se uma triagem falhar 3 vezes (ex: dados corrompidos), ela não é perdida. Ela é movida para a fila `triagem.dlq` para análise manual, garantindo que nenhum paciente seja "esquecido" pelo sistema.
2. **Escalabilidade Horizontal:** O Docker Compose permite escalar os workers (`docker compose up --scale sus-triage-api=3`) para processar milhares de triagens simultaneamente.
3. **Logs Estruturados:** Cada etapa (Recebimento, Cálculo, Alocação) gera logs claros para auditoria.

---

## 📚 Aprendizados e Futuro

### O que aprendemos

* **Complexidade Distribuída:** Lidar com consistência eventual (o delay entre a triagem e a alocação) exigiu tratamento robusto de erros e filas de espera críticas.
* **Importância do Protocolo:** Traduzir regras médicas (Manchester) para código (`Strategy Pattern`) mostrou como a arquitetura limpa facilita a manutenção e evolução das regras de negócio sem quebrar o sistema.

### Próximos Passos (Roadmap)

1. **Integração com API de Trânsito:** Evoluir o cálculo de Haversine para considerar o tempo real de trânsito (Google Maps API).
2. **Machine Learning:** Implementar um modelo preditivo que "aprende" com o histórico de triagens para prever lotação em dias de surto.
3. **App do Paciente:** Permitir pré-triagem informativa antes do deslocamento.

---

## 👥 Equipe

Desenvolvido com dedicação para o Tech Challenge 5 - FIAP 2026.

* **Membros:** 
Leonardo Felipe Ventura Ferreira - RM363339
Wagner de Lima Braga Silva - RM364223
Everton Cristiano de Souza Teixeira - RM362065

* **Licença:** Educational

---

```

```