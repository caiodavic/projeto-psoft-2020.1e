package com.projeto.grupo10.vacineja.util;

import com.projeto.grupo10.vacineja.model.requisitos_vacina.RequisitoDTO;
import com.projeto.grupo10.vacineja.util.CustomErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroRequisito {
    static final String REQUISITO_EXISTENTE = "Requisito %s já cadastrado";
    static final String REQUISITOS_INEXISTENTE = "Nenhum requisito cadastrado";
    static final String REQUISITO_INEXISTENTE = "Requisito %s não cadastrado";


    public ResponseEntity<CustomErrorType> requisitoCadastrado(RequisitoDTO requisito){
         return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(REQUISITO_EXISTENTE,requisito.getRequisito())), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CustomErrorType> nenhumRequisitoCadastrado(){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(REQUISITOS_INEXISTENTE), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<CustomErrorType> requisitoNaoCadastrado(String requisito){
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(REQUISITO_INEXISTENTE,requisito)), HttpStatus.BAD_REQUEST);
    }
}
