package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

public interface VacinaService {
    public Vacina criaVacina(VacinaDTO vacinaDTO, String authToken) throws ServletException;
    public List<Vacina> listarVacinas();
    public Optional<Vacina> getVacinaById(String nomeFabricante);
    public Vacina fetchVacina(String nomeFabricante);

}
