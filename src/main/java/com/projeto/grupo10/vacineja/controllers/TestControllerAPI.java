package com.projeto.grupo10.vacineja.controllers;


import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
}
