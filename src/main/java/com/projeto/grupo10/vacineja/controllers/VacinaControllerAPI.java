package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.DTO.VacinaDTO;
import com.projeto.grupo10.vacineja.service.*;
import com.projeto.grupo10.vacineja.util.ErroLote;
import com.projeto.grupo10.vacineja.util.ErroVacina;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.List;

/**
 * Sumariza funcionalidades relacionadas à Vacina e Lotes: criação, listagem, atualização e remoção.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinaControllerAPI {
    /**
     * Responsável por realizar funções de cadastro, verificação, listagem e busca de Vacinas no sistema.
     */
    @Autowired
    VacinaService vacinaService;

    /**
     * Responsável por ministrar métodos de criação, listagem, remoção, reserva e validação de vacinas em lotes.
     */
    @Autowired
    LoteService loteService;

    /**
     * Entidade que faz verificações e checagens liagadas ao serviço jwt
     */
    @Autowired
    JWTService jwtService;

    /**
     * Cria uma Vacina a partir de uma VacinaDTO. É necessária a apresentação do token de Administrador para relizar essa
     * ação. Nesse sistema, por padrão, o ADM tem CPF = "00000000000".
     *
     * @param headerToken eh o token do administrador
     * @param vacinaDTO eh o dto da vacina a ser criada
     * @return response entity adequada, contendo a vacina criada
     */
    @PostMapping("/vacina")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> criaVacina(@RequestHeader("Authorization") String headerToken, @RequestBody VacinaDTO vacinaDTO){

        try {
            Vacina vacina = vacinaService.criaVacina(vacinaDTO, headerToken);
            return new ResponseEntity<>(vacina, HttpStatus.CREATED);

        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroCadastroVacina(e.getMessage());
        }
    }

    /**
     * Faz uma listagem de todas as Vacinas cadastradas no Sistema. É necessária a apresentção de token de Fucnionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token do funcionario valido
     * @return response entity adequada, contendo a lista de Vacinas
     */
    @GetMapping("/vacina")
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
     * Cria uma Lote de Vacina a partir de LoteDTO. É necessária a apresentação de token de Fucnionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token de funcionario valido
     * @param nomeFabricante eh o nome do fabricante do lote
     * @param loteDTO eh o dto do lote, contendo qtd de doses e data de validade
     * @return response entity adequada, contendo o lote criado
     */
    @PostMapping("/vacina/lote/{nome_fabricante}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> criaLote(@RequestHeader("Authorization") String headerToken, @PathVariable("nome_fabricante") String nomeFabricante, @RequestBody LoteDTO loteDTO){

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
     * Retorna todos os Lotes de Vacina criadas. É necessária a apresentação de token de Fucnionário
     * valído para realizar essa ação.
     *
     * @param headerToken eh o token de funcionario valido
     * @return response entity adequada, contendo a lista de Lotes
     */
    @GetMapping("/vacina/lote")
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
     * Retorna todos os Lotes de um certo fabricante. É necessária a apresentação de token de Fucnionário
     * valído para realizar essa ação.
     *
     * @param nomeFabricante eh o nome do fabricante do lote
     * @param headerToken eh o token de funcionario valido
     * @return response entity adequada, contendo a lista de Lotes do fabricante
     */
    @GetMapping("/vacina/lote/{nome_fabricante}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> listaLotesPorFabricante(@PathVariable ("nome_fabricante") String nomeFabricante, @RequestHeader("Authorization") String headerToken){
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
     * TODO Esboço do método de retirada de Vacina, será removido (ou modificado) após definido a remoção somente de vacinas reservadas
     */
    @PostMapping("/vacina/{nome_fabricante}/{qtd_vacinas}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> retiraVacina(@RequestHeader("Authorization") String headerToken, @PathVariable("nome_fabricante") String nomeFabricante, @PathVariable("qtd_vacinas") int qtdVacinas){

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


}
