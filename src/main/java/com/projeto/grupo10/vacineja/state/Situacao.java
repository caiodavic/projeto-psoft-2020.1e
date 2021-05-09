package com.projeto.grupo10.vacineja.state;

import com.projeto.grupo10.vacineja.model.usuario.CartaoVacina;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.util.Date;

public interface Situacao {

    public void agendarVacinacao(CartaoVacina cartaoVacina, Date data);
    public void proximaSituacao(CartaoVacina cartaoVacina);
    public void proximaSituacao(CartaoVacina cartaoVacina, Vacina vacina, Date data);
    public String toString();

}
