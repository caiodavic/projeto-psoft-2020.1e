package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import javax.persistence.*;

@MappedSuperclass
public abstract class Requisito{

    @Id
    long id;
    protected int idade;
    protected String requisito;

    public int getIdade(){return idade;}
    public String getRequisito(){return requisito;}
    public void setIdade(int idade){this.idade = idade;}
}
