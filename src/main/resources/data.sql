

-- Criando algumas Vacinas

INSERT INTO vacina VALUES ('coronavac', 28, 2);
INSERT INTO vacina VALUES ('moderna', 28, 2);
INSERT INTO vacina VALUES ('pfizer', 28, 2);
INSERT INTO vacina VALUES ('sputnik v', 21, 2);
INSERT INTO vacina VALUES ('astrazeneca', 28, 2);
INSERT INTO vacina VALUES ('janssen',0,1);
-- Vacina de teste ( com diasEntreDoses = 0)
--TODO tirar essa vacina
INSERT INTO vacina VALUES ('string',0,2);

-- Criando Lotes

INSERT INTO lote (data_de_validade, qtd_doses_disponiveis, vacina_nome_fabricante) VALUES (date'2021-12-31',10,'moderna');

INSERT INTO lote (data_de_validade, qtd_doses_disponiveis, vacina_nome_fabricante) VALUES (date'2021-12-30',10,'sputnik v');



--Criando o Administrador
/*
 {
  "cpfLogin": "00000000000",
  "senhaLogin": "admin",
  "tipoLogin": "Administrador"
}
 */

INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000000','VACINACAOFINALIZADA');
INSERT INTO cidadao (email,cpf,data_nascimento,nome,senha,cartao_vacina_cartao_sus) VALUES ('admin@admin','00000000000',date'1900-05-28','Administrador','admin','000000000000000');


-- Criando cinco Funcionários
/*
  {
  "cpfLogin": "12345678001",
  "senhaLogin": "senha",
  "tipoLogin": "Funcionário"
}
 */
INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000001','VACINACAOFINALIZADA');

INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678001',true,'Médico chefe','Laguinho da UFCG');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf,cartao_vacina_cartao_sus)
VALUES ('12345678001',date'1999-05-28','fernando.costa@ccc.ufcg.edu.br','Av do Matheus, 430','Fernando','senha','(79)99982-1201','12345678001','000000000000001');

INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000002','VACINACAOFINALIZADA');
INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678002',true,'Médico chefe','LLC 3');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf,cartao_vacina_cartao_sus)
VALUES ('12345678002',date'1999-04-20','holliver.costa@ccc.ufcg.edu.br','Av das Ostras Aladas, 33','Holliver Costa','senha','(83)99982-1201','12345678002','000000000000002');

INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000003','VACINACAOFINALIZADA');
INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678003',true,'Médico chefe','Sala do Caesi');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf,cartao_vacina_cartao_sus)
VALUES ('12345678003',date'1999-12-08','caio.silva@ccc.ufcg.edu.br','Rua das Ostras Aladas, 42','Caio Davi Silva','senha','(83)99188-1315','12345678003','000000000000003');

INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000004','VACINACAOFINALIZADA');
INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678004',true,'Médico chefe','Quiosque do Helhão');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf,cartao_vacina_cartao_sus)
VALUES ('12345678004',date'1999-12-08','caetano.albuquerque@ccc.ufcg.edu.br','Travessa do Dado Dolabela, 90','Caio Davi Silva','senha','(83)99975-7584','12345678004','000000000000004');

INSERT INTO cartao_vacina(cartao_sus, situacao) VALUES ('000000000000005','VACINACAOFINALIZADA');
INSERT INTO funcionario_governo(cpf,aprovado,cargo,local_trabalho) VALUES ('12345678005',true,'Médico chefe','Amarelinha');
INSERT INTO cidadao (cpf,data_nascimento,email,endereco,nome,senha,telefone,funcionario_governo_cpf,cartao_vacina_cartao_sus)
VALUES ('12345678005',date'1999-01-02','artur.souza@ccc.ufcg.edu.br','Travessa do Dado Dolabela, 100','Artur Souza','senha','(83)99968-5682','12345678005','000000000000005');


-- Criando 10 Cidadãos
INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256001','HABILITADO1DOSE');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES ('12345670010','154789653256001',date'1930-06-29','vovoari@hotmail.com','Av Ruy Barbosa, 23', 'Ari Moreira', 'voari','(71)99923-1032','154789653256001');
INSERT INTO cidadao_profissoes VALUES('12345670010','advogado');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256002','HABILITADO1DOSE');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES ('12345670011','154789653256002',date'1940-12-12','meninamari@gmail.com','Rua Flavio Menezes, 171', 'Mari Barbosa','meninamari','(79)99913-2422','154789653256002');
INSERT INTO cidadao_profissoes VALUES ('12345670011','medico');
INSERT INTO cidadao_comorbidades VALUES ('12345670011','diabetes');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256003','HABILITADO1DOSE');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670012','154789653256003',date'1950-01-06','primoeric@gmail.com','Travessa Joaquim Sabino, 643', 'Eric Sanches','primoeric','(73)3288-6873','154789653256003');
INSERT INTO cidadao_profissoes VALUES ('12345670012','professor universitario');

INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256004','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670013','154789653256004',date'1999-01-22','amigagiovanna@gmail.com','Av das Lontras, 62', 'Giovanna Munaretto','amigagiovanna','(79)99963-6873','154789653256004');
INSERT INTO cidadao_profissoes VALUES ('12345670013','estudante de medicina');

INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256005','HABILITADO1DOSE');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670014','154789653256005',date'1950-10-05','jojotoddynhasua@gmail.com','Rua Anitta, 69', 'Jojo Toddynha','jojotoddy','(79)3386-3342','154789653256005');
INSERT INTO cidadao_profissoes VALUES ('12345670014','cantor');
INSERT INTO cidadao_comorbidades VALUES ('12345670014','pressao alta');
INSERT INTO cidadao_comorbidades VALUES ('12345670014','diabetes');

INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256006','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670015','154789653256006',date'1980-11-22','caetanobca@gmail.com','Av do Matheus, 410', 'Froid Renato','froid','(79)3523-5342','154789653256006');
INSERT INTO cidadao_profissoes VALUES ('12345670015','cantor');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256007','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670016','154789653256007',date'1990-07-24','mrcatrafunkeiro@gmail.com','Av do Leopardo Cego, 27', 'Señor Catra','catra','(79)96642-5342','154789653256007');
INSERT INTO cidadao_profissoes VALUES ('12345670016','cantor');
INSERT INTO cidadao_comorbidades VALUES ('12345670016','diabetes');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256008','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670017','154789653256008',date'1989-12-03','juliettefreire@bbb.com','Rua da Pracha Quebrada, 1500000', 'Juliette Freire','juliette','(83)3243-3312','154789653256008');
INSERT INTO cidadao_profissoes VALUES ('12345670017','influencer');
INSERT INTO cidadao_profissoes VALUES ('12345670017','advogado');
INSERT INTO cidadao_profissoes VALUES ('12345670017','maquiador');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256009','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670018','154789653256009',date'1991-07-14','gilnogueira@bbb.com','Rua da Pracha Quebrada, 444', 'Gil Nogueira','cachorrada','(83)3252-0000','154789653256009');
INSERT INTO cidadao_profissoes VALUES ('12345670018','influencer');
INSERT INTO cidadao_profissoes VALUES ('12345670018','economista');


INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256010','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670019','154789653256010',date'1991-09-14','amywinehouse@jazz.com','Av Beer Madinson, 33', 'Amy Winehouse','amywinehouse','(63)99982-3200','154789653256009');
INSERT INTO cidadao_profissoes VALUES ('12345670019','cantor');

INSERT INTO cartao_vacina (cartao_sus,situacao) VALUES ('154789653256011','NAO_HABILITADO');
INSERT INTO cidadao(cpf, cartao_sus, data_nascimento, email, endereco, nome, senha, telefone, cartao_vacina_cartao_sus)
VALUES('12345670020','154789653256011',date'1999-01-06','tomazpadula@hotmail.com','Av do Matheus, 420', 'Tomaz Padula','tomazpadula','(11)99900-0000','154789653256011');
INSERT INTO cidadao_profissoes VALUES ('12345670020','estudante de medicina');




-- Vacinando alguns cidadãos
UPDATE cartao_vacina SET data_primeira_dose = timestamp'2021-03-01',data_prevista_segunda_dose = timestamp'2021-03-30',situacao = 'TOMOU1DOSE',vacina_nome_fabricante = 'moderna' WHERE cartao_sus = '154789653256001';

UPDATE cartao_vacina SET data_primeira_dose = timestamp'2021-04-01',data_prevista_segunda_dose = timestamp'2021-03-30',situacao = 'TOMOU1DOSE',vacina_nome_fabricante = 'sputnik v'  WHERE cartao_sus = '154789653256002';

UPDATE cartao_vacina SET data_primeira_dose = timestamp'2021-03-01',data_prevista_segunda_dose = timestamp'2021-03-30', data_segunda_dose = timestamp'2021-03-30',situacao = 'VACINACAOFINALIZADA',vacina_nome_fabricante = 'moderna' WHERE cartao_sus = '154789653256005';


-- Criando requisito

INSERT INTO requisito(requisito,idade,pode_vacinar,tipo_requisito) VALUES ('idade',70,true,'IDADE');
INSERT INTO requisito(requisito,idade,pode_vacinar,tipo_requisito) VALUES ('tcc',100,false,'COMORBIDADE');
INSERT INTO requisito(requisito,idade,pode_vacinar,tipo_requisito) VALUES ('teacher',100,false,'PROFISSAO');
INSERT INTO requisito(requisito,idade,pode_vacinar,tipo_requisito) VALUES ('cantor',10,false,'PROFISSAO');

-- Adicionando alguns requisitos
INSERT INTO cidadao_profissoes VALUES ('12345670020','estudante de medicina');

