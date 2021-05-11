package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public interface Situacao {
    void agendarVacinacao(CartaoVacina cartaoVacina, Date data);
    void proximaSituacao(CartaoVacina cartaoVacina);
    void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data);
    String toString();
}
