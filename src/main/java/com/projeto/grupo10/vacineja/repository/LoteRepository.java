package com.projeto.grupo10.vacineja.repository;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote,Long> {
    long countByQtdDosesAndAndNomeFabricanteVacina(String nomeFabricante);
    Lote findByNomeFabricanteVacina(String nomeFabricante);
    Lote findByNomeFabricanteVacinaAndQtdDosesGreaterThan(String nomeFabricante,int qtdDoses);
}
