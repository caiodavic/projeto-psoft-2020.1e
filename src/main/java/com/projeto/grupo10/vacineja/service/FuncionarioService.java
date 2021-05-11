package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;

import javax.servlet.ServletException;
import java.util.List;

public interface FuncionarioService {

    public void alteraIdadeGeral(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void setComorbidadeHabilitada(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void ministraVacina(String headerToken, MinistraVacinaDTO ministraVacinaDTO) throws ServletException;
    public List<String> listaComorbidadesCadastradas(String headerToken) throws ServletException, IllegalArgumentException;
    public List<String> listaProfissoesCadastradas(String headerToken) throws ServletException, IllegalArgumentException;
    public int getCidadaosAcimaIdade(String headerToken, int idade) throws ServletException;
    public int getQtdCidadaosAtendeRequisito(String headerToken, RequisitoDTO requisito) throws ServletException, IllegalArgumentException;
}