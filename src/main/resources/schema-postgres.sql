DROP TABLE IF EXISTS triagens CASCADE;
DROP TABLE IF EXISTS pacientes CASCADE;
DROP TABLE IF EXISTS unidades_saude CASCADE;

CREATE TABLE pacientes (
                           id BIGSERIAL PRIMARY KEY,
                           nome VARCHAR(255) NOT NULL,
                           cpf VARCHAR(11) NOT NULL UNIQUE,
                           latitude DOUBLE PRECISION,
                           longitude DOUBLE PRECISION
);

CREATE TABLE unidades_saude (
                                id BIGSERIAL PRIMARY KEY,
                                nome VARCHAR(255) NOT NULL UNIQUE,
                                tipo VARCHAR(50) NOT NULL,
                                latitude DOUBLE PRECISION NOT NULL,
                                longitude DOUBLE PRECISION NOT NULL,
                                capacidade_total INTEGER NOT NULL,
                                ocupacao_atual INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE triagens (
                          id BIGSERIAL PRIMARY KEY,
                          paciente_id BIGINT NOT NULL REFERENCES pacientes(id),
                          unidade_saude_id BIGINT REFERENCES unidades_saude(id),
                          sintomas TEXT,
                          pressao_arterial_sistolica INTEGER,
                          pressao_arterial_diastolica INTEGER,
                          temperatura DOUBLE PRECISION,
                          batimentos_por_minuto INTEGER,
                          saturacao_oxigenio INTEGER,
                          risco VARCHAR(20) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          data_hora TIMESTAMP NOT NULL
);