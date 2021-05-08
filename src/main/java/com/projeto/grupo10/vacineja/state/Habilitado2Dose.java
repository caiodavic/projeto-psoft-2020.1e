package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Habilitado2Dose implements Situacao {
    private static final String SITUACAO_ATUAL = "Habilitado para tomar a 2ª dose";
    private static final String MENSSAGEM_EMAIL = "Ola %s" +
            "\nVocê esta apto para receber a 2ª dose da vacina! " +
            "\nPor favor acesse o sistema Vacine Já para agendar sua vacinação";
    private static final String ASSUNTO_EMAIL = "Vacinação primeira dose";

    public Habilitado2Dose() {

    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina) {
        cartaoVacina.setSituacao(SituacaoEnum.VACINACAOFINALIZADA);
    }

    @Override
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data) {
        cartaoVacina.setDataSegundaDose(data);
        this.proximaSituacao(cartaoVacina);
    }

    @Override
    public String toString() {
        return this.SITUACAO_ATUAL;
    }

}
