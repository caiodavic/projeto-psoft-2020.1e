package com.projeto.grupo10.vacineja.model.vacina;


import javax.persistence.*;
import java.time.temporal.ChronoUnit;

@Entity
public class Vacina {

    @Id
    private String nomeFabricante;

    private int numDosesNecessarias;

    private ChronoUnit diasEntreDoses;

    public Vacina(){}
    public Vacina(String nomeFabricante, int numDosesNecessarias, ChronoUnit numDiasEntredoses){
        // TO-DO verificar se o fabricante existe
        this.nomeFabricante = nomeFabricante;
        this.numDosesNecessarias = numDosesNecessarias;
        this.diasEntreDoses = numDiasEntredoses;
    }
}
