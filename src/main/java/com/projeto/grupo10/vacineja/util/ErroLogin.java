package com.projeto.grupo10.vacineja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.PushBuilder;

public class ErroLogin {
    static final String ERRO_AUTENTICAR_LOGIN_USUARIO_SENHA_ERRADO = "Cpf ou senha incorreto";
    static final String ERRO_AUTENTICAR_LOGIN_ADMINISTRADOR = "Esse usuário não pode realizar login como administrador";
    static final String ERRO_AUTENTICAR_LOGIN_FUNCIONARIO = "Esse usuário não pode realizar login como funcionario";
    static final String ERRO_TOKEN_INVALIDO = "Token invalido ou expirado!";


    public static ResponseEntity<String> erroLoginSenhaUsuarioErrado() {
        return new ResponseEntity<String>(ErroLogin.ERRO_AUTENTICAR_LOGIN_USUARIO_SENHA_ERRADO, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<String> erroLoginNaoAutorizadoFuncionario() {
        return new ResponseEntity<String>(ErroLogin.ERRO_AUTENTICAR_LOGIN_FUNCIONARIO, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<String> erroLoginNaoAutorizadoAdministrador() {
        return new ResponseEntity<String>(ErroLogin.ERRO_AUTENTICAR_LOGIN_ADMINISTRADOR, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<String> erroTokenInvalido(){
        return new ResponseEntity<String>(ErroLogin.ERRO_TOKEN_INVALIDO, HttpStatus.FORBIDDEN);
    }
}
