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
import java.util.Optional;


/**
 * Implementação re LoteService responsável por ministrar métodos de criação, listagem, remoção, reserva e validação de
 * Vacinas em lotes.
 */
@Service
public class LoteServiceImpl implements LoteService {
    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private CidadaoService cidadaoService;

    /**
     * Cria um lote com base em LoteDTO. Realiza verifição jwt para ver se o dono do Token passado é um administrador
     *
     * @param loteDTO eh o modelo do lote
     * @param vacina eh a vacina do lote
     * @param headerToken eh o token de verificacao do adm
     * @return o lote criado
     * @throws ServletException se houver algum problema na validacao jwt
     */
    @Override
    public Lote criaLote(LoteDTO loteDTO, Vacina vacina, String headerToken) throws ServletException {

        cidadaoService.verificaTokenFuncionario(headerToken);

        validaDoseLotes(loteDTO.getQtdDoses());
        validaDataDeValidade(loteDTO.getDataDeValidade());

        Lote lote = new Lote(vacina, loteDTO.getQtdDoses(), loteDTO.getDataDeValidade());
        loteRepository.save(lote);
        return lote;
    }

    /**
     * Retorna todos os lotes armazenados no sistema. Realiza verifição jwt para ver se o dono do Token passado é um funcionário
     *
     * @param headerToken eh o token de verificacao de funcionario
     * @return a lista de lotes
     * @throws ServletException se houver aglum problema na verificacao jwt
     */
    @Override
    public List<Lote> listaLotes(String headerToken) throws ServletException{
        cidadaoService.verificaTokenFuncionario(headerToken);
        return loteRepository.findAll();
    }

    /**
     * Retorna todos os lotes de vacina de um determinado fabricante. Realiza verifição jwt para ver se o dono do Token passado é um funcionário
     *
     * @param nomeFabricante eh o nome do fabricante da vacina procurada
     * @param headerToken eh o token de verificacao de funcionario
     * @return a lista de lotes da fabricante
     * @throws ServletException se houver algum problema na verificacao jwt
     */
    @Override
    public List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException{
        cidadaoService.verificaTokenFuncionario(headerToken);
        return loteRepository.findAllByNomeFabricanteVacina(nomeFabricante);
    }



