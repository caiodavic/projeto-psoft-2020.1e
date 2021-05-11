package com.projeto.grupo10.vacineja.DTO;

import java.util.Date;

public class MinistraVacinaDTO {

    //TODO se cartao vacinacao usa o numero do cartao_sus, esse usaria tb?
    private String cpfCidadao;
    private Date dataVacinacao;
    private long loteVacina;
    private String tipoVacina;

    public String getCpfCidadao() {
        return cpfCidadao;
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
