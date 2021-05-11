package com.projeto.grupo10.vacineja.model.agenda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Agenda {
    @Id
    @GeneratedValue
    private Long id;
    private String cpf;
    private String data;
    private String horario;
    private String local;

    public Agenda(String cpf, String data, String horario, String local){
        this.cpf = cpf;
        this.data = data;
        this.horario = horario;
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getHorario() {
        return horario;
    }

    public String getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }
}
