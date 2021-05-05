package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.service.AdministradorService;
import com.projeto.grupo10.vacineja.service.AdministradorServiceImpl;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.util.CustomErrorType;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import com.projeto.grupo10.vacineja.util.ErroLogin;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdministradorControllerAPI {
    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    AdministradorService administradorService;

    @RequestMapping(value = "/admin/funcionariosNaoAutorizados", method = RequestMethod.GET)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getFuncionariosNaoAutorizados(@RequestHeader("Authorization") String headerToken){
        ArrayList<String> usuariosNaoAutorizados;

        try{
            usuariosNaoAutorizados = this.administradorService.getUsuariosNaoAutorizados(headerToken);
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroSemPermissaoAdministrador();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<ArrayList<String>>(usuariosNaoAutorizados, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/autorizarFuncionario", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> autorizarCadastroFuncionario(@RequestHeader("Authorization") String headerToken,
                                                                        @RequestHeader String cpfFuncionario){

        try{
            this.administradorService.autorizarCadastroFuncionario(headerToken, cpfFuncionario);
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<String>("Cadastro aprovado.", HttpStatus.OK);
    }

}
