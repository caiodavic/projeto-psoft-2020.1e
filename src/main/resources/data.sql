INSERT INTO vacina VALUES ('CoronaVac-Butatan', 28, 2);
INSERT INTO vacina VALUES ('Moderna', 28, 2);
INSERT INTO vacina VALUES ('Pfizer-BioNTech', 28, 2);
INSERT INTO vacina VALUES ('Sputnik V', 21, 1);
INSERT INTO vacina VALUES ('Oxford-AstraZeneca', 28, 2);
INSERT INTO vacina VALUES ('Janssen',0,1);
INSERT INTO vacina VALUES ('Vacina do Caetano',0,1);
INSERT INTO vacina VALUES ('string',0,2);


--Criando o Administrador =
/*
 login: '00000000000'



 */
INSERT INTO cidadao (email,cpf,nome,senha) VALUES ('admin@admin','00000000000','Administrador','admin');

-- Criando um Funcionário
/*

 */
INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678001',true,'Médico chefe','Laguinho da UFCG');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf)
VALUES ('12345678001',date'1999-05-28','fernando.costa@ccc.ufcg.edu.br','Rua das Ostras,4','Fernando','senha','(79)99982-1201','12345678001');

-- Criando 10 cidadãos para tomar vacina
INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256001');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES ('12345670010','154789653256001',date'1930-06-29','vovoari@hotmail.com','Av Ruy Barbosa, 23', 'Ari Moreira', 'voari','(71)99923-1032','154789653256001');
INSERT INTO cidadao_profissoes VALUES('12345670010','Advogado');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256002');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES ('12345670011','154789653256002',date'1940-12-12','meninamari@gmail.com','Rua Flavio Menezes, 171', 'Mari Barbosa','meninamari','(79)99913-2422','154789653256002');
INSERT INTO cidadao_profissoes VALUES ('12345670011','Médico');
INSERT INTO cidadao_comorbidades VALUES ('12345670011','Diabetes');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256003');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670012','154789653256003',date'1950-01-06','primoeric@gmail.com','Travessa Joaquim Sabino, 643', 'Eric Sanches','primoeric','(73)3288-6873','154789653256003');
INSERT INTO cidadao_profissoes VALUES ('12345670012','Professor Universitário');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256004');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670013','154789653256004',date'1960-01-22','amigagiovanna@gmail.com','Av das Lontras, 62', 'Giovanna Munaretto','amigagiovanna','(79)99963-6873','154789653256004');
INSERT INTO cidadao_profissoes VALUES ('12345670013','Estudante de Medicina da Unit');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256005');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670014','154789653256005',date'1970-10-05','jojotoddynhasua@gmail.com','Rua Anitta, 69', 'Jojo Toddynha','jojotoddy','(79)3386-3342','154789653256005');
INSERT INTO cidadao_profissoes VALUES ('12345670014','Cantor');
INSERT INTO cidadao_comorbidades VALUES ('12345670014','Pressão Alta');
INSERT INTO cidadao_comorbidades VALUES ('12345670014','Diabetes');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256006');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670015','154789653256006',date'1980-11-22','froidcantor@gmail.com','Av do Matheus, 27', 'Froid Renato','froid','(79)3523-5342','154789653256006');
INSERT INTO cidadao_profissoes VALUES ('12345670015','Cantor');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256007');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670016','154789653256007',date'1990-07-24','mrcatrafunkeiro@gmail.com','Av do Leopardo Cego, 27', 'Señor Catra','catra','(79)96642-5342','154789653256007');
INSERT INTO cidadao_profissoes VALUES ('12345670016','Cantor');
INSERT INTO cidadao_comorbidades VALUES ('12345670016','Diabetes');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256008');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670017','154789653256008',date'1989-12-03','juliettefreire@bbb.com','Rua da Pracha Quebrada, 1500000', 'Juliette Freire','juliette','(83)3243-3312','154789653256008');
INSERT INTO cidadao_profissoes VALUES ('12345670017','Influencer');
INSERT INTO cidadao_profissoes VALUES ('12345670017','Advogado');
INSERT INTO cidadao_profissoes VALUES ('12345670017','Maquiador');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256009');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670018','154789653256009',date'1991-07-14','gilnogueira@bbb.com','Rua da Pracha Quebrada, 444', 'Gil Nogueira','cachorrada','(83)3252-0000','154789653256009');
INSERT INTO cidadao_profissoes VALUES ('12345670018','Influencer');
INSERT INTO cidadao_profissoes VALUES ('12345670018','Economista');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256010');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670019','154789653256010',date'1991-09-14','amywinehouse@jazz.com','Av Beer Madinson, 33', 'Amy Winehouse','amywinehouse','(63)99982-3200','154789653256009');
INSERT INTO cidadao_profissoes VALUES ('12345670019','Cantor');

INSERT INTO cartao_vacina (cartao_sus) VALUES ('154789653256011');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670020','154789653256011',date'1999-01-06','tomazpadula@hotmail.com','Av das Pedras Verdes Latejantes, 420', 'Tomaz Padula','tomazpadula','(11)99900-0000','154789653256011');
INSERT INTO cidadao_profissoes VALUES ('12345670020','Estudante de Medicina');

