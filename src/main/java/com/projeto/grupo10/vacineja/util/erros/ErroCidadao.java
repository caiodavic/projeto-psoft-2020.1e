package com.projeto.grupo10.vacineja.util.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCidadao {
    static final String USUARIO_SEM_PERMISSAO_FUNCIONARIO = "O usuário não tem permissão para realizar operações" +
            " como funcionario";
    static final String USUARIO_SEM_PERMISSAO_ADMINISTRADOR = "O usuário não tem permissão para realizar operações " +
            "como administrador";
    static final String USUARIO_NAO_ENCONTRADO = "O usuário não foi encontrado";
    static final String CIDADAO_CADASTRADO = "Cidadão com cpf %s já está cadastrado";
    static final String EMAIL_INVALIDO = "Email inválido";
    static final String CIDADAO_NAO_CADASTRADO = "Cidadão com cpf %s não está cadastrado";

    public static ResponseEntity<CustomErrorType> erroSemPermissaoFuncionario(String usuario) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroCidadao.USUARIO_SEM_PERMISSAO_FUNCIONARIO, usuario)),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<CustomErrorType> erroSemPermissaoAdministrador() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCidadao.USUARIO_SEM_PERMISSAO_ADMINISTRADOR),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<CustomErrorType> erroUsuarioNaoEncontrado() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCidadao.USUARIO_NAO_ENCONTRADO), HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<CustomErrorType> erroCidadaoCadastrado(String cpf) {
		return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroCidadao.CIDADAO_CADASTRADO, cpf)),
				HttpStatus.NOT_ACCEPTABLE);
	}
    public static ResponseEntity<CustomErrorType> erroEmailInvalido() {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroCidadao.EMAIL_INVALIDO)),
                HttpStatus.NOT_ACCEPTABLE);
    }

    public static ResponseEntity<CustomErrorType> erroCidadaoNaoCadastrado(String cpf) {
        return new ResponseEntity<CustomErrorType>(
                new CustomErrorType(String.format(ErroCidadao.CIDADAO_NAO_CADASTRADO, cpf)),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
