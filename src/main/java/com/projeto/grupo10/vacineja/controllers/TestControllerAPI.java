package com.projeto.grupo10.vacineja.controllers;


import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TestControllerAPI {

    @Autowired
    CidadaoService cidadaoService;


    @GetMapping(value = "/teste")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<String> teste(@RequestHeader("Authorization") String header){

        try{
            return new ResponseEntity<String>(cidadaoService.teste(header),HttpStatus.OK);
        }

        catch(IllegalArgumentException iae){
            return new ResponseEntity<String>("Sem autorização", HttpStatus.BAD_REQUEST);
        } catch (ServletException e){
            return new ResponseEntity<String>("Sem autorização", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/teste/testeEmail", method = RequestMethod.POST)
    public ResponseEntity<String> testeEmail(@RequestHeader String email){
        EmailService.enviarAlertaVacinacao("Atualização do Vacine Já",
                "Você esta habilitado para receber a primeira dose da vacina," +
                        " acesse o Vacine já e agende sua vacinação", email);

        return new ResponseEntity<String>("email enviado", HttpStatus.OK);
    }
}
