package com.projeto.grupo10.vacineja.repository;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacinaRepository extends JpaRepository<Vacina, String> {
    List<Vacina> findByNomeFabricante(String nomeFabricante);
}
