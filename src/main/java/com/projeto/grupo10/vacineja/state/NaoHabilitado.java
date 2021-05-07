package com.projeto.grupo10.vacineja.state;

public class NaoHabilitado implements Situacao{
    private static final String SITUACAO_ATUAL = "Não habilitado para tomar a vacina";

    public NaoHabilitado() {
    }

    @Override
    public Situacao proximaSituacao() {
        return new Habilitado1Dose();
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
