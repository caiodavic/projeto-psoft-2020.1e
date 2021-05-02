package com.projeto.grupo10.vacineja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroVacina {
    static final String VACINA_NAO_CADASTRADA = "Vacina da Fabricante %s não cadastrada!";
    static final String VACINA_JA_CADASTRADA = "Vacina da Fabricante %s já está cadastrada, tente adicionar novas doses ao invés de criar!";
    static final String SEM_DOSES_SUFICIENTES = "Não há doses suficientes da Vacina  %s!";

    public static ResponseEntity<CustomErrorType> erroVacinaNaoCadastrada(String nomeFabricante) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroVacina.VACINA_NAO_CADASTRADA, nomeFabricante)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroVacinaJaCadastrada(String nomeFabricante) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroVacina.VACINA_JA_CADASTRADA, nomeFabricante)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> erroNaoHaVacinaSuficiente(String nomeFabricante) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroVacina.SEM_DOSES_SUFICIENTES, nomeFabricante)),
                HttpStatus.BAD_REQUEST);
    }

}
