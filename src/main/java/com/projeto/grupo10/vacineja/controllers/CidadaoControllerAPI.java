package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoDTO;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
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
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoControllerAPI {

    @Autowired
    CidadaoService cidadaoService;

    @RequestMapping(value = "/usuario/cadastraCidadao", method = RequestMethod.POST)
    public ResponseEntity<?> criarCidadao(@RequestBody CidadaoDTO cidadaoDTO) {
        Optional<Cidadao> cidadaos = cidadaoService.getCidadaoById(cidadaoDTO.getCpf());
        String emailCidadao = cidadaoDTO.getEmail();
        if(cidadaos.isPresent())
            return ErroCidadao.erroCidadaoCadastrado(cidadaoDTO.getCpf());

        if(!ErroEmail.validarEmail(emailCidadao))
            return ErroCidadao.erroEmailInvalido();

        Cidadao cidadao = cidadaoService.criaCidadao(cidadaoDTO);
        cidadaoService.salvarCidadao(cidadao);

        return new ResponseEntity<Cidadao>(cidadao, HttpStatus.CREATED);
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


}
