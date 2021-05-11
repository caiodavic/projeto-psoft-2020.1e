package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import javax.persistence.*;

@MappedSuperclass
public abstract class Requisito{

    @Id
    protected String requisito;
    protected int idade;
    boolean podeVacinar;

    public int getIdade(){return idade;}
    public String getRequisito(){return requisito;}
    public void setIdade(int idade){this.idade = idade;}
    public void setPodeVacinar(){this.podeVacinar = true;}
}
