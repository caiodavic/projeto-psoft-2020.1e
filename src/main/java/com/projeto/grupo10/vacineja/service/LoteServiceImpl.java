package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.util.List;


@Service
public class LoteServiceImpl implements LoteService {
    @Autowired
    LoteRepository loteRepository;

    @Autowired
    CidadaoService cidadaoService;

    @Override
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina, String headerToken) throws ServletException {

        cidadaoService.verificaTokenFuncionario(headerToken);

        validaDoseLotes(loteDTO.getQtdDoses());
        validaDataDeValidade(loteDTO.getDataDeValidade());

        Lote lote = new Lote(vacina, loteDTO.getQtdDoses(), loteDTO.getDataDeValidade());
        loteRepository.save(lote);
        return lote;
    }

    @Override
    public List<Lote> listaLotes(String headerToken) throws ServletException{
        cidadaoService.verificaTokenFuncionario(headerToken);
        return loteRepository.findAll();
    }

    public List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException{
        cidadaoService.verificaTokenFuncionario(headerToken);
        return loteRepository.findAllByNomeFabricanteVacina(nomeFabricante);
    }

    private void salvaLote(Lote loteVacina) {
        loteRepository.save(loteVacina);
    }

    @Override
    public List<Lote> removeDoseLotes(String nomeFabricante,int qtdVacinas, String authToken) throws ServletException {
        cidadaoService.verificaTokenFuncionario(authToken);
        List<Lote> loteList = loteRepository.findAllByNomeFabricanteVacina(nomeFabricante);
        int j = 0;

        for(int i = 0 ; i < qtdVacinas; i++){

            Lote currentLote = loteList.get(j);
            if(isLoteVazio(currentLote)){
                j++;
            }
            else{
                verificaDataValidade(currentLote);
                currentLote.diminuiQtdDoses();
                loteRepository.save(currentLote);
            }
        }
        return loteList;
    }

    private boolean isLoteVazio(Lote lote){
        return lote.getQtdDoses() == 0;
    }

    @Override
    public Lote findLoteByNomeFabricante(String nomeFabricante, String headerToken) throws ServletException{
        cidadaoService.verificaTokenFuncionario(headerToken);
       return loteRepository.findByNomeFabricanteVacina(nomeFabricante);
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
            loteRepository.delete(lote);
            throw new IllegalArgumentException("Lote com data de validade vencida! Lote removido. Adicione novo lote para continuar vacinação");
        }
    }
}
