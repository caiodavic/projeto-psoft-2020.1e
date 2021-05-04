package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.usuario.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Optional;

public interface CidadaoService {
    public Optional<Cidadao> getCidadaoById(String cpf);
    public boolean validaCidadaoSenha (CidadaoLoginDTO cidadaoLogin);
    public boolean validaLoginComoFuncionario (CidadaoLoginDTO cidadaoLogin);
    public boolean validaLoginComoAdministrador (CidadaoLoginDTO cidadaoLogin);
    public String teste(String id) throws ServletException;
    public void cadastroFuncionario(String headerToken, FuncionarioCadastroDTO cadastroFuncionario) throws ServletException;
    public ArrayList<String> getUsuariosNaoAutorizados() throws ServletException;
    public void autorizarCadastroFuncionario(String cpfFuncionario) throws ServletException;
    public Cidadao criaCidadao(CidadaoDTO cidadaoDTO);
    public void salvarCidadao(Cidadao cidadao);
    public Optional<Cidadao> getCidadaoByCpf(String cpf);
    public Cidadao updateCidadao(CidadaoUpdateDTO cidadaoUpdateDTO, Cidadao cidadao);
}
