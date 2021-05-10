package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.*;

import java.util.List;
import java.util.Optional;

public interface RequisitoService {
    void setIdade(RequisitoDTO requisito);
    void setNovaComorbidade(RequisitoDTO requisito) throws IllegalArgumentException;
    void setNovaProfissao(RequisitoDTO requisito) throws IllegalArgumentException;
    void setNovaIdadeComorbidadeProfissao(RequisitoDTO requisito);
    Optional<Requisito> getRequisitoById(String requisito);
    List<Requisito> getTodasComorbidades() throws IllegalArgumentException;
    Requisito getIdade() throws IllegalArgumentException;
    List<Requisito> getTodasProfissoes();
    RequisitoDTO setPodeVacinar(String requisito) throws IllegalArgumentException;
}
