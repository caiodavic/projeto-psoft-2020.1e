package com.projeto.grupo10.vacineja.model.lote;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Lote {

    @Id
    @GeneratedValue
    private Long id;

    private String nomeFabricanteVacina;

    @OneToOne
    private Vacina vacina;

    private int qtdDoses;

    private LocalDate dataDeValidade;

    public Lote() {
    }

    public Lote(Vacina vacina, int qtdDoses, LocalDate dataDeValidade) {
        this.nomeFabricanteVacina = vacina.getNomeFabricante();
        this.vacina = vacina;
        this.qtdDoses = qtdDoses;
        this.dataDeValidade = dataDeValidade;
    }

    public Long getId() {
        return id;
    }

    public String getNomeFabricanteVacina() {
        return nomeFabricanteVacina;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public int getQtdDoses() {
        return qtdDoses;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void diminuiQtdDoses(){
        qtdDoses--;
    }
}

