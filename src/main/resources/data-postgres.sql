--- INSERÇÃO DE UNIDADES DE SAÚDE ---
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES
    ('Hospital Metropolitano Central', 'HOSPITAL', -23.5600, -46.6500, 10, 2),
    ('Hospital do Coração (LOTADO)', 'HOSPITAL', -23.5550, -46.6600, 3, 3),
    ('UPA 24h Ipiranga', 'UPA', -23.5900, -46.6100, 8, 4),
    ('UBS Posto da Sé', 'UBS', -23.5489, -46.6388, 50, 5),
    ('UBS Jardim das Flores', 'UBS', -23.6500, -46.7000, 30, 2)
    ON CONFLICT (nome) DO NOTHING;


--- INSERÇÃO DE PACIENTES ---
INSERT INTO pacientes (nome, cpf, latitude, longitude)
VALUES
    ('João Silva', '12345678901', -23.5610, -46.6510),
    ('Maria Oliveira', '23456789012', -23.5920, -46.6110),
    ('Ricardo Santos', '34567890123', -23.5490, -46.6390),
    ('Ana Souza', '45678901234', -23.6510, -46.7010),
    ('Carlos Lima', '56789012345', -23.5555, -46.6610),
    ('Juliana Costa', '67890123456', -23.5605, -46.6505),
    ('Fernando Rocha', '78901234567', -23.5001, -46.6001),
    ('Beatriz Alves', '89012345678', -23.5700, -46.6400),
    ('Paulo Mendes', '90123456789', -23.5800, -46.200),
    ('Luciana Dias', '01234567890', -23.5400, -46.6300)
    ON CONFLICT (cpf) DO NOTHING;