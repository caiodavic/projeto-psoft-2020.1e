package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.LoteDTO;
import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.lote.Lote;

import javax.servlet.ServletException;
import java.util.List;

public interface FuncionarioService {

    public void alteraIdadeGeral(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void setComorbidadeHabilitada(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void ministraVacina(String headerToken, MinistraVacinaDTO ministraVacinaDTO) throws ServletException;
    List<Lote> listaLotes(String headerToken) throws ServletException;
    Lote criarLote(String headerToken, String nomeFabricante, LoteDTO loteDTO) throws ServletException;
    List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException;
    List<Lote> removeDoseLotes(String nomeFabricante, int qtdVacinas, String headerToken) throws ServletException;
}