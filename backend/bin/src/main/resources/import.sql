INSERT INTO TB_TECNICO (nome, telefone, email, senha, created_at) VALUES ('Marcelo Taz', '86876876', 'marcelo@gmail.com', '1234', now());
INSERT INTO TB_TECNICO (nome, telefone, email, senha, created_at) VALUES ('Bruno Mazeo', '86876876', 'bruno@gmail.com', '1234', now());
INSERT INTO TB_TECNICO (nome, telefone, email, senha, created_at) VALUES ('Claudia Raia', '86876876', 'claudia@gmail.com', '1234', now());
INSERT INTO TB_EQUIPAMENTO (equipamento, patrimonio, setor) VALUES ('Notebook Lenovo', '12345', 'CEREL');
INSERT INTO TB_EQUIPAMENTO (equipamento, patrimonio, setor) VALUES ('Daten AllInOne', '54321', 'DIREN');
INSERT INTO TB_ORDEM_DE_SERVICO (descricao_problema, descricao_solucao, data_cadastro, prioridade, status, id_tecnico_fk) VALUES ('Muito lento e não imprime', '', '2022-06-10', 'ALTA', 'PENDENTE', 1);