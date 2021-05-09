package com.projeto.grupo10.vacineja.state;
import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public class VacinacaoFinalizada implements Situacao{
    private static final String SITUACAO_ATUAL = "Vacinação já finalizada.";
    private static final String MENSSAGEM_ERRO_AGENDAR_VACINACAO = "Vacinação finalizada. Cidadão não pode marcar vacinação";

    public VacinacaoFinalizada() {
    }

    @Override
    public void agendarVacinacao(CartaoVacina cartaoVacina, Date data) {
        throw new IllegalArgumentException(this.MENSSAGEM_ERRO_AGENDAR_VACINACAO);
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {
        throw new IllegalArgumentException(SITUACAO_ATUAL);
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {
        throw new IllegalArgumentException(SITUACAO_ATUAL);
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
