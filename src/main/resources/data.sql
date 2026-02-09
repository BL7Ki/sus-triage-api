--- INSERÇÃO DE UNIDADES DE SAÚDE ---

-- 1. Hospital Disponível
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES ('Hospital Metropolitano Central', 'HOSPITAL', -23.5600, -46.6500, 10, 2);

-- 2. Hospital LOTADO (Para o teste da Fila Crítica)
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES ('Hospital do Coração (LOTADO)', 'HOSPITAL', -23.5550, -46.6600, 3, 3);

-- 3. UPA (Média Complexidade)
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES ('UPA 24h Ipiranga', 'UPA', -23.5900, -46.6100, 8, 4);

-- 4. UBS (Baixa Complexidade)
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES ('UBS Posto da Sé', 'UBS', -23.5489, -46.6388, 50, 5);

-- 5. UBS (Outra localização)
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES ('UBS Jardim das Flores', 'UBS', -23.6500, -46.7000, 30, 2);


--- INSERÇÃO DE PACIENTES ---

INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('João Silva', '12345678901', -23.5610, -46.6510);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Maria Oliveira', '23456789012', -23.5920, -46.6110);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Ricardo Santos', '34567890123', -23.5490, -46.6390);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Ana Souza', '45678901234', -23.6510, -46.7010);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Carlos Lima', '56789012345', -23.5555, -46.6610);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Juliana Costa', '67890123456', -23.5605, -46.6505);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Fernando Rocha', '78901234567', -23.5001, -46.6001);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Beatriz Alves', '89012345678', -23.5700, -46.6400);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Paulo Mendes', '90123456789', -23.5800, -46.6200);
INSERT INTO pacientes (nome, cpf, latitude, longitude) VALUES ('Luciana Dias', '01234567890', -23.5400, -46.6300);