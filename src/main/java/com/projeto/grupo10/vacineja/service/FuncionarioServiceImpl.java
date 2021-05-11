package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    RequisitoService requisitoService;

    /**
     * Método responsável por chamar o método de requisitoService que altera a idade geral das pessoas que podem vacinar
     * @param requisito idade a ser habilitada para vacina
     * @param headerToken token do funcionário que está executando a operação
     * @throws ServletException lança caso o token seja inválido
     * @throws IllegalArgumentException lança caso o requisito esteja errado
     * @throws IllegalCallerException  lança caso tenha mais pessoas a serem habilitadas do que doses disponiveis
     */
    @Override
    public void alteraIdadeGeral(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException, IllegalCallerException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        if(!cidadaoService.podeAlterarIdade(requisito))
            throw new IllegalCallerException("Impossivel habilitar a idade");

        requisitoService.setIdade(requisito);
    }

    /**
     * Método responsável por chamar o método de requisitoService que habilita um novo requisito
     * @param requisito requisito a ser habilitado para vacina
     * @param headerToken token do funcionário que está executando a operação
     * @throws ServletException lança caso o token seja inválido
     * @throws IllegalArgumentException lança caso o requisito esteja errado
     * @throws IllegalCallerException  lança caso tenha mais pessoas a serem habilitadas do que doses disponiveis
     */
    @Override
    public void setComorbidadeHabilitada(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException, IllegalCallerException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        if(!cidadaoService.podeHabilitarRequisito(requisito))
            throw new IllegalCallerException("Impossível habilitar tal requisito");

        requisitoService.setPodeVacinar(requisito);
    }
}