package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.*;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.Requisito;
import com.projeto.grupo10.vacineja.model.usuario.*;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.observer.Subscriber;
import com.projeto.grupo10.vacineja.state.Situacao;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface CidadaoService extends Subscriber {
    Optional<Cidadao> getCidadaoById(String cpf);
    boolean validaCidadaoSenha (CidadaoLoginDTO cidadaoLogin);
    boolean validaLoginComoFuncionario (CidadaoLoginDTO cidadaoLogin);
    boolean validaLoginComoAdministrador (CidadaoLoginDTO cidadaoLogin);
    String teste(String id) throws ServletException;
    void cadastroFuncionario(String headerToken, FuncionarioCadastroDTO cadastroFuncionario) throws ServletException;
    ArrayList<String> getUsuariosNaoAutorizados() throws ServletException;
    void autorizarCadastroFuncionario(String cpfFuncionario) throws ServletException;
    Optional<Cidadao> cadastraCidadao(CidadaoDTO cidadaoDTO);
    Cidadao updateCidadao(String headerToken, CidadaoUpdateDTO cidadaoUpdateDTO) throws ServletException;
    void verificaTokenFuncionario(String authHeader) throws ServletException;
    void habilitarSegundaDose(String headerToken) throws ServletException;
    public void recebeVacina(String cpfCidadao, Vacina vacina, Date dataVacina);
    public boolean podeAlterarIdade(RequisitoDTO requisito);
    public boolean podeHabilitarRequisito(RequisitoDTO requisito);
    public Situacao getSituacao(String cpf);
    public void habilitaPelaIdade(Requisito requisito);
    public void habilitaPorRequisito(Requisito requisito);
    public String getEstadoVacinacao(String headerToken) throws ServletException;
    public int contaCidadaosAcimaIdade(int idade);
    public int contaCidadaosAtendeRequisito(RequisitoDTO requisito);

}
