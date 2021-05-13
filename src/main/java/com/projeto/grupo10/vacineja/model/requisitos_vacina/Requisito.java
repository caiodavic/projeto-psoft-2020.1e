package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Requisito{

    @Id
    protected String requisito;
    protected int idade;
    boolean podeVacinar;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoRequisito tipoRequisito;

    public Requisito(){}
    public Requisito(String requisito, int idade, TipoRequisito tipoRequisito){
        this.requisito = requisito;
        this.idade = idade;
        this.tipoRequisito = tipoRequisito;
    }
    public int getIdade(){return idade;}
    public String getRequisito(){return requisito;}
    public void setIdade(int idade){this.idade = idade;}
    public void setPodeVacinar(){this.podeVacinar = true;}
    public boolean isPodeVacinar() {
        return podeVacinar;
    }

    public TipoRequisito getTipoRequisito() {
        return tipoRequisito;
    }

    @Override
    public String toString() {
        return "Requisito: " + requisito + '\n' +
                "Idade: " + idade + '\n' +
                "Habilitado para vacina: " + podeVacinar;
    }
}
