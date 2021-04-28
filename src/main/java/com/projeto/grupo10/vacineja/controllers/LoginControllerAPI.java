package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginControllerAPI {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    JWTService jwtService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<String> autentica(@RequestBody CidadaoLoginDTO login) throws ServletException {
        return jwtService.autentica(login);
    }

}
