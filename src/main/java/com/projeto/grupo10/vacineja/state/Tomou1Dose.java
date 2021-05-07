package com.projeto.grupo10.vacineja.state;

public class Tomou1Dose implements Situacao {
    private static final String SITUACAO_ATUAL = "Tomou a 1ª dose";

    public Tomou1Dose(){
    }

    @Override
    public Situacao proximaSituacao() {
        return new Habilitado2Dose();
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
