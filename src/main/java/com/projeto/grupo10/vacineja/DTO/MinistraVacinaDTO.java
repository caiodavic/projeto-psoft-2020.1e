package com.projeto.grupo10.vacineja.DTO;

import java.util.Date;

public class MinistraVacinaDTO {

    private String cpf;
    private Date dataVacinacao;
    private long loteVacina;
    private String tipoVacina;

    public String getCpf() {
        return this.cpf;
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

    public void setTipoVacina(String tipoVacina) {
        this.tipoVacina = tipoVacina;
    }
}
