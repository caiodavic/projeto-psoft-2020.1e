package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VacinaServiceImpl implements VacinaService {

    @Autowired
    private VacinaRepository vacinaRepository;


    //TO-DO verificar se/como usar o JWTService no service de Vacina

    /**
     * Cria uma nova Vacina. Caso já exista uma Vacina com o mesmo nome de Fabricante, uma exceção irá ser lançada.
     * @param vacinaDTO eh o DTO da Vacina a ser criada
     * @return a vacina cadastrada
     */
    @Override
    public Vacina criaVacina(VacinaDTO vacinaDTO) {
        Optional<Vacina> optionalVacina = vacinaRepository.findById(vacinaDTO.getNomeFabricante());

        if(optionalVacina.isPresent()){
            throw new IllegalArgumentException("Já existe vacina desse fabricante cadastrada!");
        }

        validaDiasEntreDoses(vacinaDTO.getDiasEntreDoses());
        validaNumDoses(vacinaDTO.getNumDosesNecessarias());

        Vacina vacina = new Vacina(vacinaDTO.getNomeFabricante(), vacinaDTO.getNumDosesNecessarias(), vacinaDTO.getDiasEntreDoses());

        vacinaRepository.save(vacina);

        return vacina;
    }

    /**
     * Retorna uma lista das vacinas armazenadas
     * @return lista das vacinas armazenadas em VacinaRepository
     */
    @Override
    public List<Vacina> listarVacinas() {
        return vacinaRepository.findAll();
    }

    /**
     * Retorna um Optional<Vacina> baseada no seu Id (caso nao exista retornara empty)
     * @param nomeFabricante eh o nome do fabricante da Vacina procurada
     * @return optional da Vacina procurada
     */
    @Override
    public Optional<Vacina> getVacinaById(String nomeFabricante) {
        return this.vacinaRepository.findById(nomeFabricante);
    }



    // TO-DO Verificar o comportamento do lançamento da exceção, se
    // existe algum problema no tratamento embaixo de tantas camadas de metodos.

    /**
     * Busca a Vacina em VacinaRepository, se encontrar ele a retorna, se não lança uma exceção
     * @param nomeFabricante eh o nome do fabricante da Vacina
     * @return a vacina procurada
     */
    @Override
    public Vacina fetchVacina(String nomeFabricante) throws NullPointerException{
        Optional<Vacina> optionalVacina = getVacinaById(nomeFabricante);
        if(optionalVacina.isEmpty()){
            throw new NullPointerException("Não há Vacina desse fabricante cadastrada!");
        }
        return optionalVacina.get();
    }

    private void validaNumDoses(int numDosesNecessarias){
        if(numDosesNecessarias > 2 || numDosesNecessarias <= 0){
            throw new IllegalArgumentException("Número de doses inválido! (o mínino é 1 e o máximo 2)");
        }
    }

    private void validaDiasEntreDoses(int diasEntreDoses){
        if(diasEntreDoses < 30 || diasEntreDoses > 90){
            throw new IllegalArgumentException("Quantidade de dias entre doses inválido! O mínimo é 30 e o máximo é 90");
        }
    }

}
// Null pointer = nao ha vacina cadastrada
// Illegal argument = ja existe vacina cadastrada
// arrayindexoutofbound = nao ha doses suficientes para ministrar