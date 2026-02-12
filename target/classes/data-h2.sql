INSERT INTO unidades_saude (nome, tipo, latitude, longitude, capacidade_total, ocupacao_atual)
VALUES
    ('Hospital Central H2', 'HOSPITAL', -23.5600, -46.6500, 10, 2),
    ('UPA H2 Teste', 'UPA', -23.5900, -46.6100, 8, 0),
    ('UBS H2 Teste', 'UBS', -23.5489, -46.6388, 50, 0);

INSERT INTO pacientes (nome, cpf, latitude, longitude)
VALUES
    ('Paciente H2 Um', '11122233344', -23.5610, -46.6510),
    ('Paciente H2 Dois', '55566677788', -23.5920, -46.6110);