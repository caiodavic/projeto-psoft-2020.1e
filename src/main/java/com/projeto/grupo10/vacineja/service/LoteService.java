package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import java.util.List;

public interface LoteService {
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina);
    public List<Lote> listaLotes();
    public void salvaLote(Lote loteVacina);
    public void removeDoseLotes(String nomeFabricante);
    public boolean verificaQtdDoseLotes(String nomeFabricante, int qtdDoses);
}
