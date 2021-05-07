package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.requisitos_vacina.*;

import java.util.List;
import java.util.Optional;

public interface RequisitoService {

    public void setIdade(RequisitoDTO requisito);
    public void setNovaComorbidade(RequisitoDTO requisito) throws IllegalArgumentException;
    public void setNovaProfissao(RequisitoDTO requisito) throws IllegalArgumentException;
    public void setNovaIdadeComorbidadeProfissao(RequisitoDTO requisito);
    public Optional<Requisito> getRequisitoById(String requisito);
    public List<Requisito> getTodasComorbidades() throws IllegalArgumentException;
    public Requisito getIdade() throws IllegalArgumentException;
    public List<Requisito> getTodasProfissoes();
    public RequisitoDTO setPodeVacinar(String requisito) throws IllegalArgumentException;

}
