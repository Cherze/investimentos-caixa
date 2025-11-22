
-- SQLite usa 1 para true e 0 para false
--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (1, 'CDB Caixa 2026', 'CDB', 0.115, 'Baixo', 6, 1000.00, 'Conservador', 1);

--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (2, 'LCI Caixa', 'LCI', 0.095, 'Baixo', 12, 5000.00, 'Conservador', 1);

--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (3, 'Fundo Ações Growth', 'Fundo', 0.185, 'Alto', 24, 10000.00, 'Agressivo', 1);

--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (4, 'Tesouro Direto IPCA+', 'Tesouro', 0.065, 'Baixo', 36, 100.00, 'Conservador', 1);

--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (5, 'Fundo Multimercado Balanced', 'Fundo', 0.125, 'Moderado', 12, 2000.00, 'Moderado', 1);

--INSERT INTO produtos_investimento (id, nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
--VALUES (6, 'LCA Agro Brasil', 'LCA', 0.085, 'Baixo', 18, 3000.00, 'Moderado', 1);

-- TESTE NO H2
INSERT INTO produtos_investimento (nome, tipo, rentabilidade, risco, prazo_minimo_meses, valor_minimo, perfil_recomendado, ativo)
VALUES
('CDB Caixa 2026', 'CDB', 0.115, 'Baixo', 6, 1000.00, 'Conservador', true),
('LCI Caixa', 'LCI', 0.095, 'Baixo', 12, 5000.00, 'Conservador', true),
('Fundo Ações', 'FundoA', 0.185, 'Alto', 24, 10000.00, 'Agressivo', true),
('Tesouro Direto', 'Tesouro', 0.065, 'Baixo', 36, 100.00, 'Conservador', true),
('Fundo Multimercado', 'FundoM', 0.125, 'Moderado', 12, 2000.00, 'Moderado', true),
('LCA Agro Brasil', 'LCA', 0.085, 'Baixo', 18, 3000.00, 'Moderado', true);

--INSERT INTO clientes_investimento (perfil, pontuacao, descricao, volume_investimentos, frequencia_movimentacoes, preferencia_liquidez)
--VALUES
--('Conservador', 10, 'Perfil focado em segurança', 6000.00, 100.00, true),
--('Conservador', 35, 'Perfil focado em segurança', 12000.00, 5000.00, false),
--('Moderado', 55, 'Perfil equilibrado entre segurança e rentabilidade', 40000.00, 1000.00, true),
--('Moderado', 65, 'Perfil equilibrado entre segurança e rentabilidade', 30000.00, 100.00, false),
--('Agressivo', 80, 'Perfil focado em rentabilidade', 120000.00, 5000.00, true),
--('Agressivo', 80, 'Perfil focado em rentabilidade', 180000.00, 10000.00, false);

INSERT INTO historico_investimentos (cliente_id, tipo, valor, rentabilidade, data)
VALUES
(1, 'CDB', 5000.00, 0.12, '2025-01-15'),
(1, 'FundoM', 3000.00, 0.125, '2025-03-10'),
(2, 'LCA', 7000.00, 0.085, '2025-03-11'),
(2, 'FundoA', 8000.00, 0.185, '2025-11-12'),
(3, 'LCI', 7000.00, 0.095, '2025-06-20'),
(4, 'Tesouro', 2000.00, 0.065, '2025-02-01'),
(4, 'LCI', 10000.00, 0.095, '2025-06-20'),
(5, 'FundoA', 50000.00, 0.185, '2025-11-15'),
(5, 'FundoA', 40000.00, 0.185, '2025-11-15'),
(5, 'FundoA', 15000.00, 0.185, '2025-11-15');