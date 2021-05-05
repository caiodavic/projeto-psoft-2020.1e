package com.projeto.grupo10.vacineja.controllers;


import com.projeto.grupo10.vacineja.model.usuario.*;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoDTO;
import com.projeto.grupo10.vacineja.model.usuario.FuncionarioCadastroDTO;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import com.projeto.grupo10.vacineja.util.ErroEmail;
import com.projeto.grupo10.vacineja.util.ErroLogin;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoControllerAPI {

    @Autowired
    CidadaoService cidadaoService;


    @RequestMapping(value = "/usuario/cadastraCidadao", method = RequestMethod.POST)
    public ResponseEntity<?> cadastraCidadao(@RequestBody CidadaoDTO cidadaoDTO) {

        Optional<Cidadao> cidadaos = cidadaoService.getCidadaoById(cidadaoDTO.getCpf());
        String emailCidadao = cidadaoDTO.getEmail();

        try{
            cidadaoService.cadastraCidadao(cidadaoDTO);
        } catch (IllegalArgumentException e){
            if(e.getMessage().toString() == "Email invalido"){
                return ErroCidadao.erroEmailInvalido();
            }
            else if(e.getMessage().toString() == "Cidadao cadastrado"){
                return ErroCidadao.erroCidadaoCadastrado(cidadaoDTO.getCpf());
            }
        }
        return new ResponseEntity<CidadaoDTO>(cidadaoDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/cidadao/cadastrarFuncionario", method = RequestMethod.POST)

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<String> cadastrarFuncionario(@RequestHeader("Authorization") String headerToken,
                                        @RequestBody FuncionarioCadastroDTO cadastroFuncionario){

        try{
            cidadaoService.cadastroFuncionario(headerToken, cadastroFuncionario);
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

    @RequestMapping(value = "/cidadao/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> updateCidadao(@RequestHeader("Authorization") String headerToken,
                                           @RequestBody CidadaoUpdateDTO cidadaoUpdateDTO) {

        Optional<Cidadao> cidadao = cidadaoService.getCidadaoById(cidadaoUpdateDTO.getCpf());


        try{
            cidadaoService.updateCidadao(headerToken, cidadaoUpdateDTO, cidadao.get());
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }




        cidadaoService.salvarCidadao(cidadao.get());
        return new ResponseEntity<Cidadao>(cidadao.get(),HttpStatus.ACCEPTED);
    }


}
