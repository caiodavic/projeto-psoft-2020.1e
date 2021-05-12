package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.*;
import com.projeto.grupo10.vacineja.repository.RequisitoRepository;
import com.projeto.grupo10.vacineja.util.PadronizaString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.projeto.grupo10.vacineja.util.PadronizaString.padronizaString;

@Service
public class RequisitoServiceImpl implements RequisitoService{

    @Autowired
    RequisitoRepository requisitoRepository;

    @Override
    public void setIdade(RequisitoDTO requisito) {
        if(!requisito.getRequisito().equals("idade"))
            throw new IllegalArgumentException("Requisito inválido");

        Optional<Requisito> idadeRequisito = getRequisitoById("idade");
        Requisito idade;
        if(idadeRequisito.isEmpty()){
            idade = new Requisito("idade",requisito.getIdade(),TipoRequisito.IDADE);
        } else {
            idade = idadeRequisito.get();
            idade.setIdade(requisito.getIdade());
        }
        requisitoRepository.save(idade);
    }

    @Override
    public Requisito setNovaComorbidade(RequisitoDTO requisito) throws IllegalArgumentException{

        if(!getRequisitoById(requisito.getRequisito()).isEmpty()){
            throw new IllegalArgumentException(String.format("Requisito %s já cadastrado",requisito.getRequisito()));
        }
        Requisito novaComorbidade = new Requisito(padronizaString(requisito.getRequisito()),requisito.getIdade(),TipoRequisito.COMORBIDADE);

        requisitoRepository.save(novaComorbidade);

        return getRequisitoById(PadronizaString.padronizaString(requisito.getRequisito())).get();
    }

    @Override
    public Requisito setNovaProfissao(RequisitoDTO requisito) throws IllegalArgumentException{

        if(!getRequisitoById(requisito.getRequisito()).isEmpty()){
            throw new IllegalArgumentException(String.format("Requisito %s já cadastrado",requisito.getRequisito()));
        }
        Requisito novaComorbidade = new Requisito(padronizaString(requisito.getRequisito()),requisito.getIdade(),TipoRequisito.PROFISSAO);

        requisitoRepository.save(novaComorbidade);

        return getRequisitoById(PadronizaString.padronizaString(requisito.getRequisito())).get();
    }

    @Override
    public Optional<Requisito> getRequisitoById(String requisito){
        Optional<Requisito> requisitoExistente = requisitoRepository.findById(PadronizaString.padronizaString(requisito));
        return requisitoExistente;
    }

    @Override
    public List<String> getTodasComorbidades() throws IllegalArgumentException{
        List<Requisito> requisitos = requisitoRepository.findAll();
        List<String> requisitoComorbidades = new ArrayList<>();

        if(requisitos.isEmpty()){
            throw new IllegalArgumentException("Nenhuma comorbidade cadastrada");
        }
        for(Requisito requisito:requisitos){
            if(requisito.getTipoRequisito().equals(TipoRequisito.COMORBIDADE))
                requisitoComorbidades.add(requisito.getRequisito());
        }

        return requisitoComorbidades;
    }

    @Override
    public Requisito getIdade() {
        Optional<Requisito> idadeRequisito = getRequisitoById("idade");

        if(idadeRequisito.isEmpty())
            throw new IllegalArgumentException("Nenhuma idade registrada como requisito");

        return idadeRequisito.get();
    }

    @Override
    public List<String> getTodasProfissoes() {
        List<Requisito> requisitos = requisitoRepository.findAll();
        List<String> requisitoProfissoes = new ArrayList<>();

        if(requisitos.isEmpty()){
            throw new IllegalArgumentException("Nenhuma profissao cadastrada");
        }
        for(Requisito requisito:requisitos){
            if(requisito.getTipoRequisito().equals(TipoRequisito.PROFISSAO))
                requisitoProfissoes.add(requisito.getRequisito());
        }

        return requisitoProfissoes;
    }

    @Override
    public RequisitoDTO setPodeVacinar(RequisitoDTO requisito) throws IllegalArgumentException{
        Optional<Requisito> requisitoPodeVacinar = requisitoRepository.findById(requisito.getRequisito());

        if(requisitoPodeVacinar.isEmpty()){
            throw new IllegalArgumentException("Requisito inexistete");
        }

        Requisito requisitoAlterado = requisitoPodeVacinar.get();
        requisitoAlterado.setIdade(requisito.getIdade());
        requisitoAlterado.setPodeVacinar();
        requisitoRepository.save(requisitoAlterado);

        return new RequisitoDTO(requisitoAlterado.getIdade(),requisitoAlterado.getRequisito());
    }

    @Override
    public List<String> requisitosHabilitados() throws IllegalArgumentException {
        List<String> requisitosString = new ArrayList<>();
        List<Requisito> requisitos = requisitoRepository.findAll();

        for(Requisito requisito:requisitos){
            if(requisito.isPodeVacinar()){
                String aux = "Requisito: " + requisito.getRequisito() + "\n" + "Idade: " + requisito.getIdade();
                requisitosString.add(aux);
            }
        }
        if(requisitosString.isEmpty()){
            throw new IllegalArgumentException("Nenhum requisito está habilitado ainda");
        }

        return requisitosString;
    }

}