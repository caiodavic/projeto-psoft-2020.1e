package com.projeto.grupo10.vacineja.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class CidadaoUpdateDTO {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String endereco;

    @NotEmpty
    private String telefone;

    @NotEmpty
    private Set<String> profissoes;

    @NotEmpty
    private Set<String> comorbidades;

    @NotEmpty
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
