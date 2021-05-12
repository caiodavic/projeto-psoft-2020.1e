package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.LoteDTO;
import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.Requisito;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    RequisitoService requisitoService;

    @Autowired
    LoteService loteService;

    @Autowired
    VacinaService vacinaService;

    /**
     * Método responsável por chamar o método de requisitoService que altera a idade geral das pessoas que podem vacinar
     * @param requisito idade a ser habilitada para vacina
     * @param headerToken token do funcionário que está executando a operação
     * @throws ServletException lança caso o token seja inválido
     * @throws IllegalArgumentException lança caso o requisito esteja errado
     * @throws IllegalCallerException  lança caso tenha mais pessoas a serem habilitadas do que doses disponiveis
     */
    @Override
    public void alteraIdadeGeral(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException, IllegalCallerException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        if(!cidadaoService.podeAlterarIdade(requisito))
            throw new IllegalCallerException("Impossivel habilitar a idade");

        requisitoService.setIdade(requisito);
        Optional<Requisito> requisitoOptional = requisitoService.getRequisitoById(requisito.getRequisito());
        if(requisitoOptional.isEmpty())
            throw new IllegalCallerException("Ocorreu algum erro com o requisito");

        cidadaoService.habilitaPelaIdade(requisitoOptional.get());
    }

    /**
     * Método responsável por chamar o método de requisitoService que habilita um novo requisito
     * @param requisito requisito a ser habilitado para vacina
     * @param headerToken token do funcionário que está executando a operação
     * @throws ServletException lança caso o token seja inválido
     * @throws IllegalArgumentException lança caso o requisito esteja errado
     * @throws IllegalCallerException  lança caso tenha mais pessoas a serem habilitadas do que doses disponiveis
     */
    @Override
    public void setComorbidadeHabilitada(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException, IllegalCallerException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        if(!cidadaoService.podeHabilitarRequisito(requisito))
            throw new IllegalCallerException("Impossível habilitar tal requisito");

        requisitoService.setPodeVacinar(requisito);
        Optional<Requisito> requisitoOptional = requisitoService.getRequisitoById(requisito.getRequisito());
        if(requisitoOptional.isEmpty())
            throw new IllegalCallerException("Ocorreu algum erro com o requisito");

        cidadaoService.habilitaPorRequisito(requisitoOptional.get());
    }

    /**
     * Metodo responsavel por ministrar uma dose da vacina ao cidadão que esteja apto a receber uma vacina,
     * a partir disso caso essa seja a segunda dose ou a vacina ministrada seja de dose unica o estado de
     * vacinação passa para finalizado e se for a primeira dose de uma vacina que tem duas doses o cidadão deve
     * ir para o estado de "tomou primeira dose"
     * @param headerToken - token do funcionario que deve ministrar a dose
     * @param  ministraVacinaDTO - Objeto que contem todas as informações necessarias para testar a vacina
     * @throws ServletException
     * @author Caetano Albuquerque
     */
    @Override
    public void ministraVacina(String headerToken, MinistraVacinaDTO ministraVacinaDTO) throws ServletException {
        this.cidadaoService.verificaTokenFuncionario(headerToken);

        String cpfCidadao = ministraVacinaDTO.getCartaoSus();
        Date dataVacina = ministraVacinaDTO.getDataVacinacao();
        String Tipovacina = ministraVacinaDTO.getTipoVacina();

        Vacina vacina = this.loteService.retirarVacinaValidadeProxima(Tipovacina);

        this.cidadaoService.recebeVacina(cpfCidadao, vacina, dataVacina);
    }

    /**

     * Método que retorna todas as comorbidades cadastradas no sistema
     * @return uma lista com todas as comorbidades já cadastradas
     * @throws IllegalArgumentException caso não exista nenhuma comorbidade
     * @throws  ServletException lança caso o token seja inválido
     * @author Caio Silva
     */
    @Override
    public List<String> listaComorbidadesCadastradas(String headerToken) throws ServletException, IllegalArgumentException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        List<String> requisitos = requisitoService.getTodasComorbidades();
        return requisitos;
    }

    /**
     * Método que retorna todas as profissoes cadastradas no sistema
     * @return uma lista com todas as comorbidades já cadastradas
     * @throws IllegalArgumentException caso não exista nenhuma comorbidade
     * @throws  ServletException lança caso o token seja inválido
     * @author Caio Silva
     */
    @Override
    public List<String> listaProfissoesCadastradas(String headerToken) throws ServletException, IllegalArgumentException {
        cidadaoService.verificaTokenFuncionario(headerToken);
        List<String> requisitos = requisitoService.getTodasProfissoes();
        return requisitos;
    }

    /**
     * Retorna a quantidade de cidadaos não habilitados com idade igual ou superior a idade passada como parametro
     * @param headerToken token do usuario logado
     * @param idade idade a ser usada para o calcul
     * @return quantidade de cidadaos nao habilitados com idade igual ou superior a idade passada
     * @throws ServletException lança caso o token seja inválido
     * @author Caio Silva
     */
    @Override
    public int getCidadaosAcimaIdade(String headerToken, int idade) throws ServletException {
       cidadaoService.verificaTokenFuncionario(headerToken);
       return cidadaoService.contaCidadaosAcimaIdade(idade);
    }

    public int getQtdCidadaosAtendeRequisito(String headerToken, RequisitoDTO requisito) throws ServletException, IllegalArgumentException{
        cidadaoService.verificaTokenFuncionario(headerToken);
        return cidadaoService.contaCidadaosAtendeRequisito(requisito);
    }


     /** Retorna todos os lotes armazenados no sistema. Realiza verifição jwt para ver se o dono do Token passado é um funcionário
     * @param headerToken - token do funcionario que ta criando a vacina
     * @return a lista de lotes
     * @throws ServletException se houver aglum problema na verificacao jwt
     */
    @Override
    public List<Lote> listaLotes(String headerToken) throws ServletException {
        this.cidadaoService.verificaTokenFuncionario(headerToken);
        return this.loteService.listaLotes();
    }

    /**
     *
     * Cria um lote com base em LoteDTO. Realiza verifição jwt para ver se o dono do Token passado é um administrador
     * @param headerToken - token do funcionario que ta criando a vacina
     * @param loteDTO eh o modelo do lote
     * @param nomeFabricante o tipo da vacina
     * @return o lote criado
     * @throws ServletException se houver algum problema na validacao jwt
     */
    @Override
    public Lote criarLote(String headerToken, String nomeFabricante, LoteDTO loteDTO) throws ServletException {
        this.cidadaoService.verificaTokenFuncionario(headerToken);

        Vacina vacina = this.vacinaService.fetchVacina(nomeFabricante);
        return this.loteService.criaLote(loteDTO, vacina);
    }

    /**
     * Retorna todos os lotes de vacina de um determinado fabricante. Realiza verifição jwt para ver se o dono do Token passado é um funcionário
     * @param headerToken - token do funcionario
     * @param nomeFabricante eh o nome do fabricante da vacina procurada
     * @return a lista de lotes da fabricante
     * @throws ServletException se houver algum problema na verificacao jwt
     */
    @Override
    public List<Lote> listaLotesPorFabricante(String nomeFabricante, String headerToken) throws ServletException {
        this.cidadaoService.verificaTokenFuncionario(headerToken);
        return this.loteService.listaLotesPorFabricante(nomeFabricante);
    }

    /**
     * Remove qtdVacinas dose(s) de Vacina dentro de Lotes. Realiza verifição jwt para ver se o dono do Token passado é um funcionário.
     * Se a data de validade de algum lote encontrado estiver vencida, o lote é removido e uma exceção é lançada (IllegalArgument).
     * @param headerToken - toke do funcionario
     * @param nomeFabricante eh o nome da fabricante da vacina
     * @param qtdVacinas eh
     * @return a lista de lotes validos (???) TODO mudar isso
     * @throws ServletException se houver algum problema na verificacao jwt
     */
    @Override
    public List<Lote> removeDoseLotes(String nomeFabricante, int qtdVacinas, String headerToken) throws ServletException {
        this.cidadaoService.verificaTokenFuncionario(headerToken);
        return this.loteService.removeDoseLotes(nomeFabricante, qtdVacinas);
    }

}