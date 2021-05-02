package com.projeto.grupo10.vacineja.model.lote;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import java.time.LocalDate;

public class LoteDTO {
    private Long id;

    private String nomeFabricanteVacina;

    private int qtdDoses;

    private LocalDate dataDeValidade;

    public Long getId() {
        return id;
    }

    public String getNomeFabricanteVacina() {
        return nomeFabricanteVacina;
    }

    public int getQtdDoses() {
        return qtdDoses;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }


}