     //TODO Esse método é um esboço, será removido após definir que só podem ser ministradas doses reservadas
    /**
     * Remove qtdVacinas dose(s) de Vacina dentro de Lotes. Realiza verifição jwt para ver se o dono do Token passado é um funcionário.
     * Se a data de validade de algum lote encontrado estiver vencida, o lote é removido e uma exceção é lançada (IllegalArgument).
     *
     * @param nomeFabricante eh o nome da fabricante da vacina
     * @param qtdVacinas eh
     * @param authToken eh o token de verificacao de funcionario
     * @return a lista de lotes validos (???) TODO mudar isso
     * @throws ServletException se houver algum problema na verificacao jwt
     */
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
                currentLote.diminuiQtdDosesDisponiveis();
                loteRepository.save(currentLote);
            }
        }
        return loteList;
    }

    //TODO os dois métodos abaixo são redundantes, apenas um pode sobreviver

    /**
     * Retorna Vacina cuja dose já foi reservada dentro de um Lote com id = idLote. Exceções serão lançadas caso haja
     * id inexistente (NullPointer) ou não houver doses reservadas disponíveis (IllegalArgument).
     *
     * @param idLote eh id do lote procurado
     * @return a vacina cuja dose está reservada
     */
    public Vacina getVacinaReservadaByIdLote(Long idLote){
        Optional<Lote> optionalLote = loteRepository.findById(idLote);

        if(optionalLote.isEmpty())
            throw new NullPointerException("Não há Lote com esse ID!!!");

        Lote loteReservado = optionalLote.get();

        if(loteReservado.getQtdDosesReservadas() <= 0)
            throw new IllegalArgumentException("O Lote informado não tem doses reservadas!");

        verificaDataValidade(loteReservado);
        loteReservado.diminuiQtdDosesReservadas();
        loteRepository.save(loteReservado);


        return loteReservado.getVacina();
    }

    /**
     * Retorna Vacina cuja dose já foi reservada em algum Lote. Exceção será lançada caso não haja Lotes válidos no
     * sistema. Lotes válidos são aqueles com fabricante = nomeFabricante e qtdDosesReservadas > 0
     *
     * @param nomeFabricante eh o nome do fabricante do lote
     * @return a vacina cuja dose está reservada
     */
    public Vacina getVacinaReservadaByNomeFabricante(String nomeFabricante) {
        Optional<Lote> optionalLote = loteRepository.findFirstByQtdDosesDisponiveisGreaterThanAndNomeFabricanteVacina(0, nomeFabricante);

        if (optionalLote.isEmpty())
            throw new NullPointerException("Não há Lotes da Vacina informada contendo Doses reservadas!!");

        Lote loteReservado = optionalLote.get();
        verificaDataValidade(loteReservado);
        loteReservado.diminuiQtdDosesReservadas();
        loteRepository.save(loteReservado);

        return loteReservado.getVacina();
    }

    /**
     * Verifica se o Lote está vazio
     *
     * @param lote eh o lote
     * @return booleano informado se está vazio ou nao
     */
    private boolean isLoteVazio(Lote lote){
        return lote.getQtdDosesDisponiveis() == 0;
    }


    /**
     * Valida a quantidade de doses de vacina. Ela deve ser maior ou igual a 0 para não lançar exceção.
     *
     * @param qtdDoses eh a quantidade de doses a ser validada
     */
    private void validaDoseLotes(int qtdDoses) {
        if (qtdDoses <= 0) {
            throw new IllegalArgumentException("Quantidade de doses inválida!");
        }
    }

    /**
     * Valida a data de validade. Ela deve ser depois que hoje.
     *
     * @param date eh a data de valiade a ser validada
     */
    private void validaDataDeValidade(LocalDate date){
        if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data de validade inválida");
        }
    }

    /**
     * Verifica a data de validade. Se o Lote estiver vencido ele será removido e uma exceção será lançada");
     * @param lote eh o lote cuja data de validade sera verificada
     */
    private void verificaDataValidade(Lote lote) {
        if (!lote.getDataDeValidade().isAfter(LocalDate.now())) {
            loteRepository.delete(lote);
            throw new IllegalArgumentException("Lote com data de validade vencida! Lote removido. " +
                    "Adicione novo lote para continuar vacinação");
        }
    }

    //TODO ver se é melhor metodo abaixo retorna Lote ao invés de Vacina, afinal é mt mais util saber o Lote do que a Vacina
    //TODO verificar tambem QUEM irá fazer a reserva (Cidadao ou Funcionario), para questoes de verificacao JWT
    /**
     * Método que reserva uma vacina no primeiro lote válido que encontrar. Leia-se lote válido aquele que terá
     * validade depois da data prevista para vacinação. Se não encontrar lotes válidos, lança uma exceção.
     *
     * @param dataVacinacao é a data prevista para vacinação
     * @return a vacina válida
     */
    public Vacina reservaVacinaEmLote(LocalDate dataVacinacao)  {

        // TODO is this a bed smell? https://imgur.com/a/jMF5qXz
        Optional<Lote> optPrimeiroLoteValido = loteRepository.findAllByQtdDosesDisponiveisGreaterThanAndDataDeValidadeBeforeOrderByDataDeValidadeAsc(0,dataVacinacao);

        if(optPrimeiroLoteValido.isEmpty()){
            throw new NullPointerException("Não há lotes com data de validade disponíveis para essa data!");
        }

        Lote loteValido = optPrimeiroLoteValido.get();
        Vacina vacinaValida = loteValido.getVacina();

        loteValido.diminuiQtdDosesDisponiveis();
        loteValido.aumentaQtdDosesReservadas();

        loteRepository.save(loteValido);

        return vacinaValida;
    }

}
