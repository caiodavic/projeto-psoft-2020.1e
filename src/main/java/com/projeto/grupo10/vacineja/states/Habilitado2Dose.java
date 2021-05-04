package com.projeto.grupo10.vacineja.states;

public class Habilitado2Dose implements Situacao {
    private static final String SITUACAO_ATUAL = "Habilitado para tomar a 2ª dose";

    public Habilitado2Dose() {
    }

    @Override
    public Situacao proximaSituacao() {
        return new VacinacaoFinalizada();
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }

}
