package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public class Tomou1Dose implements Situacao {
    private static final String SITUACAO_ATUAL = "Tomou a 1ª dose";
    private static final String MENSSAGEM_ERRO_TENTATIVA_VACINACAO = "Cidadão não habilitado para tomar a segunda dose";
    private static final String MENSSAGEM_ERRO_AGENDAMENTO =
            "Cidadão não pode agendar a vacinação da segunda dose para uma data anterior a data prevista";


    public Tomou1Dose(){}


    /**
     * O agendamento da segunda dose deve ser igual ou superior ao da data indicada a partir da primeira dose
     * @param cartaoVacina
     * @param data
     */
    @Override
    public void agendarVacinacao(CartaoVacina cartaoVacina, Date data) {
        if (cartaoVacina.getDataPrevistaSegundaDose().after(data)) {
            throw new IllegalArgumentException(MENSSAGEM_ERRO_AGENDAMENTO);
        }
        cartaoVacina.setDataAgendamento(data);
    }

    /**
     * Se a data prevista para segunda dose já tiver passado, o cidadão pode ser habilitado para segunda dose
     * @param cartaoVacina
     */
    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {
        Date dataPrevistaSegundaDose = cartaoVacina.getDataPrevistaSegundaDose();
        if (dataPrevistaSegundaDose.before(new Date())) {
            cartaoVacina.setSituacao(SituacaoEnum.HABILITADO2DOSE);
        }
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {}

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }
}
