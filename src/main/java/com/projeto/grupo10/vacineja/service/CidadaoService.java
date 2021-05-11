package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.CidadaoDTO;
import com.projeto.grupo10.vacineja.DTO.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.DTO.CidadaoUpdateDTO;
import com.projeto.grupo10.vacineja.DTO.FuncionarioCadastroDTO;
import com.projeto.grupo10.vacineja.model.usuario.*;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.observer.Subscriber;

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
    void cadastraCidadao(CidadaoDTO cidadaoDTO);
    Cidadao updateCidadao(String headerToken, CidadaoUpdateDTO cidadaoUpdateDTO, Cidadao cidadao) throws ServletException;
    void verificaTokenFuncionario(String authHeader) throws ServletException;
    void habilitarSegundaDose(String headerToken) throws ServletException;
    void ministraVacina(String headerToken, String cpfCidadao, Vacina vacina, Date dataVacina) throws ServletException;
}
