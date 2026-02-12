--- INSERÇÃO DE UNIDADES DE SAÚDE (H2)
INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES
    ('Hospital Central H2', 'HOSPITAL', -23.5600, -46.6500, 10, 2),
    ('Hospital das Clínicas', 'HOSPITAL', -23.5575, -46.6717, 100, 10),
    ('Hospital Albert Einstein', 'HOSPITAL', -23.5999, -46.7155, 100, 20),
    ('UPA H2 Teste', 'UPA', -23.5900, -46.6100, 8, 0),
    ('UPA Vergueiro', 'UPA', -23.5684, -46.6358, 50, 5),
    ('UBS H2 Teste', 'UBS', -23.5489, -46.6388, 50, 0),
    ('UBS Santa Cecília', 'UBS', -23.5395, -46.6493, 30, 2);

--- INSERÇÃO DE PACIENTES (H2)
INSERT INTO pacientes (nome, cpf, latitude, longitude)
VALUES
    ('Paciente H2 Um', '11122233344', -23.5610, -46.6510),
    ('Paciente H2 Dois', '55566677788', -23.5920, -46.6110);