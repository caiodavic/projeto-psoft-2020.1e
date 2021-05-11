package com.projeto.grupo10.vacineja.model.usuario;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.state.Situacao;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


/**
 * Representa uma Cidadão cadastrado no sistema. É unicamente identificada pelo seu CPF (não poderá ter dois Cidadões com o
 * mesmo numero de CPF). Além disso, possui um grupo de profissões e de comorbidades (com quantidade podendo ser 0 ou mais).
 */
@Entity
public class Cidadao {

    /**
     * É o cpf do Cidadão. É sua chave primária.
     */
    @Id
    private String cpf;

    /**
     * É o conjunto de profissões do Cidadão.
     */
    @ElementCollection
    private Set<String> profissoes;

    /**
     * É o conjunto de comorbidades do Cidadão.
     */
    @ElementCollection
    private Set<String> comorbidades;

    /**
     * É a representação do nivel de autoridade do usuario no sistema, indicando se ele é um Cidadão, um
     * Cidadão aguardando aprovação para ser funcionário, ou um funcionário do governo.
     */
    @OneToOne
    private FuncionarioGoverno funcionarioGoverno;

    /**
     * É a representação do cartão de vacinação do Cidadão.
     */
    @OneToOne
    private CartaoVacina cartaoVacina;

    /**
     * É o email do Cidadão.
     */
    private String email;

    /**
     * É o nome do Cidadão.
     */
    private String nome;

    /**
     * É a representação do endereço que o Cidadão resida.
     */
    private String endereco;

    /**
     * É o número do cartão do SUS do Cidadão.
     */
    private String cartaoSus;

    /**
     * É a data em que o Cidadão nasceu.
     */
    private LocalDate data_nascimento;

    /**
     * É o numero do telefone do Cidadão.
     */
    private String telefone;
    

    /**
     * É a senha do Cidadão. Utilizada para fazer o login no sistema.
     */

    @NotNull
    private String senha;


    /**
     * Cria um Cidadão.
     */
    public Cidadao() {
    }

    public Cidadao(String nome, String cpf,String endereco, String cartaoSus, String email,  LocalDate data_nascimento,
                   String telefone, Set<String> profissoes, Set<String> comorbidades, String senha, CartaoVacina cartaoVacina) {

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
        this.cartaoVacina = cartaoVacina;

    }
    public String getSenha(){
        return this.senha;
    }
    public String getCpf(){
        return this.cpf;
    }

    /**
     * Verifica se o Cidadão é um funcionario.
     */
    public boolean isFuncionario (){
        return this.funcionarioGoverno != null && this.funcionarioGoverno.isAprovado();
    }

    /**
     * Verifica se o Cidadão é apenas um Cidadão.
     */
    public boolean isCidadao (){
        return this.funcionarioGoverno == null;
    }

    /**
     * Veriifica se o Cidadão fez o pedido para ser um funcionário, mas não foi autorizado ainda.
     */
    public boolean aguardandoAutorizacaoFuncionario() {
        return this.funcionarioGoverno != null && !this.funcionarioGoverno.isAprovado();
    }

    /**
     * Método que altera o estado do Cidadão, retornado true para sua situação de ser funcionário.
     */
    public void autorizaCadastroFuncionario(){
        this.funcionarioGoverno.aprovaCadastro();
    }

    public void avancarSituacaoVacina(){
        this.cartaoVacina.proximaSituacao();
    }

    public void receberVacina(Vacina vacina, Date dataVacina) {
        this.cartaoVacina.proximaSituacao(vacina, dataVacina);
    }

    public void agendarVacina(Date data){
        this.cartaoVacina.agendarVacinacao(data);
    }

    public void setFuncionarioGoverno (FuncionarioGoverno funcionarioGoverno){
        this.funcionarioGoverno = funcionarioGoverno;
    }

    public Situacao getSituacao(){
        return this.cartaoVacina.getSituacaoAtual();
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

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
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