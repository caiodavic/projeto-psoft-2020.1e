package com.projeto.grupo10.vacineja.DTO;

import java.util.Date;

public class MinistraVacinaDTO {

    private String cartaoSus;
    private Date dataVacinacao;
    private long loteVacina;
    private String tipoVacina;

    public String getCartaoSus() {
        return cartaoSus;
    }

    public Date getDataVacinacao() {
        return dataVacinacao;
    }

    public long getLoteVacina() {
        return loteVacina;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }

}
