package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import javax.persistence.Entity;

@Entity
public class RequisitoProfissao extends Requisito{

    private boolean habilitado;

    public RequisitoProfissao(){}
    public RequisitoProfissao(int idade, String profissao){
        super.idade = idade;
        super.requisito = profissao;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}
