package com.projeto.grupo10.vacineja.model.usuario;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Cidadao {

    @Id
    private String cpf;

    @ElementCollection
    private Set<String> profissoes;

    @ElementCollection
    private Set<String> comorbidades;

    @OneToOne
    private FuncionarioGoverno funcionarioGoverno;

    @OneToOne
    private CartaoVacina cartaoVacina;


    private String email;
    private String nome;
    private String endereco;
    private String cartaoSus;
    private String data_nascimento;
    private String telefone;
    private String senha;


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
        this.funcionarioGoverno = null;
        this.senha = senha;
        this.cartaoVacina = new CartaoVacina(cpf);
    }
    public String getSenha(){
        return this.senha;
    }
    public String getCpf(){
        return this.cpf;
    }


    public boolean isFuncionario (){
        return this.funcionarioGoverno != null && this.funcionarioGoverno.isAprovado();
    }

    public boolean isCidadao (){
        return this.funcionarioGoverno == null;
    }


    public boolean aguardandoAutorizacaoFuncionario() {
        return this.funcionarioGoverno != null && !this.funcionarioGoverno.isAprovado();
    }

    public void autorizaCadastroFuncionario(){
        this.funcionarioGoverno.aprovaCadastro();
    }

    public void avancarSituacaoVacina(){
        this.cartaoVacina.proximaSituacao();
    }

    public void receberVacina(Vacina vacina, Date dataVacina) {
        this.cartaoVacina.proximaSituacao(vacina, dataVacina);
    }

    public void setFuncionarioGoverno (FuncionarioGoverno funcionarioGoverno){
        this.funcionarioGoverno = funcionarioGoverno;
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

    public FuncionarioGoverno getFuncionarioGoverno() {
        return funcionarioGoverno;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

