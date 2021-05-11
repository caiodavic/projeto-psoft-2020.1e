package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public class NaoHabilitado implements Situacao{
    private static final String SITUACAO_ATUAL = "Não habilitado para tomar a vacina";
    private static final String MENSSAGEM_ERRO_TENTATIVA_VACINACAO = "Cidadão não habilitado para tomar a vacina";
    private static final String MENSSAGEM_ERRO_AGENDAR_VACINACAO = "Cidadão não pode marcar vacinação";

    public NaoHabilitado() {
    }


    @Override
    public void agendarVacinacao(CartaoVacina cartaoVacina, Date data) {
        throw new IllegalArgumentException(MENSSAGEM_ERRO_AGENDAR_VACINACAO);
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {
        cartaoVacina.setSituacao(SituacaoEnum.HABILITADO1DOSE);
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {
        throw new IllegalArgumentException(this.MENSSAGEM_ERRO_TENTATIVA_VACINACAO);
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
