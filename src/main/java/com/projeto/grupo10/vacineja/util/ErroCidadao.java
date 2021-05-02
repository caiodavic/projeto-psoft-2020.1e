package com.projeto.grupo10.vacineja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCidadao {
    static final String USUARIO_SEM_PERMISSAO_FUNCIONARIO = "O usuário não tem permissão para realizar operações" +
            " como funcionario";
    static final String USUARIO_SEM_PERMISSAO_ADMINISTRADOR = "O usuário não tem permissão para realizar operações " +
            "como administrador";
    static final String USUARIO_NAO_ENCONTRADO = "O usuário não foi encontrado";
    static final String CIDADAO_CADASTRADO = "Cidadão com cpf %s já está cadastrado";

    public static ResponseEntity<String> erroSemPermissaoFuncionario(String usuario) {
        return new ResponseEntity<String>(String.format(ErroCidadao.USUARIO_SEM_PERMISSAO_FUNCIONARIO, usuario),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> erroSemPermissaoAdministrador() {
        return new ResponseEntity<String>(ErroCidadao.USUARIO_SEM_PERMISSAO_ADMINISTRADOR,
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> erroUsuarioNaoEncontrado() {
        return new ResponseEntity<String>(ErroCidadao.USUARIO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<String> erroCidadaoCadastrado(String cpf) {
		return new ResponseEntity<String>(
				String.format(ErroCidadao.CIDADAO_CADASTRADO, cpf), 
				HttpStatus.NOT_ACCEPTABLE);
	}
}
