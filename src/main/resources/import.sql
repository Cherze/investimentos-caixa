

INSERT INTO produtos_investimento (nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
VALUES
('CDB Caixa 2026', 'CDB', 0.115, 'Baixo', 6, 1000.00, 'Conservador', true),
('LCI Caixa', 'LCI', 0.095, 'Baixo', 12, 5000.00, 'Conservador', true),
('Fundo Ações', 'FundoA', 0.185, 'Alto', 24, 10000.00, 'Agressivo', true),
('Tesouro Direto', 'Tesouro', 0.065, 'Baixo', 36, 100.00, 'Conservador', true),
('Fundo Multimercado', 'FundoM', 0.125, 'Moderado', 12, 2000.00, 'Moderado', true),
('LCA Agro Brasil', 'LCA', 0.085, 'Baixo', 18, 3000.00, 'Moderado', true);

INSERT INTO historico_investimentos (cliente_id, tipo, valor, rentabilidade, data)
VALUES
(1, 'CDB', 5000.00, 0.12, '2025-01-15 00:00:00.000'),
(1, 'FundoM', 3000.00, 0.125, '2025-03-10 00:00:00.000'),
(2, 'LCA', 7000.00, 0.085, '2025-03-11 00:00:00.000'),
(2, 'FundoA', 8000.00, 0.185, '2025-11-12 00:00:00.000'),
(3, 'LCI', 7000.00, 0.095, '2025-06-20 00:00:00.000'),
(4, 'Tesouro', 2000.00, 0.065, '2025-02-01 00:00:00.000'),
(4, 'LCI', 10000.00, 0.095, '2025-06-20 00:00:00.000'),
(5, 'FundoA', 50000.00, 0.185, '2025-11-15 00:00:00.000'),
(5, 'FundoA', 40000.00, 0.185, '2025-11-15 00:00:00.000'),
(5, 'FundoA', 15000.00, 0.185, '2025-11-15 00:00:00.000');

-- TESTE NO H2
--INSERT INTO produtos_investimento (nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES
--('CDB Caixa 2026', 'CDB', 0.115, 'Baixo', 6, 1000.00, 'Conservador', true),
--('LCI Caixa', 'LCI', 0.095, 'Baixo', 12, 5000.00, 'Conservador', true),
--('Fundo Ações', 'FundoA', 0.185, 'Alto', 24, 10000.00, 'Agressivo', true),
--('Tesouro Direto', 'Tesouro', 0.065, 'Baixo', 36, 100.00, 'Conservador', true),
--('Fundo Multimercado', 'FundoM', 0.125, 'Moderado', 12, 2000.00, 'Moderado', true),
--('LCA Agro Brasil', 'LCA', 0.085, 'Baixo', 18, 3000.00, 'Moderado', true);

--INSERT INTO historico_investimentos (cliente_id, tipo, valor, rentabilidade, data)
--VALUES
--(1, 'CDB', 5000.00, 0.12, '2025-01-15'),
--(1, 'FundoM', 3000.00, 0.125, '2025-03-10'),
--(2, 'LCA', 7000.00, 0.085, '2025-03-11'),
--(2, 'FundoA', 8000.00, 0.185, '2025-11-12'),
--(3, 'LCI', 7000.00, 0.095, '2025-06-20'),
--(4, 'Tesouro', 2000.00, 0.065, '2025-02-01'),
--(4, 'LCI', 10000.00, 0.095, '2025-06-20'),
--(5, 'FundoA', 50000.00, 0.185, '2025-11-15'),
--(5, 'FundoA', 40000.00, 0.185, '2025-11-15'),
--(5, 'FundoA', 15000.00, 0.185, '2025-11-15');