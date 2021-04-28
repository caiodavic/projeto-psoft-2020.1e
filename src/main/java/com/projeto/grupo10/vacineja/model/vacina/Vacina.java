package com.projeto.grupo10.vacineja.model.vacina;


import javax.persistence.*;
import java.time.temporal.ChronoUnit;

@Entity
public class Vacina {

    @Id
    private String nomeFabricante;

    private int numDosesNecessarias;

    private ChronoUnit diasEntreDoses;

    private int qtdDoses;

    public Vacina(){}
    public Vacina(String nomeFabricante, int numDosesNecessarias, ChronoUnit numDiasEntredoses){
        // TO-DO verificar se o fabricante existe (?)
        // TO-DO ver como transformar de int ChronoUnit (ou se há alguma forma melhor de fazer isso)
        this.nomeFabricante = nomeFabricante;
        this.numDosesNecessarias = numDosesNecessarias;
        this.diasEntreDoses = numDiasEntredoses;
        this.qtdDoses = 0;
    }

    public void addDoses(int numDoses){
        this.qtdDoses += numDoses;
    }
}
