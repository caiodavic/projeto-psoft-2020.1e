package com.projeto.grupo10.vacineja.state;

public class Habilitado1Dose implements Situacao{
    private static final String SITUACAO_ATUAL = "Habilitado para tomar a 1ª dose";

    public Habilitado1Dose() {
    }

    @Override
    public Situacao proximaSituacao() {
        return new Tomou1Dose();
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }


}
