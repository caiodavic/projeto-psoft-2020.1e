package com.projeto.grupo10.vacineja.model.requisitos_vacina;

public class RequisitoDTO {
    protected int idade;
    protected String requisito;

    public RequisitoDTO(int idade, String requisito) {
        this.idade = idade;
        this.requisito = requisito;
    }

    public int getIdade() {
        return idade;
    }

    public String getRequisito() {
        return requisito;
    }


}
