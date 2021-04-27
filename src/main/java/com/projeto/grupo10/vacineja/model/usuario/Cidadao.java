package com.projeto.grupo10.vacineja.model.usuario;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cidadao {

    @Id
    private String cpf;

    private String email;

    private String nome;
    private String endereco;
    private String cartaoSus;
    private String data_nascimento;
    private String telefone;

    @ElementCollection
    private Set<String> profissoes;

    @ElementCollection
    private Set<String> comorbidades;

    @OneToOne
    private FuncionarioGoverno tipoUsuario;

    private String senha;

    public Cidadao() {
    }

    public Cidadao(String nome, String cpf,String endereco, String cartaoSus, String email, String data_nascimento,
                   String telefone, HashSet<String> profissoes, HashSet<String> comorbidades) {

        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.cartaoSus = cartaoSus;
        this.email = email;
        this.data_nascimento = data_nascimento;
        this.telefone = telefone;
        this.profissoes = profissoes;
        this.comorbidades = comorbidades;
        this.tipoUsuario = null;
    }
    public String getSenha(){
        return this.senha;
    }
    public String getCpf(){
        return this.cpf;
    }

    public void setFuncionarioGoverno (String cargo, String localTrabalho){
        this.tipoUsuario = new FuncionarioGoverno(this.cpf, cargo, localTrabalho);
    }

    public boolean isFuncionario (){
        return this.tipoUsuario != null;
    }

    public boolean isCidadao (){
        return this.tipoUsuario == null;
    }


}

