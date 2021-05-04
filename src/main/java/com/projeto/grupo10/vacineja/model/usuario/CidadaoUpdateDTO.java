package com.projeto.grupo10.vacineja.model.usuario;

import java.util.Set;

public class CidadaoUpdateDTO {

    private String cpf;
    private String email;
    private String nome;
    private String endereco;
    private String cartaoSus;
    private String data_nascimento;
    private String telefone;
    private Set<String> profissoes;
    private Set<String> comorbidades;
    private String senha;

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public String getData_nascimento() {
        return data_nascimento;
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
