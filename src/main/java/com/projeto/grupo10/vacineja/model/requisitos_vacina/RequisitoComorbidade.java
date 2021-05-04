package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import javax.persistence.Entity;

@Entity
public class RequisitoComorbidade extends Requisito{

    private boolean habilitado;

    public RequisitoComorbidade(){}
    public RequisitoComorbidade(int idade, String comorbidade){
        super.idade = idade;
        super.requisito = comorbidade;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

}
