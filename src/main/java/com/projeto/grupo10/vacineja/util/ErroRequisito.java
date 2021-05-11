package com.projeto.grupo10.vacineja.util;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroRequisito {
    static final String REQUISITO_EXISTENTE = "Requisito %s já cadastrado";
    static final String REQUISITOS_INEXISTENTE = "Nenhum requisito cadastrado";
    static final String REQUISITO_INEXISTENTE = "Requisito %s não cadastrado";
    static final String REQUISITO_NAO_PODE_HABILITAR = "Não pode habilitar o requisito %s, temos mais cidadãos do que dose";



    public static ResponseEntity<CustomErrorType> requisitoCadastrado(RequisitoDTO requisito){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(REQUISITO_EXISTENTE,requisito.getRequisito())), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> nenhumRequisitoCadastrado(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(REQUISITOS_INEXISTENTE), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> requisitoNaoCadastrado(RequisitoDTO requisito){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(REQUISITO_INEXISTENTE,requisito.getRequisito())), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> requisitoNaoPodeHabilitar(RequisitoDTO requisito){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(REQUISITO_NAO_PODE_HABILITAR,requisito.getRequisito())), HttpStatus.BAD_REQUEST);
    }
}
