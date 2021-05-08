package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.DTO.MinistraVacinaDTO;
import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.service.JWTService;
import com.projeto.grupo10.vacineja.service.LoteService;
import com.projeto.grupo10.vacineja.service.VacinaService;
import com.projeto.grupo10.vacineja.util.erros.ErroCidadao;
import com.projeto.grupo10.vacineja.util.erros.ErroLogin;
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
    LoteService loteService;

    @Autowired
    JWTService jwtService;



    @RequestMapping(value = "/funcionario/ministrarVacina", method = RequestMethod.POST)
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
}
