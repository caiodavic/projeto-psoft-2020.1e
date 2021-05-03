package com.projeto.grupo10.vacineja.repository;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LoteRepository extends JpaRepository<Lote,Long> {
    List<Lote> findByNomeFabricanteVacina(String nomeFabricante);
    Lote findByNomeFabricanteVacinaAndQtdDosesGreaterThan(String nomeFabricante,int qtdDoses);
}
