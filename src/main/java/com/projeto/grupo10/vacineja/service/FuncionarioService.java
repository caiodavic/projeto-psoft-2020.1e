package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.LoteDTO;
import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.lote.Lote;

import javax.servlet.ServletException;
import java.util.List;

public interface FuncionarioService {

    public void alteraIdadeGeral(int idade, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void setRequisitoHabilitado(RequisitoDTO requisito, String headerToken) throws ServletException,IllegalArgumentException, IllegalCallerException;
    public void ministraVacina(String headerToken, MinistraVacinaDTO ministraVacinaDTO) throws ServletException;
    public List<String> listaComorbidadesCadastradas(String headerToken) throws ServletException, IllegalArgumentException;
    public List<String> listaProfissoesCadastradas(String headerToken) throws ServletException, IllegalArgumentException;
    public int getCidadaosAcimaIdade(String headerToken, int idade) throws ServletException;
    public int getQtdCidadaosAtendeRequisito(String headerToken, RequisitoDTO requisito) throws ServletException, IllegalArgumentException;
    public List<Lote> listaLotes(String headerToken) throws ServletException;
    public Lote criarLote(String headerToken, String nomeFabricante, LoteDTO loteDTO) throws ServletException;
    public List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException;
    public List<Lote> removeDoseLotes(String nomeFabricante, int qtdVacinas, String headerToken) throws ServletException;

}