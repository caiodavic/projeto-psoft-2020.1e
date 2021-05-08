package com.projeto.grupo10.vacineja.state;
import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public class VacinacaoFinalizada implements Situacao{
    private static final String SITUACAO_ATUAL = "Finalizada a Vacinação";

    public VacinacaoFinalizada() {
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {}

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {}

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
