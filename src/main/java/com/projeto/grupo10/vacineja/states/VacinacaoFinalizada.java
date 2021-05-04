package com.projeto.grupo10.vacineja.states;

public class VacinacaoFinalizada implements Situacao{
    private static final String SITUACAO_ATUAL = "Finalizada a Vacinação";

    public VacinacaoFinalizada() {
    }

    @Override
    public Situacao proximaSituacao() {
        return null;
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
