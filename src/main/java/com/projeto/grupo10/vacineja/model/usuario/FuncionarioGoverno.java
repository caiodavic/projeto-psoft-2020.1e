
package com.projeto.grupo10.vacineja.model.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FuncionarioGoverno {

    @Id
    private String cpf;

    private String cargo;

    String localTrabalho;

    public FuncionarioGoverno(){}

    public FuncionarioGoverno(String cpf, String cargo, String localTrabalho) {
        this.cpf = cpf;
        this.cargo = cargo;
        this.localTrabalho = localTrabalho;
  }