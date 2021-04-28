package com.projeto.grupo10.vacineja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroJWT {
    static final String USUARIO_SEM_PERMISSAO_FUNCIONARIO = "O usuário %s não tem permissão para realizae operações" +
            " como funcionario";
    static final String USUARIO_SEM_PERMISSAO_ADMINISTRADOR = "O usuário %s não tem permissão para realizae operações " +
            "como administrador";

    public static ResponseEntity<String> erroSemPermissaoFuncionario(String usuario) {
        return new ResponseEntity<String>(String.format(ErroJWT.USUARIO_SEM_PERMISSAO_FUNCIONARIO, usuario),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> erroSemPermissaoAdministrador(String usuario) {
        return new ResponseEntity<String>(String.format(ErroJWT.USUARIO_SEM_PERMISSAO_ADMINISTRADOR, usuario),
                HttpStatus.UNAUTHORIZED);
    }
}
