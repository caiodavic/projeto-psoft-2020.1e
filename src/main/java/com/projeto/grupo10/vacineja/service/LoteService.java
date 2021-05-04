package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;

import javax.servlet.ServletException;
import java.util.List;

public interface LoteService {
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina, String token) throws ServletException;
    public List<Lote> listaLotes();
    public List<Lote> listaLotesPorFabricante(String nomeFabricante);
    public void salvaLote(Lote loteVacina);
    public List<Lote> removeDoseLotes(String nomeFabricante,int qtdVacinas,String headerToken) throws ServletException;
    public Lote findLoteByNomeFabricante(String nomeFabricante);
}
