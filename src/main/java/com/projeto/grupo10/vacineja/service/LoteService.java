package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.util.List;

/**
 * Responsável por ministrar métodos de criação, listagem, remoção, reserva e validação de vacinas em lotes.
 */
public interface LoteService {
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina, String token) throws ServletException;
    public List<Lote> listaLotes(String headerToken) throws ServletException;
    public List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException;
    public List<Lote> removeDoseLotes(String nomeFabricante, int qtdVacinas, String headerToken) throws ServletException;
    public Vacina reservaVacinaEmLote(LocalDate dataVacinacao);
    public Vacina getVacinaReservadaByIdLote(Long idLote);
    public Vacina getVacinaReservadaByNomeFabricante(String nomeFabricante);
    public int getQtdVacinaDisponivel();

}
