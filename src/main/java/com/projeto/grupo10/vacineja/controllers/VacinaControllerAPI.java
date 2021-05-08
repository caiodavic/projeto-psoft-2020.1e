package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.service.*;
import com.projeto.grupo10.vacineja.util.erros.ErroLote;
import com.projeto.grupo10.vacineja.util.erros.ErroVacina;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.OAuth2Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

// É necessário realizar uma verificação toda vez que uma ação com vacinação for feita
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinaControllerAPI {
    @Autowired
    VacinaService vacinaService;

    @Autowired
    LoteService loteService;

    @Autowired
    JWTService jwtService;


    @PostMapping("/vacina")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> criaVacina(@RequestHeader("Authorization") String headerToken, @RequestBody VacinaDTO vacinaDTO){

        try {
            Vacina vacina = vacinaService.criaVacina(vacinaDTO, headerToken);
            return new ResponseEntity<>(vacina, HttpStatus.CREATED);

        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroCadastroVacina(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e){
            return ErroVacina.erroCadastroVacina("eae kkk");
        }
    }

    // TO-DO exception handling
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
        } catch (ArrayIndexOutOfBoundsException e){
            return ErroVacina.erroCadastroVacina("eae kkk");
        }

    }

    // TO-DO exception handling
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
        } catch (ArrayIndexOutOfBoundsException e){
            return ErroVacina.erroListarVacina("eae kkk");
        }
    }

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
        } catch (ArrayIndexOutOfBoundsException e){
            return ErroVacina.erroListarVacina("eae kkk");
        }
    }

    // TO-DO exception handling
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
     * Esboço do método de retirada de Vacina
     */
    @PostMapping("/vacina/{nome_fabricante}/{qtd_vacinas}")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> retiraVacina(@RequestHeader("Authorization") String headerToken, @PathVariable("nome_fabricante") String nomeFabricante, @PathVariable("qtd_vacinas") int qtdVacinas){

        try{
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);
            List<Lote> loteList = loteService.removeDoseLotes(nomeFabricante,qtdVacinas,headerToken);
            return new ResponseEntity<>(loteList,HttpStatus.CREATED);
        } catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);
        } catch (IllegalArgumentException | ServletException e){
            return ErroVacina.erroListarVacina(e.getMessage());
        }

    }
}