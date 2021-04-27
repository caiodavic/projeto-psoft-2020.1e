package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;

import javax.servlet.ServletException;
import java.util.Optional;

public interface CidadaoService {
    public Optional<Cidadao> getCidadaoById(String cpf);
    public void setFuncionarioGoverno (String cargo, String localTrabalho);
    public boolean validaCidadaoSenha (CidadaoLoginDTO cidadaoLogin);
    public String teste(String id) throws ServletException;

}
