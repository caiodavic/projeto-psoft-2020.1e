package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoteServiceImpl implements LoteService {
    @Autowired
    LoteRepository loteRepository;

    @Override
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina) {
        Optional<Lote> optionalLote = loteRepository.findById(loteDTO.getId());
        if (optionalLote.isPresent()) {
            throw new IllegalArgumentException("Já há lote com esse ID cadastrado");
        }

        validaDoseLotes(loteDTO.getQtdDoses());
        validaDataDeValidade(loteDTO.getDataDeValidade());

        Lote lote = new Lote(vacina, loteDTO.getQtdDoses(), loteDTO.getDataDeValidade());
        loteRepository.save(lote);
        return lote;
    }

    @Override
    public List<Lote> listaLotes() {
        return loteRepository.findAll();
    }

    @Override
    public void salvaLote(Lote loteVacina) {
        loteRepository.save(loteVacina);
    }

    public boolean verificaQtdDoseLotes(String nomeFabricante, int qtdDoses){
        return qtdDoses < loteRepository.countByQtdDosesAndAndNomeFabricanteVacina(nomeFabricante);
    }

    @Override
    public void removeDoseLotes(String nomeFabricante) {
        Lote lote = loteRepository.findByNomeFabricanteVacinaAndQtdDosesGreaterThan(nomeFabricante,0);
        verificaDataValidade(lote);
        lote.diminuiQtdDoses();
    }

    private void validaDoseLotes(int qtdDoses) {
        if (qtdDoses < 0 || qtdDoses == 0) {
            throw new IllegalArgumentException("Quantidade de doses inválida!");
        }
    }

    private void validaDataDeValidade(LocalDate date){
        if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data de validade inválida");
        }
    }

    private void verificaDataValidade(Lote lote) {
        if (!lote.getDataDeValidade().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Lote com data de validade vencida! Remova-o para continuar");
        }
    }
}
