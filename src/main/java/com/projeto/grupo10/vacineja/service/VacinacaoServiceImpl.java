package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class VacinacaoServiceImpl implements VacinacaoService {

    @Autowired
    private VacinaRepository vacinaRepository;

    //TO-DO verificar como usar o JWTService no service de Vacina

    /**
     * Retornar uma lista das vacinas armazenadas
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

    /**
     * Adiciona qtdDoses de Vacina a VacinaRepository
     * @param nomeFabricante eh o nome do fabricante da Vacina
     * @param qtdDoses eh o num de doses a ser adicionada
     */
    @Override
    public void addDosesVacinaEstoque(String nomeFabricante, int qtdDoses) {
        Vacina vacina = fetchVacina(nomeFabricante);
        vacina.addDoses(qtdDoses);
    }


    /**
     * Remove qtdDoses de Vacina em VacinaRepository
     * @param nomeFabricante eh o nome do fabricante da Vacina
     * @param qtdDoses eh o num de doses a ser adicionada
     */
    @Override
    public void removeDosesVacinaEstoque(String nomeFabricante, int qtdDoses) throws ArrayIndexOutOfBoundsException {
        Vacina vacina = fetchVacina(nomeFabricante);

        if(!verificaEstoque(nomeFabricante,qtdDoses)){
            //TO-DO Ver se existe outra exceção mais adequada a "não ha doses suficiente de vacina"
            throw new ArrayIndexOutOfBoundsException();
        }
        vacina.removeDoses(qtdDoses);
    }

    /**
     * Vê se exite qtdDoses suficentes de Vacina suficientes em VacinaRepository
     * @param nomeFabricante eh o nome do fabricante da Vacina
     * @param qtdDoses eh o num de de doses buscadas
     * @return booleano que informa se há ou não doses suficientes
     */
    @Override
    public boolean verificaEstoque(String nomeFabricante, int qtdDoses) {
        Vacina vacina = fetchVacina(nomeFabricante);

        if(vacina.getQtdDoses() < qtdDoses){
            return false;
        }
        return true;
    }

    /**
     * Cria uma nova Vacina. Caso já exista uma Vacina com o mesmo nome de Fabricante, uma exceção irá ser lançada.
     * @param vacinaDTO eh o DTO da Vacina a ser criada
     * @return
     */
    @Override
    public Vacina criaVacina(VacinaDTO vacinaDTO) {
        Optional<Vacina> optionalVacina = vacinaRepository.findById(vacinaDTO.getNomeFabricante());

        if(optionalVacina.isPresent()){
            throw new IllegalArgumentException();
        }

        Vacina vacina = new Vacina(vacinaDTO.getNomeFabricante(), vacinaDTO.getNumDosesNecessarias(), vacinaDTO.getDiasEntreDoses(), vacinaDTO.getQtdDoses());

        vacinaRepository.save(vacina);


        return vacina;
    }

    // TO-DO Verificar o comportamento do lançamento da exceção, se
    // existe algum problema no tratamento embaixo de tantas camadas de metodos.

    /**
     * Busca a Vacina em VacinaRepository, se encontrar ele a retorna, se não lança uma exceção
     * @param nomeFabricante eh o nome do fabricante da Vacina
     * @return a vacina procurada
     */
    public Vacina fetchVacina(String nomeFabricante) throws NullPointerException{
        Optional<Vacina> optionalVacina = getVacinaById(nomeFabricante);
        if(optionalVacina.isEmpty()){
            throw new NullPointerException();
        }
        return optionalVacina.get();
    }

}
// Null pointer = nao ha vacina cadastrada
// Illegal argument = ja existe vacina cadastrada
// arrayindexoutofbound = nao ha doses suficientes para ministrar