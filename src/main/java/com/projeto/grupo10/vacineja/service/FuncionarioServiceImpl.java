package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.Requisito;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    RequisitoService requisitoService;

    @Autowired
    LoteService loteService;

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
}