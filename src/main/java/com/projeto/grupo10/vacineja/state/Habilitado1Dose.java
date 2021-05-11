package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Calendar;
import java.util.Date;

public class Habilitado1Dose implements Situacao{
    private static final String SITUACAO_ATUAL = "Habilitado para tomar a 1ª dose";
    private static final String MENSSAGEM_EMAIL = "Ola %s" +
            "\nVocê esta apto para receber a 1ª dose da vacina! " +
            "\nPor favor acesse o sistema Vacine Já para agendar sua vacinação";
    private static final String ASSUNTO_EMAIL = "Vacinação primeira dose";


    public Habilitado1Dose() { }

    @Override
    public void agendarVacinacao(CartaoVacina cartaoVacina, Date data) {
        cartaoVacina.setDataAgendamento(data);
    }

    /**
     * Ao passar para o proximo estado, caso o vacina escolhida para o cidadão seja de dose unica, a vacinação dele
     * sera considerada finalizada, caso não ele deve passar para o estado "tomou primeira dose"
     * @param cartaoVacina
     */
    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {}

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {
        cartaoVacina.setDataPrimeiraDose(data);
        cartaoVacina.setVacina(vacina);

        if (cartaoVacina.getQtdDoseVacina() == 1) {
            cartaoVacina.setSituacao(SituacaoEnum.VACINACAOFINALIZADA);
        } else {
            cartaoVacina.setSituacao(SituacaoEnum.TOMOU1DOSE);

            Date dataSegundaDose = this.diaPropostoSegundaDose(cartaoVacina.getDataPrimeiraDose(),
                    cartaoVacina.getIntervaloVacina());

            cartaoVacina.setDataPrevistaSegundaDose(dataSegundaDose);
            cartaoVacina.setDataAgendamento(dataSegundaDose);
        }

    }

    private Date diaPropostoSegundaDose(Date primeiraDose, int diasEntreDoses){
        Calendar calendarioSegundaDose = new Calendar.Builder().build();
        calendarioSegundaDose.setTime(primeiraDose);
        calendarioSegundaDose.add(Calendar.DAY_OF_MONTH, diasEntreDoses);
        return calendarioSegundaDose.getTime();
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }


}
