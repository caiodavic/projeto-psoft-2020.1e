package com.projeto.grupo10.vacineja.model.requisitos_vacina;

import javax.persistence.Entity;

@Entity
public class RequisitoIdade extends Requisito{

    public RequisitoIdade(){}
    public RequisitoIdade(int idade){
        super.idade = idade;
        super.requisito = "idade";
        super.podeVacinar = true;
    }

}
