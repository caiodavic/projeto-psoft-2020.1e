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

    private int qtdDosesDisponiveis;

    private LocalDate dataDeValidade;

    private int qtdDosesReservadas;

    public Lote() {
    }

    public Lote(Vacina vacina, int qtdDoses, LocalDate dataDeValidade) {
        this.nomeFabricanteVacina = vacina.getNomeFabricante();
        this.vacina = vacina;
        this.qtdDosesDisponiveis = qtdDoses;
        this.dataDeValidade = dataDeValidade;
        this.qtdDosesReservadas = 0;
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

    public int getQtdDosesDisponiveis() {
        return qtdDosesDisponiveis;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void diminuiQtdDosesDisponiveis(){
        qtdDosesDisponiveis--;
    }

    public int getQtdDosesReservadas(){return qtdDosesReservadas;}

    public void diminuiQtdDosesReservadas(){ qtdDosesReservadas--;}

    public void aumentaQtdDosesReservadas(){
        qtdDosesReservadas++;
    }
}

