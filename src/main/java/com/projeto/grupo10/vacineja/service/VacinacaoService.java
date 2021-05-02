package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;

import java.util.List;
import java.util.Optional;

public interface VacinacaoService {
    public Vacina criaVacina(VacinaDTO vacinaDTO);
    public List<Vacina> listarVacinas();
    public Optional<Vacina> getVacinaById(String nomeFabricante);
    public void addDosesVacinaEstoque(String nomeFabricante, int qtdDoses);
    public void removeDosesVacinaEstoque(String nomeFabricante, int qtdDoses);
    public boolean verificaEstoque(String nomeFabricante, int qtdDoses);
    public Vacina fetchVacina(String nomeFabricante);
}
