package com.projeto.grupo10.vacineja.DTO;

import java.time.LocalDate;
import java.util.Set;

public class CidadaoUpdateDTO {

    private String email;
    private String nome;
    private String endereco;
    private String telefone;
    private Set<String> profissoes;
    private Set<String> comorbidades;
    private String senha;


    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public Set<String> getProfissoes() {
        return profissoes;
    }

    public Set<String> getComorbidades() {
        return comorbidades;
    }

    public String getSenha() {
        return senha;
    }
}
