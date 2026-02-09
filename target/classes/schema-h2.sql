DROP TABLE IF EXISTS triagens;
DROP TABLE IF EXISTS pacientes;
DROP TABLE IF EXISTS unidades_saude;

CREATE TABLE pacientes (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(255) NOT NULL,
                           cpf VARCHAR(11) NOT NULL UNIQUE,
                           latitude DOUBLE,
                           longitude DOUBLE
);

CREATE TABLE unidades_saude (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                nome VARCHAR(255) NOT NULL UNIQUE,
                                tipo VARCHAR(50) NOT NULL,
                                latitude DOUBLE NOT NULL,
                                longitude DOUBLE NOT NULL,
                                capacidade_total INTEGER NOT NULL,
                                ocupacao_atual INTEGER DEFAULT 0
);

CREATE TABLE triagens (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          paciente_id BIGINT NOT NULL,
                          unidade_saude_id BIGINT,
                          sintomas VARCHAR(1000),
                          pressao_arterial_sistolica INTEGER,
                          pressao_arterial_diastolica INTEGER,
                          temperatura DOUBLE,
                          batimentos_por_minuto INTEGER,
                          saturacao_oxigenio INTEGER,
                          risco VARCHAR(20) NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          data_hora TIMESTAMP NOT NULL,
                          FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
                          FOREIGN KEY (unidade_saude_id) REFERENCES unidades_saude(id)
);