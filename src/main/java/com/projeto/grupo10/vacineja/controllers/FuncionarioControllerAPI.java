package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.DTO.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.service.*;
import com.projeto.grupo10.vacineja.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FuncionarioControllerAPI {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    VacinaService vacinaService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    LoteService loteService;

    @Autowired
    JWTService jwtService;



    @RequestMapping(value = "/funcionario/ministrar-vacina", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> ministrarVacina(@RequestHeader("Authorization") String headerToken,
                                             @RequestBody MinistraVacinaDTO ministraVacinaDTO){

        String tipoVacina = ministraVacinaDTO.getTipoVacina();
        Date dataVacina = ministraVacinaDTO.getDataVacinacao();
        String cpfCidadao = ministraVacinaDTO.getCpfCidadao();

        try{
            Vacina vacina = vacinaService.fetchVacina(tipoVacina);
            this.cidadaoService.ministraVacina(headerToken, cpfCidadao, vacina, dataVacina);
            List<Lote> loteList = loteService.removeDoseLotes(tipoVacina,1,headerToken);
        }
        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<String>("Cidadão definido como funcionario, aguardando aprovação do administrador.",
                HttpStatus.OK);
    }

    @RequestMapping(value = "/funcionario/habilitar-segunda-dose", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> habilitarSegundaDose(@RequestHeader("Authorization") String headerToken){
        try{
            this.cidadaoService.habilitarSegundaDose(headerToken);
        } catch (ServletException e) {
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<String>("Segunda Dose habilitada para alguns pacientes",
                HttpStatus.OK);
    }

    /**
     * Faz uma listagem de todas as Vacinas cadastradas no Sistema. É necessária a apresentção de token de Funcionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token do funcionario valido
     * @return response entity adequada, contendo a lista de Vacinas
     */
    @GetMapping("/funcionario/vacinas")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> listaVacinas(@RequestHeader("Authorization") String headerToken){

        try {
            List<Vacina> vacinasList = vacinaService.listarVacinas(headerToken);

            if(vacinasList.isEmpty()){
                return  ErroVacina.semVacinasCadastradas();
            }
            return new ResponseEntity<>(vacinasList,HttpStatus.OK);
        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroListarVacina(e.getMessage());
        }

    }

    /**
     * Cria uma Lote de Vacina a partir de LoteDTO. É necessária a apresentação de token de Funcionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token de funcionario valido
     * @param nomeFabricante eh o nome do fabricante do lote
     * @param loteDTO eh o dto do lote, contendo qtd de doses e data de validade
     * @return response entity adequada, contendo o lote criado
     */
    @PostMapping("/funcionario/vacinas/lotes/{nome-fabricante}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> criaLote(@RequestHeader("Authorization") String headerToken, @PathVariable("nome-fabricante") String nomeFabricante, @RequestBody LoteDTO loteDTO){

        try{
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);
            Lote lote = loteService.criaLote(loteDTO,vacina, headerToken);
            return new ResponseEntity<>(lote,HttpStatus.CREATED);

        }
        catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);

        }
        catch (IllegalArgumentException | ServletException e){
            return ErroLote.erroCadastroLote(e.getMessage());
        }

    }

    /**
     * Retorna todos os Lotes de um certo fabricante. É necessária a apresentação de token de Funcionário
     * valído para realizar essa ação.
     *
     * @param nomeFabricante eh o nome do fabricante do lote
     * @param headerToken eh o token de funcionario valido
     * @return response entity adequada, contendo a lista de Lotes do fabricante
     */
    @GetMapping("/funcionario/vacinas/lotes/{nome-fabricante}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> listaLotesPorFabricante(@PathVariable ("nome-fabricante") String nomeFabricante, @RequestHeader("Authorization") String headerToken){
        try {
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);

            List<Lote> loteList = loteService.listaLotesPorFabricante(nomeFabricante, headerToken);

            if(loteList.isEmpty()){
                return ErroLote.semLotesCadastrados();
            }

            return new ResponseEntity<>(loteList, HttpStatus.OK);

        }
        catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);
        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroListarVacina(e.getMessage());
        }
    }

    /**
     * Retorna todos os Lotes de Vacina criadas. É necessária a apresentação de token de Funcionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token de funcionario valido
     * @return response entity adequada, contendo a lista de Lotes
     */
    @GetMapping("/funcionario/vacinas/lotes")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> listaLotes(@RequestHeader("Authorization") String headerToken){


        try {
            List<Lote> loteList = loteService.listaLotes(headerToken);
            if(loteList.isEmpty()){
                return ErroLote.semLotesCadastrados();
            }
            return new ResponseEntity<>(loteList,HttpStatus.OK);
        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroListarVacina(e.getMessage());
        }
    }

    /**
     * TODO Esboço do método de retirada de Vacina, será removido (ou modificado) após definido a remoção somente de vacinas reservadas
     */
    @PostMapping("/funcionario/vacinas/{nome-fabricante}/{qtd-vacinas}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> retiraVacina(@RequestHeader("Authorization") String headerToken, @PathVariable("nome-fabricante") String nomeFabricante, @PathVariable("qtd-vacinas") int qtdVacinas){

        try{
            vacinaService.fetchVacina(nomeFabricante);
            List<Lote> loteList = loteService.removeDoseLotes(nomeFabricante,qtdVacinas,headerToken);
            return new ResponseEntity<>(loteList,HttpStatus.CREATED);
        } catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);
        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroListarVacina(e.getMessage());
        }

    }

    @RequestMapping(value = "/funcionario/habilita-idade", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> habilitaIdade(@RequestHeader("Authorization") String headerToken,
                                           @RequestBody RequisitoDTO requisito){

        try{
            this.funcionarioService.alteraIdadeGeral(requisito,headerToken);
        } catch (ServletException e){
            ErroLogin.erroTokenInvalido();
        } catch (IllegalCallerException ice){
            ErroRequisito.requisitoNaoPodeHabilitar(requisito);
        } catch (IllegalArgumentException iae){
            ErroRequisito.requisitoNaoCadastrado(requisito);
        }

        return new ResponseEntity<String>(String.format("A partir de agora pessoas com %d ou mais poderão se vacinar",requisito.getIdade()),HttpStatus.OK);
    }

    @RequestMapping(value = "/funcionario/habilita-requisito", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> habilitaRequisito(@RequestHeader("Authorization") String headerToken,
                                               @RequestBody RequisitoDTO requisito){

        try{
            this.funcionarioService.setComorbidadeHabilitada(requisito,headerToken);
        } catch (ServletException e){
            ErroLogin.erroTokenInvalido();
        } catch (IllegalCallerException ice){
            ErroRequisito.requisitoNaoPodeHabilitar(requisito);
        } catch (IllegalArgumentException iae){
            ErroRequisito.requisitoNaoCadastrado(requisito);
        }

        return new ResponseEntity<String>(String.format("A partir de agora pessoas com o requisito %s com a idade %d ou mais poderão se vacinar",requisito.getRequisito(),requisito.getIdade()),HttpStatus.OK);
    }
}
