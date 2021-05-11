INSERT INTO cidadao (cpf,senha) VALUES ('00000000000','admin');
INSERT INTO vacina VALUES ('CoronaVac-Butatan', 28, 2);
INSERT INTO vacina VALUES ('Moderna', 28, 2);
INSERT INTO vacina VALUES ('Pfizer-BioNTech', 28, 2);
INSERT INTO vacina VALUES ('Sputnik V', 21, 1);
INSERT INTO vacina VALUES ('Oxford-AstraZeneca', 28, 2);
INSERT INTO vacina VALUES ('Janssen',0,1);
INSERT INTO vacina VALUES ('Vacina do Caetano',0,1);

INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678001',true,'Médico chefe','Laguinho da UFCG');

INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,funcionario_governo_cpf,cartao_vacina_cpf_usuario,funcionario_governo_cpf)
VALUES ('12345678001','1999-05-28','fernando.costa@ccc.ufcg.edu.br','Rua das Ostras,4','senha','12345678001');
