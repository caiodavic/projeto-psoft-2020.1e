package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.DTO.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.observer.Publisher;

import java.time.LocalDate;
import java.util.List;

/**
 * Responsável por ministrar métodos de criação, listagem, remoção, reserva e validação de vacinas em lotes.
 */
public interface LoteService extends Publisher {
    Lote criaLote(LoteDTO loteDTO, Vacina vacina);
    List<Lote> listaLotes();
    List<Lote> listaLotesPorFabricante(String nomeFabricante);
    List<Lote> removeDoseLotes(String nomeFabricante, int qtdVacinas);
//    Vacina reservaVacinaEmLote(LocalDate dataVacinacao);
//    Vacina getVacinaReservadaByIdLote(Long idLote);
//    Vacina getVacinaReservadaByNomeFabricante(String nomeFabricante); //TODO
    int getQtdVacinaDisponivel();
    LocalDate getMaiorValidadeLotes();
    Vacina retirarVacinaValidadeProxima(String nomeFabricante);
    boolean existeLoteDaVacina(String tipoVacina);

}
