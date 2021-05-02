package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoDTO;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.model.usuario.FuncionarioCadastroDTO;
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
    public ArrayList<String> getUsuariosNaoAutorizados(String headerToken) throws ServletException;
    public void autorizarCadastroFuncionario(String headerToken, String cpfFuncionario) throws ServletException;
    public Cidadao criaCidadao(CidadaoDTO cidadaoDTO);
    public void salvarCidadao(Cidadao cidadao);
    public Optional<Cidadao> getCidadaoByCpf(String cpf);
}
