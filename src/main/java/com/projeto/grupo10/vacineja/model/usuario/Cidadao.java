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

    private 

    public Cidadao() {
    }

    public Cidadao(String nome, String cpf,String endereco, String cartaoSus, String email, String data_nascimento,
                   String telefone, Set<String> profissoes, Set<String> comorbidades, String senha) {

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
        this.senha = senha;
    }
    public String getSenha(){
        return this.senha;
    }
    public String getCpf(){
        return this.cpf;
    }

    public void setFuncionarioGoverno (FuncionarioGoverno funcionarioGoverno){
        this.tipoUsuario = funcionarioGoverno;
    }

    public boolean isFuncionario (){
        return this.tipoUsuario != null && this.tipoUsuario.isAprovado();
    }

    public boolean isCidadao (){
        return this.tipoUsuario == null;
    }


    public boolean aguardandoAutorizacaoFuncionario() {
        return this.tipoUsuario != null && !this.tipoUsuario.isAprovado();
    }

    public void autorizaCadastroFuncionario(){
        this.tipoUsuario.aprovaCadastro();
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<String> getProfissoes() {
        return profissoes;
    }

    public void setProfissoes(Set<String> profissoes) {
        this.profissoes = profissoes;
    }

    public Set<String> getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(Set<String> comorbidades) {
        this.comorbidades = comorbidades;
    }

    public FuncionarioGoverno getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(FuncionarioGoverno tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}

