INSERT INTO cidadao (cpf,senha) VALUES ('00000000000','admin');
INSERT INTO vacina VALUES ('CoronaVac-Butatan', 28, 2);
INSERT INTO vacina VALUES ('Moderna', 28, 2);
INSERT INTO vacina VALUES ('Pfizer-BioNTech', 28, 2);
INSERT INTO vacina VALUES ('Sputnik V', 21, 1);
INSERT INTO vacina VALUES ('Oxford-AstraZeneca', 28, 2);
INSERT INTO vacina VALUES ('Janssen',0,1);
INSERT INTO vacina VALUES ('Vacina do Caetano',0,1);

INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678001',true,'Médico chefe','Laguinho da UFCG');

INSERT INTO cidadao (nome,cpf,endereco,cartao_sus,email, data_nascimento,telefone,senha) VALUES ('Holliver de Oliveira Costa','08491176400','Floriano peixoto','123456789123456','holliver3000@gmail.com','1999-08-30','(83)9 99986059','123456');
INSERT INTO cidadao_comorbidades (cidadao_cpf,comorbidades) VALUES ('08491176400','nenhuma');
INSERT INTO cidadao_profissoes (cidadao_cpf,profissoes) VALUES ('08491176400','empresario');