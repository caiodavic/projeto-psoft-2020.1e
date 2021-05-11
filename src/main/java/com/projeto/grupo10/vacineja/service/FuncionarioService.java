package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;

import javax.servlet.ServletException;

public interface FuncionarioService {

    public void alteraIdadeGeral(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void setComorbidadeHabilitada(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
}